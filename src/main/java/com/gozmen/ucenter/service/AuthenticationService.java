package com.gozmen.ucenter.service;

import com.gozmen.common.exception.ServerException;
import com.gozmen.ucenter.service.exception.AuthenticationFailedException;
import com.gozmen.ucenter.service.exception.UserNotFoundException;
import com.gozmen.ucenter.service.exception.UsernameExistsException;
import com.gozmen.ucenter.service.model.UserAuthenticationInfo;
import com.gozmen.ucenter.utils.AuthenticationUtils;
import com.gozmen.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenManager tokenManager;

    @Autowired
    UserService userService;

    public String getUsernameBy(String token) {

        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return tokenManager.getUsernameByToken(token);
    }

    public void register(String username, String password, String nick, int gender) throws IllegalArgumentException , UsernameExistsException {

        if (!AuthenticationUtils.matchUsername(username)) {
            throw new IllegalArgumentException("username is invalid");
        }
//        if (!AuthenticationUtils.matchEmail(registerRequest.getEmail())) {
//            throw new IllegalArgumentException("email is invalid");
//        }

        if (!AuthenticationUtils.matchPassword(password)) {
            throw new IllegalArgumentException("password is invalid");
        }

        try {
            final String salt = StringUtils.randomString(4);
            final String encryptedPassword = AuthenticationUtils.encryptPassword(password, salt);
            userService.createUser(username, encryptedPassword, nick, gender, salt);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ServerException();
        }


//        if (StringUtils.isNullOrEmpty(registerRequest.getCode())) {
//            throw new IllegalArgumentException("code is invalid");
//        }

//        if (!registerRequest.getCode().equals(getEmailCodeKey(registerRequest.getEmail()))) {
//            throw new IllegalArgumentException("code is invalid");
//        }

    }



    public String login(String username, String password) throws UserNotFoundException, AuthenticationFailedException {
        UserAuthenticationInfo userAuthenticationInfo = userService.getUserAuthenticationInfo(username);
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("pwd is empty");
        }

        try {
            final String encryptedPassword = AuthenticationUtils.encryptPassword(password, userAuthenticationInfo.getSalt());
            if (Objects.equals(encryptedPassword, userAuthenticationInfo.getPassword())) {
                return tokenManager.createTokenFor(username);
            } else {
                throw new AuthenticationFailedException();
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ServerException();
        }
    }

    public void logout(String username)  {
        tokenManager.deleteTokenFor(username);
    }


}
