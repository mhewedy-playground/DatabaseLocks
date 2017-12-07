package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void createNew() {

        User user = new User();
        user.setUsername("user-" + LocalDateTime.now());
        user.setAge(30);

        userRepository.save(user);

        try {
            log.info("sleeping 20 sec in the same transaction as of insert...");
            Thread.sleep(5000);
            throw new RuntimeException("abbas");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        try {
            log.info("sleeping 1 sec before select to make sure insert is executed first");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userRepository.findOne(1L);
    }

    public long countUsers() {
        try {
            log.info("sleeping 1 sec before select to make sure insert is executed first");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userRepository.count();
    }
}
