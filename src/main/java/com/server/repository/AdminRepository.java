package com.server.repository;

import com.server.model.Admin;

public interface AdminRepository {
    void save(Admin admin);
    Admin getByEmail(String email);
}
