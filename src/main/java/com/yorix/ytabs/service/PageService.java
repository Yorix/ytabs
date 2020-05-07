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

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
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

        pageRepository.save(page);
    }
}
