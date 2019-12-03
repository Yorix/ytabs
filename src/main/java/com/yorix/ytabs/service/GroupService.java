package com.yorix.ytabs.service;

import com.yorix.ytabs.model.Group;
import com.yorix.ytabs.model.Page;
import com.yorix.ytabs.storage.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    public Group findGroup(int id, String name) {
        return groupRepository.findById(id).orElseGet(() -> {
            Group newGroup = new Group();
            newGroup.setName(name);
            return newGroup;
        });
    }
}
