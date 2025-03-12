package cz.kavka.controller;

import cz.kavka.dto.HistoryAndPresentDTO;
import cz.kavka.service.HistoryAndPresentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history-and-present")
public class HistoryAndPresentController {

    HistoryAndPresentService historyAndPresentService;

    @Autowired
    public HistoryAndPresentController(HistoryAndPresentService historyAndPresentService){
        this.historyAndPresentService = historyAndPresentService;
    }

    @PostMapping("/create")
    public HistoryAndPresentDTO create (HistoryAndPresentDTO historyAndPresentDTO){
        return historyAndPresentService.create(historyAndPresentDTO);
    }

}
