package com.newbegin.project.newbegin.model.util;

import com.newbegin.project.newbegin.model.User;

public abstract class PostHelper {
    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
    }
}

