package com.learning.api.checklistappapi.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(indexes = {@Index(name = "IDX_GUID_CAT", columnList = "guid")})
public class CategoryEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ChecklistItemEntity> checklistItems;
}
