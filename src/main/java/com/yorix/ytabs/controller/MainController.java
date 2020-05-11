package com.yorix.ytabs.controller;

import com.yorix.ytabs.model.Group;
import com.yorix.ytabs.service.GroupService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
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
}
