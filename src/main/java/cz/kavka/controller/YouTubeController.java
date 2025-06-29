package cz.kavka.controller;

import cz.kavka.dto.YouTubeVideoDTO;
import cz.kavka.service.serviceinterface.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/youtube")
public class YouTubeController {

    private final YouTubeService youTubeService;

    @Autowired
    public YouTubeController(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @GetMapping("/videos")
    public List<YouTubeVideoDTO> getVideos() throws IOException {
        return youTubeService.fetchAllVideos();
    }
}
