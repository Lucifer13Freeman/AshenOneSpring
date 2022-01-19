package org.ashenone.AshenOne.repos;

import org.ashenone.AshenOne.domain.Post;
import org.ashenone.AshenOne.domain.User;
import org.ashenone.AshenOne.domain.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepo extends CrudRepository<Post, Long>
{
    @Query("select new org.ashenone.AshenOne.domain.dto.PostDto(" +
            "   p, " +
            "   count(pl), " +
            "   sum(case when pl = :user then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.likes pl " +
            "group by p")
    Page<PostDto> findAll(Pageable pageable, @Param("user") User user);

    @Query("select new org.ashenone.AshenOne.domain.dto.PostDto(" +
            "   p, " +
            "   count(pl), " +
            "   sum(case when pl = :user then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.likes pl " +
            "where p.tag = :tag " +
            "group by p")
    Page<PostDto> findByTag(@Param("tag") String tag, Pageable pageable, @Param("user") User user);

    @Query("select new org.ashenone.AshenOne.domain.dto.PostDto(" +
            "   p, " +
            "   count(pl), " +
            "   sum(case when pl = :user then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.likes pl " +
            "where p.text = :text " +
            "group by p")
    Page<PostDto> findByText(String text, Pageable pageable);

    @Query("select new org.ashenone.AshenOne.domain.dto.PostDto(" +
            "   p, " +
            "   count(pl), " +
            "   sum(case when pl = :user then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.likes pl " +
            "where p.author = :author " +
            "group by p")
    Page<PostDto> findByUser(Pageable pageable, @Param("author") User author, @Param("user") User user);
}
