package com.epam.esm.repository;

import com.epam.esm.Certificate;
import com.epam.esm.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User>{
    long getCount();
}
