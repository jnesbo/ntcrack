package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "required")
    @Length(max = 256,message = "Too long")
    private String text;
    @Length(max = 100)
    private String tag;
    public String getAuthorName(){
        return author!=null ? author.getUsername():"<none>";
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Post(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }
}
