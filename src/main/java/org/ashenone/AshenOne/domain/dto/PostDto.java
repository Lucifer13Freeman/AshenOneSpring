package org.ashenone.AshenOne.domain.dto;

import org.ashenone.AshenOne.domain.Post;
import org.ashenone.AshenOne.domain.User;
import org.ashenone.AshenOne.domain.util.PostHelper;

public class PostDto
{
    private Long id;
    private String text;
    private String tag;
    private User author;
    private String filename;
    private Long likes;
    private Boolean isLiked;

    public PostDto(Post post, Long likes, Boolean isLiked)
    {
        this.id = post.getId();
        this.text = post.getText();
        this.tag = post.getTag();
        this.author = post.getAuthor();
        this.filename = post.getFilename();
        this.likes = likes;
        this.isLiked = isLiked;
    }

    public String getAuthorName() { return PostHelper.getAuthorName(author); }

    public Long getId() { return id; }

    public String getText() { return text; }

    public String getTag() { return tag; }

    public User getAuthor() { return author; }

    public String getFilename() { return filename; }

    public Long getLikes() { return likes; }

    public Boolean getIsLiked() { return isLiked; }

    @Override
    public String toString()
    {
        return "PostDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", isLiked=" + isLiked +
                '}';
    }
}
