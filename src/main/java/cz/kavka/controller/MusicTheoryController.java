package cz.kavka.controller;

import cz.kavka.dto.MusicTheoryDTO;
import cz.kavka.service.MusicTheoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/music-theory")
public class MusicTheoryController {

    private final MusicTheoryService musicTheoryService;

    @Autowired
    public MusicTheoryController(MusicTheoryService musicTheoryService){
        this.musicTheoryService = musicTheoryService;
    }

    @PostMapping("/create")
    public MusicTheoryDTO create (@RequestBody MusicTheoryDTO musicTheoryDTO){
        return musicTheoryService.create(musicTheoryDTO);
    }
}
