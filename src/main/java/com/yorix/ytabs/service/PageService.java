package com.yorix.ytabs.service;

import com.yorix.ytabs.model.Page;
import com.yorix.ytabs.storage.PageRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
public class PageService {
    private final PageRepository pageRepository;
    private final ImageService imageService;

    public PageService(PageRepository pageRepository, ImageService imageService) {
        this.pageRepository = pageRepository;
        this.imageService = imageService;
    }

    public List<Page> getAll() {
        return pageRepository.findAll();
    }

    public Page get(int id) {
        return pageRepository.getOne(id);
    }

    public void save(Page page) throws IOException {

        if (!page.getUrl().startsWith("http"))
            page.setUrl("http://".concat(page.getUrl()));

        if (StringUtils.isEmpty(page.getTitle()))
            page.setTitle(page.getUrl());

        if (page.getImgFilename().startsWith("http")) {
            String imageFilename = imageService.downloadImg(page.getImgFilename());
            page.setImgFilename(imageFilename);
        }

        pageRepository.save(page);
    }
}
