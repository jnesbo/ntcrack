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
    @NotBlank(message = "post cannot be empty")
    @Length(max = 256, message = "Too long")
    private String text;
    @NotBlank(message ="enter tags" )
    @Length(max = 100, message = "enter tags")
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
