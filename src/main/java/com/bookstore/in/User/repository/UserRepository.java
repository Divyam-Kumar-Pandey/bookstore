package com.bookstore.in.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.in.User.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
