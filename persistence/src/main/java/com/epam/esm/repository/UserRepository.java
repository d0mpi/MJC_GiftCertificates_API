package com.epam.esm.repository;

import com.epam.esm.User;

public interface UserRepository extends Repository<User> {
    boolean exists(User user);
}
