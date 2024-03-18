package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 추가적인 메서드를 선언하여 사용 가능
    Page<User> findByUsername(String username, Pageable pageable);
}