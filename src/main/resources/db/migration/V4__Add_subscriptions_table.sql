create table user_subscriptions (
    profile_id int8 not null references users,
    follower_id int8 not null references users,
    primary key (profile_id, follower_id)
)