package com.construcao.financiase.user.service;

import com.construcao.financiase.user.dto.AuthenticatedUser;
import com.construcao.financiase.user.dto.MessageDTO;
import com.construcao.financiase.user.dto.UserDTO;
import com.construcao.financiase.user.entity.User;
import com.construcao.financiase.user.exception.UserEmailAlreadyExistsException;
import com.construcao.financiase.user.exception.UserNameAlreadyExistsException;
import com.construcao.financiase.user.exception.UserNotFoundException;
import com.construcao.financiase.user.mapper.UserMapper;
import com.construcao.financiase.user.repository.UserRepository;
import com.construcao.financiase.user.token.Token;
import com.construcao.financiase.user.token.TokenRepository;
import com.construcao.financiase.user.token.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.construcao.financiase.user.utils.MessageDTOUtils.creationMessage;
import static com.construcao.financiase.user.utils.MessageDTOUtils.updatedMessage;

@Service
public class UserService {
    private final static UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public MessageDTO register(UserDTO userToCreateDTO) {

        verifyIfExists(userToCreateDTO.getUsername(), userToCreateDTO.getEmail());

        User userToCreate = userMapper.toModel(userToCreateDTO);
        userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));

        var createdUser = this.userRepository.save(userToCreate);

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(createdUser);

        var jwtToken = jwtService.generateToken(authenticatedUser);
        var refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        saveUserToken(createdUser, jwtToken);

//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();

        return creationMessage(createdUser);

    }

    public MessageDTO update(Long id, UserDTO userToUpdateDTO) {

        //verificando se o usuario existe no banco
        User foundUser = verifyAndGetIfExists(id);

        //verificando se os dados a serem atualizados sao unicos
        verifyIfExists(userToUpdateDTO.getUsername(), userToUpdateDTO.getEmail());

        //atualizando o id do user para atualizar
        userToUpdateDTO.setId(foundUser.getId());

        User userToUpdate = userMapper.toModel(userToUpdateDTO);

        userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
        //settando a data de criacao do user
        userToUpdate.setCreatedDate(foundUser.getCreatedDate());

        User updatedUser = userRepository.save(userToUpdate);

        return updatedMessage(updatedUser);
    }

    public void delete(Long id) {

        verifyAndGetIfExists(id);

        userRepository.deleteById(id);
    }

    private User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void verifyIfExists(String username, String email) {
        Optional<User> foundUsername = userRepository.findByUsername(username);

        if (foundUsername.isPresent()) {
            throw new UserNameAlreadyExistsException(username);
        }

        Optional<User> foundUserEmail = userRepository.findByEmail(email);

        if (foundUserEmail.isPresent()) {
            throw new UserEmailAlreadyExistsException(email);
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public User verifyAndGetUserIfExists(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
}
