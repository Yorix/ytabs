package com.yorix.ytabs.controller;

import com.yorix.ytabs.model.Group;
import com.yorix.ytabs.model.Page;
import com.yorix.ytabs.service.GroupService;
import com.yorix.ytabs.service.ImageService;
import com.yorix.ytabs.service.PageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/page/", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
public class PageController {
    private final GroupService groupService;
    private final PageService pageService;
    private final ImageService imageService;

    public PageController(GroupService groupService, PageService pageService, ImageService imageService) {
        this.groupService = groupService;
        this.pageService = pageService;
        this.imageService = imageService;
    }

    @GetMapping("{pageId}/")
    public String newPage(@PathVariable("pageId") Page page, Model model) {
        List<Group> groups = groupService.getAll();

        if (page == null) {
            page = new Page();
            Group group = groups.isEmpty() ? new Group() : groups.get(0);
            page.setGroup(group);
        }

        model.addAttribute("groups", groups);
        model.addAttribute("page", page);
        return "edit-page";
    }

    @GetMapping("0/{group}/")
    public String newPage(@PathVariable Group group, Model model) {
        List<Group> groups = groupService.getAll();
        Page page = new Page();
        page.setGroup(group);
        model.addAttribute("groups", groups);
        model.addAttribute("page", page);
        return "edit-page";
    }

    @PostMapping
    public String postPage(Page page, @RequestParam String imgUrl, @RequestParam int groupId, @RequestParam String groupName) throws IOException {
        Group group = groupService.findGroup(groupId, groupName);
        groupService.save(group);
        page.setGroup(group);

        if (!StringUtils.isEmpty(imgUrl)) {
            String imageFilename = imageService.downloadImg(imgUrl, page.getImgFilename());
            page.setImgFilename(imageFilename);
        }

        pageService.save(page);
        return "redirect:/";
    }
}
