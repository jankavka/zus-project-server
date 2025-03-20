package cz.kavka.controller;

import cz.kavka.dto.MusicTheoryDTO;
import cz.kavka.service.MusicTheoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/api/music-theory")
public class MusicTheoryController {

    private final MusicTheoryService musicTheoryService;

    @Autowired
    public MusicTheoryController(MusicTheoryService musicTheoryService){
        this.musicTheoryService = musicTheoryService;
    }

    @PostMapping("/create")
    public MusicTheoryDTO create (@RequestBody MusicTheoryDTO musicTheoryDTO){
        return musicTheoryService.createOrEdit(musicTheoryDTO);
    }

    @PutMapping("/edit")
    public MusicTheoryDTO edit (@RequestBody MusicTheoryDTO musicTheoryDTO){
        return musicTheoryService.createOrEdit(musicTheoryDTO);
    }

    @GetMapping
    public MusicTheoryDTO showMusicTheory(){
        return musicTheoryService.get();
    }

}
