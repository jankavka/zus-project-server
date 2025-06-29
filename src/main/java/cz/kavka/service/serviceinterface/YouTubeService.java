package cz.kavka.service.serviceinterface;


import cz.kavka.dto.YouTubeVideoDTO;

import java.io.IOException;
import java.util.List;

public interface YouTubeService {
    List<YouTubeVideoDTO> fetchAllVideos() throws IOException;
}
