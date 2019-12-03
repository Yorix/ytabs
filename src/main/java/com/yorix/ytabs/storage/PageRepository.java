package com.yorix.ytabs.storage;

import com.yorix.ytabs.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Integer> {
}
