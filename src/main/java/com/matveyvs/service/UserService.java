package com.matveyvs.service;

import com.matveyvs.database.repository.UserRepository;
import com.matveyvs.dto.CreateUserDto;
import com.matveyvs.dto.UserDto;
import com.matveyvs.entity.AccessType;
import com.matveyvs.entity.User;
import com.matveyvs.listener.EntityEventAfter;
import com.matveyvs.listener.EntityEventBefore;
import com.matveyvs.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Optional<UserDto> findById(Long id) {
        applicationEventPublisher.publishEvent(new EntityEventBefore("Find user DTO by ID " + id, AccessType.READ));
        return userRepository.findById(id)
                .map(entity -> {
                    applicationEventPublisher.publishEvent(new EntityEventAfter(entity, AccessType.READ));
                    return userMapper.mapFrom(entity);
                });
    }

    public Optional<UserDto> save(CreateUserDto createUserDto) {
        applicationEventPublisher.publishEvent(new EntityEventBefore(createUserDto, AccessType.CREATE));
        User save = userRepository.save(userMapper.createFrom(createUserDto));
        Optional<UserDto> userDto = Optional.ofNullable(userMapper.mapFrom(save));
        if (userDto.isPresent()){
            applicationEventPublisher.publishEvent(new EntityEventAfter(userDto, AccessType.CREATE));
        }
        return userDto;
    }

    public boolean update(UserDto userDto) {
        applicationEventPublisher.publishEvent(new EntityEventBefore(userDto, AccessType.UPDATE));
        boolean update = userRepository.update(userMapper.mapFrom(userDto));
        if (update){
            applicationEventPublisher.publishEvent(new EntityEventAfter(userDto, AccessType.UPDATE));
        }
        return update;
    }

    public boolean delete(Long id) {
        Optional<User> byId = userRepository.findById(id);
        applicationEventPublisher.publishEvent(new EntityEventBefore(byId, AccessType.DELETE));
        boolean delete = userRepository.delete(id);
        if(delete){
            applicationEventPublisher.publishEvent(new EntityEventAfter(byId, AccessType.DELETE));
        }
        return delete;
    }
}
