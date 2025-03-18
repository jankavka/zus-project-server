package cz.kavka.controller;

import cz.kavka.dto.ArticleDTO;
import cz.kavka.service.serviceinterface.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create")
    public ArticleDTO create(@RequestBody ArticleDTO articleDTO){
        return articleService.createArticle(articleDTO);
    }

    @GetMapping
    public List<ArticleDTO> showAll(){
        return articleService.getAll();
    }

    @GetMapping("/{id}")
    public ArticleDTO show(@PathVariable Long id){
        return articleService.getArticle(id);
    }

    @PutMapping("/edit/{id}")
    public ArticleDTO edit(@RequestBody ArticleDTO articleDTO, @PathVariable Long id){
        return articleService.editArticle(articleDTO, id);
    }

    @DeleteMapping("/delete/{id}")
    public ArticleDTO remove (@PathVariable Long id){
        return articleService.deleteArticle(id);
    }


}
