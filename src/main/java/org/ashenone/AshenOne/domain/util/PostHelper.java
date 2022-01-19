package org.ashenone.AshenOne.domain.util;

import org.ashenone.AshenOne.domain.User;

public class PostHelper
{
    public static String getAuthorName(User author)
    {
        return author != null ? author.getUsername() : "<none>";
    }
}
