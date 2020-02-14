package com.newbegin.project.newbegin.service;

import com.newbegin.project.newbegin.repository.PostRepository;
import com.newbegin.project.newbegin.repository.TagRepository;
import com.newbegin.project.newbegin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ManagementService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;


    public  Map<String, Integer> zipToMap(List<String> keys, List<Integer> values) {
        Iterator<String> keyIter = keys.iterator();
        Iterator<Integer> valIter = values.iterator();
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(_i -> keyIter.next(), _i -> valIter.next()));
    }

    public Map<String, Integer> getTagsStatistics() {
        List<Integer> count = tagRepository.countTag();
        List<String> tags = tagRepository.getTags();
        return zipToMap(tags, count);
    }

    public Integer countUsers(){
        return userRepository.countUsers();
    }

    public Integer countPostsByUsers(){
        return postRepository.countPostsByUsers();
    }
    public Integer countUsersWithPosts(){
        return postRepository.countUsersWithPosts();
    }

    public Integer countUsersWithoutPosts(){
        return countUsers() - countUsersWithPosts();
    }


    public Map<String, Integer> getPostsStatistics(){
        List<String> date = postRepository.getDate();
        List<Integer> countByDate = postRepository.countByDate();
        return zipToMap(date, countByDate);
    }
    public Map<String, Integer> getUsersStatistics(){
        List<Integer> count = userRepository.countUsersByDate();
        List<String> dates = userRepository.dateCreated();
        return zipToMap(dates,count);
    }
}
