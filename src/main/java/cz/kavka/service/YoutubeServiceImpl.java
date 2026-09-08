package cz.kavka.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;
import cz.kavka.dto.YouTubeVideoDTO;
import cz.kavka.service.serviceinterface.YouTubeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeServiceImpl implements YouTubeService {

    @Value("${youtube.api.key}")
    private String API_KEY;

    @Value("${youtube.api.channel-id}")
    private String channelId;

    private final YouTube youtube;

   public YoutubeServiceImpl(YouTube youTube){
       this.youtube = youTube;
   }


    @Override
    public List<YouTubeVideoDTO> fetchAllVideos() throws IOException {
        // Step 1: Get Uploads playlist ID
        YouTube.Channels.List channelRequest = youtube.channels()
                .list("contentDetails")
                .setId(channelId)
                .setKey(API_KEY);

        ChannelListResponse channelResponse = channelRequest.execute();
        String uploadsPlaylistId = channelResponse.getItems().get(0)
                .getContentDetails().getRelatedPlaylists().getUploads();

        // Step 2: Get videos from uploads playlist
        List<YouTubeVideoDTO> videoList = new ArrayList<>();
        String nextPageToken = null;

        do {
            YouTube.PlaylistItems.List playlistRequest = youtube.playlistItems()
                    .list("snippet")
                    .setPlaylistId(uploadsPlaylistId)
                    .setMaxResults(50L)
                    .setPageToken(nextPageToken)
                    .setKey(API_KEY);

            PlaylistItemListResponse response = playlistRequest.execute();
            for (PlaylistItem item : response.getItems()) {
                PlaylistItemSnippet snippet = item.getSnippet();
                videoList.add(new YouTubeVideoDTO(
                        snippet.getTitle(),
                        snippet.getDescription(),
                        snippet.getResourceId().getVideoId(),
                        bestThumbnailUrl(snippet.getThumbnails())
                ));
            }
            nextPageToken = response.getNextPageToken();
        } while (nextPageToken != null);

        return videoList;
    }

    /**
     * Picks the highest-resolution thumbnail YouTube offers for a video, falling
     * back down the size chain: maxres (1280x720) -> standard (640x480) ->
     * high (480x360) -> medium (320x180) -> default (120x90). Only maxres and
     * standard may be absent, so the lower sizes guarantee a non-null result.
     */
    private static String bestThumbnailUrl(ThumbnailDetails thumbnails) {
        if (thumbnails == null) {
            return null;
        }
        for (Thumbnail thumbnail : List.of(
                nullToEmpty(thumbnails.getMaxres()),
                nullToEmpty(thumbnails.getStandard()),
                nullToEmpty(thumbnails.getHigh()),
                nullToEmpty(thumbnails.getMedium()),
                nullToEmpty(thumbnails.getDefault()))) {
            if (thumbnail.getUrl() != null) {
                return thumbnail.getUrl();
            }
        }
        return null;
    }

    private static Thumbnail nullToEmpty(Thumbnail thumbnail) {
        return thumbnail != null ? thumbnail : new Thumbnail();
    }
}
