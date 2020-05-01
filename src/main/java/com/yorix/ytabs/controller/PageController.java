package com.yorix.ytabs.controller;

import com.yorix.ytabs.model.Group;
import com.yorix.ytabs.model.Page;
import com.yorix.ytabs.service.GroupService;
import com.yorix.ytabs.service.PageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/page/")
public class PageController {
    private final GroupService groupService;
    private final PageService pageService;

    public PageController(GroupService groupService, PageService pageService) {
        this.groupService = groupService;
        this.pageService = pageService;
    }

    @GetMapping("{pageId}/")
    public ModelAndView newPage(@PathVariable("pageId") Page page) {
        ModelAndView modelAndView = new ModelAndView("edit-page");
        List<Group> groups = groupService.getAll();
        if (page == null) {
            page = new Page();
            Group group = groups.isEmpty() ? new Group() : groups.get(0);
            page.setGroup(group);
        }
        modelAndView.addObject("groups", groups);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @PostMapping
    public String postPage(Page page, @RequestParam int groupId, @RequestParam String groupName) throws IOException {
        Group group = groupService.findGroup(groupId, groupName);
        groupService.save(group);
        page.setGroup(group);
        pageService.save(page);
        return "redirect:/";
    }
}
