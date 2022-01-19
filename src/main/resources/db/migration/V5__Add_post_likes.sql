create table post_likes (
  user_id bigint not null references users,
  post_id bigint not null references posts,
  primary key (user_id, post_id)
)