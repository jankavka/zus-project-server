package cz.kavka.controller;

import cz.kavka.dto.TitleAndContentDTO;
import cz.kavka.service.TitleAndContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/static")
public class TitleAndContentController {

    TitleAndContentServiceImpl titleAndContentService;

    @Autowired
    TitleAndContentController( TitleAndContentServiceImpl titleAndContentService){
        this.titleAndContentService = titleAndContentService;
    }

    @PutMapping("/update/{key}")
    public Map<String, TitleAndContentDTO> updateContent(@PathVariable String key, @RequestBody TitleAndContentDTO titleAndContentDTO) throws IOException {
        return titleAndContentService.updateContent(key, titleAndContentDTO);
    }

    @GetMapping("/{key}")
    public Optional<TitleAndContentDTO> showSection(@PathVariable String key) throws IOException{
        return titleAndContentService.getSection(key);
    }

    @GetMapping("/keys")
    public List<String> listOfKeys (){
        return List.of("history-and-present", "group-training-schedule", "music-theory", "personal-data-protection", "school-fee", "study-focus");
    }
}
