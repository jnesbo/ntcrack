package com.newbegin.project.newbegin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String textTag;

    @Temporal(TemporalType.DATE)
    private Date postedAt = new Date();
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public Tag(String textTag) {
        this.textTag = textTag;
    }
}
