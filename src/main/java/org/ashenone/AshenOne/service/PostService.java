package org.ashenone.AshenOne.service;

import org.ashenone.AshenOne.domain.User;
import org.ashenone.AshenOne.domain.dto.PostDto;
import org.ashenone.AshenOne.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService
{
    @Autowired
    private PostRepo postRepo;

    public Page<PostDto> postList(Pageable pageable, String filter, User user)
    {
        if (filter != null && !filter.isEmpty()) {
            return postRepo.findByTag(filter, pageable, user);
        } else {
            return postRepo.findAll(pageable, user);
        }
    }

    public Page<PostDto> postListForUser(Pageable pageable, User currentUser, User author)
    {
        return postRepo.findByUser(pageable, author, currentUser);
    }
}
