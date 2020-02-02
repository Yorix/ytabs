package com.yorix.ytabs.controller;

import com.yorix.ytabs.model.Group;
import com.yorix.ytabs.model.Page;
import com.yorix.ytabs.service.GroupService;
import com.yorix.ytabs.service.PageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final GroupService groupService;
    private final PageService pageService;

    public MainController(GroupService groupService, PageService pageService) {
        this.groupService = groupService;
        this.pageService = pageService;
    }

    @GetMapping
    public String index(Model model) {
        List<Group> groups = groupService.getGroups();
        model.addAttribute("groups", groups);
        return "index";
    }

    @GetMapping("new-page/")
    public String newPage(Model model, @RequestParam int groupId) {
        List<Group> groups = groupService.getGroups();
        model.addAttribute("groups", groups);
        model.addAttribute("currentGroupId", groupId);
        model.addAttribute("logos", null);
        return "new-page";
    }

    @PostMapping
    public String addPage(
            Page page,
            @RequestParam int groupId,
            @RequestParam String groupName
    ) {
        Group group = groupService.findGroup(groupId, groupName);
        groupService.saveGroup(group);
        page.setGroup(group);
        pageService.addPage(page);
        return "redirect:/";
    }
}
