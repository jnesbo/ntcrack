package com.newbegin.project.newbegin.repository;

import com.newbegin.project.newbegin.model.Post;
import com.newbegin.project.newbegin.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    void deleteById(Long id);

    @Override
    List<Post> findAll(Sort sort);

    List<Post> findByTextContainsOrderByIdDesc(String text);

    @Query("select count(p) from Post p")
    Integer countPostsByUsers();

    @Query("select count(distinct p.author) from Post p")
    Integer countUsersWithPosts();

    @Query("select p.postedAt from Post p group by p.postedAt")
    List<String> getDate();

    @Query("select count(p) from Post p group by p.postedAt ")
    List<Integer> countByDate();

    @Query("select p from Post p where p.author=:user order by  p.id desc")
    List<Post> findPostByAuthor(@Param("user") User user);
}
