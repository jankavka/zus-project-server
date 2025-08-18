package cz.kavka.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class SiteSearchController {

    @Value("${app.search.siteDomain}")
    private String siteDomain;

    @GetMapping("/search")
    public String redirectToGoogle(@RequestParam("query") String query){
        String googleQuery = "site:" + siteDomain + " " + (query == null ? " " : query.trim());
        String url = "https://www.google.com/search?q=" + URLEncoder.encode(googleQuery, StandardCharsets.UTF_8);

        return "redirect:" + url;
    }
}
