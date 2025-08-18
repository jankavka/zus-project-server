package service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import cz.kavka.dto.YouTubeVideoDTO;
import cz.kavka.service.YoutubeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class YoutubeServiceImplTest {

    @Mock YouTube youtube;
    @Mock YouTube.Channels channels;
    @Mock YouTube.Channels.List channelsList;

    @Mock YouTube.PlaylistItems playlistItems;
    @Mock YouTube.PlaylistItems.List playlistList;

    YoutubeServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        service = new YoutubeServiceImpl(youtube);
        // inject @Value fields
        ReflectionTestUtils.setField(service, "API_KEY", "TEST_KEY");
        ReflectionTestUtils.setField(service, "channelId", "CHANNEL_123");

        // only channel stubs here (used by all tests)
        when(youtube.channels()).thenReturn(channels);
        when(channels.list("contentDetails")).thenReturn(channelsList);
        when(channelsList.setId(anyString())).thenReturn(channelsList);
        when(channelsList.setKey(anyString())).thenReturn(channelsList);
    }

    @Test
    void fetchAllVideos_singlePage_returnsMappedVideos() throws Exception {
        // Channel -> uploads playlist id
        when(channelsList.execute()).thenReturn(channelRespWithUploads("UPLOADS_PL"));

        // Playlist stubs (only needed in this test)
        when(youtube.playlistItems()).thenReturn(playlistItems);
        when(playlistItems.list("snippet")).thenReturn(playlistList);
        when(playlistList.setPlaylistId(anyString())).thenReturn(playlistList);
        when(playlistList.setMaxResults(anyLong())).thenReturn(playlistList);
        when(playlistList.setPageToken(any())).thenReturn(playlistList);
        when(playlistList.setKey(anyString())).thenReturn(playlistList);

        // single page, no next token
        when(playlistList.execute()).thenReturn(
                page(List.of(
                        item("v1", "T1", "D1", "http://thumb/1.jpg"),
                        item("v2", "T2", "D2", "http://thumb/2.jpg")
                ), null)
        );

        List<YouTubeVideoDTO> out = service.fetchAllVideos();

        assertEquals(2, out.size());
        assertEquals("T1", out.get(0).getTitle());
        assertEquals("D2", out.get(1).getDescription());
        assertEquals("v2", out.get(1).getVideoId());
        assertEquals("http://thumb/1.jpg", out.get(0).getThumbnailUrl());

        verify(channelsList).setId("CHANNEL_123");
        verify(channelsList).setKey("TEST_KEY");
        verify(playlistList).setPlaylistId("UPLOADS_PL");
        verify(playlistList).setKey("TEST_KEY");
    }

    @Test
    void fetchAllVideos_withPagination_fetchesAllPages() throws Exception {
        when(channelsList.execute()).thenReturn(channelRespWithUploads("UPLOADS_PL"));

        when(youtube.playlistItems()).thenReturn(playlistItems);
        when(playlistItems.list("snippet")).thenReturn(playlistList);
        when(playlistList.setPlaylistId(anyString())).thenReturn(playlistList);
        when(playlistList.setMaxResults(anyLong())).thenReturn(playlistList);
        when(playlistList.setPageToken(any())).thenReturn(playlistList);
        when(playlistList.setKey(anyString())).thenReturn(playlistList);

        PlaylistItemListResponse page1 = page(List.of(item("v1", "A", "a", "u1")), "NEXT");
        PlaylistItemListResponse page2 = page(List.of(item("v2", "B", "b", "u2")), null);
        when(playlistList.execute()).thenReturn(page1, page2);

        List<YouTubeVideoDTO> out = service.fetchAllVideos();

        assertEquals(List.of("v1", "v2"), out.stream().map(YouTubeVideoDTO::getVideoId).toList());
        verify(playlistList, atLeastOnce()).setPageToken(null);
        verify(playlistList).setPageToken("NEXT");
        verify(playlistList, times(2)).execute();
    }

    @Test
    void fetchAllVideos_propagatesIOException_fromChannels() throws Exception {
        when(channelsList.execute()).thenThrow(new IOException("boom"));

        IOException ex = assertThrows(IOException.class, () -> service.fetchAllVideos());
        assertTrue(ex.getMessage().contains("boom"));

        // ensures we didn't even try playlist calls
        verify(youtube, never()).playlistItems();
    }

    @Test
    void fetchAllVideos_propagatesIOException_fromPlaylist() throws Exception {
        when(channelsList.execute()).thenReturn(channelRespWithUploads("UPLOADS_PL"));

        when(youtube.playlistItems()).thenReturn(playlistItems);
        when(playlistItems.list("snippet")).thenReturn(playlistList);
        when(playlistList.setPlaylistId(anyString())).thenReturn(playlistList);
        when(playlistList.setMaxResults(anyLong())).thenReturn(playlistList);
        when(playlistList.setPageToken(any())).thenReturn(playlistList);
        when(playlistList.setKey(anyString())).thenReturn(playlistList);

        when(playlistList.execute()).thenThrow(new IOException("page fail"));

        IOException ex = assertThrows(IOException.class, () -> service.fetchAllVideos());
        assertTrue(ex.getMessage().contains("page fail"));
    }

    // ---------- helpers ----------

    private static ChannelListResponse channelRespWithUploads(String uploadsId) {
        return new ChannelListResponse().setItems(List.of(
                new Channel().setContentDetails(
                        new ChannelContentDetails().setRelatedPlaylists(
                                new ChannelContentDetails.RelatedPlaylists().setUploads(uploadsId)
                        )
                )
        ));
    }

    private static PlaylistItemListResponse page(List<PlaylistItem> items, String next) {
        return new PlaylistItemListResponse().setItems(items).setNextPageToken(next);
    }

    private static PlaylistItem item(String videoId, String title, String desc, String thumbUrl) {
        ResourceId rid = new ResourceId().setVideoId(videoId);
        Thumbnail def = new Thumbnail().setUrl(thumbUrl);
        ThumbnailDetails thumbs = new ThumbnailDetails().setDefault(def);
        PlaylistItemSnippet sn = new PlaylistItemSnippet()
                .setTitle(title)
                .setDescription(desc)
                .setResourceId(rid)
                .setThumbnails(thumbs);
        return new PlaylistItem().setSnippet(sn);
    }
}
