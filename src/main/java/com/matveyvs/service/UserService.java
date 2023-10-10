package com.matveyvs.service;

import com.matveyvs.database.repository.UserRepository;
import com.matveyvs.dto.CompanyDto;
import com.matveyvs.dto.UserDto;
import com.matveyvs.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapFrom);
    }
}
