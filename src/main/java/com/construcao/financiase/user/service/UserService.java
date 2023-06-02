package com.construcao.financiase.user.service;

import com.construcao.financiase.user.dto.MessageDTO;
import com.construcao.financiase.user.dto.UserDTO;
import com.construcao.financiase.user.entity.User;
import com.construcao.financiase.user.exception.UserEmailAlreadyExistsException;
import com.construcao.financiase.user.exception.UserNameAlreadyExistsException;
import com.construcao.financiase.user.mapper.UserMapper;
import com.construcao.financiase.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final static UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {

        verifyIsExists(userToCreateDTO.getUsername(), userToCreateDTO.getEmail());

        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);

        return creationMessage(createdUser);
    }



    private void verifyIsExists(String username, String email) {
        Optional<User> foundUsername = userRepository.findByUsername(username);

        if(foundUsername.isPresent()){
            throw new UserNameAlreadyExistsException(username);
        }

        Optional<User> foundUserEmail = userRepository.findByEmail(email);

        if(foundUserEmail.isPresent()){
            throw new UserEmailAlreadyExistsException(email);
        }
    }

    private MessageDTO creationMessage(User createdUser) {

        String username = createdUser.getUsername();
        Long id = createdUser.getId();

        String message = String.format("User %s with ID %s is successfully created", username , id);

        return MessageDTO.builder()
                .message(message)
                .build();
    }

}
