package br.com.youbook.youbook.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
    @GetMapping("/searchPage")
    public String searchPage() {
        return "searchPage";
    }
}
