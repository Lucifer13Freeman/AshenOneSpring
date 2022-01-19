package org.ashenone.AshenOne.domain;


import org.ashenone.AshenOne.domain.util.PostHelper;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Enter the post text...")
    @Length(max = 2048, message = "Post text too long (more than 2kB)!")
    private String text;

    @Length(max = 255, message = "Tag too long (more than 255)!")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> likes = new HashSet<>();

    public Post() { }

    public Post(String text, String tag, User user)
    {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public String getAuthorName() { return PostHelper.getAuthorName(author); }

    public User getAuthor() { return author; }

    public void setAuthor(User author) { this.author = author; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public Set<User> getLikes() { return likes; }

    public void setLikes(Set<User> likes) { this.likes = likes; }
}
