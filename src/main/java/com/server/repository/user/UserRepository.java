package com.server.repository.user;

import com.server.model.user.User;

public interface UserRepository {
    void save(User user);
    User getByEmail(String email);
    void clear();
}