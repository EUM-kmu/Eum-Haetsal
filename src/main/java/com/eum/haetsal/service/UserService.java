package com.eum.haetsal.service;

import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void create(Long userId){
        User user = User.toEntity(userId);
        userRepository.save(user);
    }
}
