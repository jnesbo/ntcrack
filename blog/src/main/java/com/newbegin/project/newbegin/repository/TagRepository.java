package com.newbegin.project.newbegin.repository;

import com.newbegin.project.newbegin.model.Post;
import com.newbegin.project.newbegin.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    @Query("select distinct t.textTag from Tag t  where t.textTag!='' order by rand()")
    List<String> getAllTags();

    @Query("select t.post from Tag t where t.textTag=:textTag order by t.postedAt desc ")
    List<Post> findPostByTag(@Param("textTag") String textTag);

    @Query("select count(t.textTag) from Tag t group by t.textTag")
    List<Integer> countTag();

    @Query("select t.textTag from Tag t group by t.textTag")
    List<String> getTags();

}
