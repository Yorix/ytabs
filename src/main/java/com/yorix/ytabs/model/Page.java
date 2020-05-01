package com.yorix.ytabs.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "page")
@Data
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "TEXT")
    private String url;
    private String title;
    private String imgFilename;
    @ManyToOne
    @JoinColumn(name = "grp_id")
    private Group group;
}
