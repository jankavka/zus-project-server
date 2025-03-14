package cz.kavka.controller;

import cz.kavka.dto.ArticleDTO;
import cz.kavka.service.serviceInterface.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create")
    public ArticleDTO create(ArticleDTO articleDTO){
        return articleService.create(articleDTO);
    }
}
