package com.bookmyshow.BMSproject.Repositories;

import com.bookmyshow.BMSproject.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
