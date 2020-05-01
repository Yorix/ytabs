package com.yorix.ytabs.service;

import com.yorix.ytabs.model.Group;
import com.yorix.ytabs.storage.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    public void save(Group group) {
        groupRepository.save(group);
    }

    public Group findGroup(int id, String ... name) {
        return groupRepository.findById(id).orElseGet(() -> {
            Group newGroup = new Group();
            newGroup.setName(name[0]);
            return newGroup;
        });
    }
}
