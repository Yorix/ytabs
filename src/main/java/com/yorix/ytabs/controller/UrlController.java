package com.yorix.ytabs.controller;

import com.yorix.ytabs.service.UrlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url/")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("get-logo/")
    public String[] getImages(@RequestParam("url") String rawUrl) {
        return urlService.getImages(rawUrl);
    }
}
