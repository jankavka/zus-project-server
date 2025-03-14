package cz.kavka.controller;

import cz.kavka.service.MusicTheoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/music-theory")
public class MusicTheoryController {

    private MusicTheoryService musicTheoryService;

    public MusicTheoryController(MusicTheoryService musicTheoryService){
        this.musicTheoryService = musicTheoryService;
    }


}
