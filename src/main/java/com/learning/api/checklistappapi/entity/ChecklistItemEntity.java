package com.learning.api.checklistappapi.entity;



import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity(name = "ChecklistItem")
@Table(indexes = {@Index(name = "IDX_GUID_CKIT", columnList = "guid")})
public class ChecklistItemEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ChecklistItemId;

    private Boolean isCompleted;

    private String description;

    private LocalDate deadline;

    private LocalDate postedDate;

    @ManyToOne
    private CategoryEntity category;
}
