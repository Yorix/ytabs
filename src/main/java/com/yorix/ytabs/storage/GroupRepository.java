package com.yorix.ytabs.storage;

import com.yorix.ytabs.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findByName(String name);
}
