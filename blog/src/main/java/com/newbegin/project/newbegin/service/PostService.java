package com.newbegin.project.newbegin.service;

import com.newbegin.project.newbegin.model.Post;
import com.newbegin.project.newbegin.model.Tag;
import com.newbegin.project.newbegin.model.User;
import com.newbegin.project.newbegin.repository.PostRepository;
import com.newbegin.project.newbegin.repository.TagRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<Post> postList() {

        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void delete(long id) {
        postRepository.deleteById(id);
    }

    public void addNewPost(Post post, String typeTags, User user, Model model) {

        post.setAuthor(user);
        if (typeTags != null && !typeTags.isEmpty()) {
            List<String> tagsList = Arrays.asList(typeTags.split("\\s"));

            for (String t : tagsList) {
                post.addTag(new Tag(t));
            }
        }

        model.addAttribute("post", null);
        postRepository.save(post);
    }

    public List<Post> findPostByText(String text) {
        List<Post> posts = postRepository.findByTextContainsOrderByIdDesc(text);
        if (posts.isEmpty()) {
            posts = findPostsByTag(text);
        }

        return posts;
    }

    public List<String> toptags() {
        return tagRepository.getAllTags();
    }

    public List<Post> findPostsByTag(String textTag) {
        return tagRepository.findPostByTag(textTag);
    }


    public List<Post> getPostFeed(User user) {
        List<Post> result = new ArrayList<>();

        LocalDate c = new LocalDate();
        String date = c.toString();

        Set<User> users = user.getFollowing();
        for (User u : users) {
            for (Post p : postRepository.findPostByAuthor(u)) {
                if (date.equals(p.getPostedAt().toString())) {
                    result.add(p);
                }
            }
        }
        return result;
    }
}
