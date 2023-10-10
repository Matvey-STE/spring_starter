package com.matveyvs.service;

import com.matveyvs.database.repository.UserRepository;
import com.matveyvs.dto.UserDto;
import com.matveyvs.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapFrom);
    }
}
