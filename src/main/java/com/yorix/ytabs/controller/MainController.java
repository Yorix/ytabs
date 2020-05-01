package com.yorix.ytabs.controller;

import com.yorix.ytabs.model.Group;
import com.yorix.ytabs.model.Page;
import com.yorix.ytabs.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final GroupService groupService;

    public MainController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String index(Model model) {
        List<Group> groups = groupService.getAll();
        model.addAttribute("groups", groups);
        return "index";
    }

    @GetMapping("new-page/{group}/")
    public String newPage(@PathVariable Group group, Model model) {
        List<Group> groups = groupService.getAll();
        Page page = new Page();
        page.setGroup(group);
        model.addAttribute("groups", groups);
        model.addAttribute("page", page);
        return "edit-page";
    }
}
