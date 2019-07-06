package com.gozmen.ucenter.service;

import com.gozmen.common.exception.NoPermissionException;
import com.gozmen.common.utils.StringUtils;
import com.gozmen.ucenter.domain.model.User;
import com.gozmen.ucenter.repository.UserRepository;
import com.gozmen.ucenter.service.exception.UserNotFoundException;
import com.gozmen.ucenter.service.exception.UsernameExistsException;
import com.gozmen.ucenter.service.model.UserAuthenticationInfo;
import com.gozmen.ucenter.service.model.UserInfo;
import com.gozmen.ucenter.service.model.UserPersonalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserInfo getUserInfo(String username) throws UserNotFoundException {
        User user = getUserOrThrow(username);
        return convertUserInfoFrom(user);
    }

    private UserInfo convertUserInfoFrom(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(user.getName());
        userInfo.setNick(user.getNick());
        userInfo.setPortraitUrl(user.getPortraitUrl());
        userInfo.setGender(user.getGender());
        return userInfo;
    }

    void createUser(String username, String password, String nick, int gender, String salt) throws UsernameExistsException, IllegalArgumentException {
        User user = getUser(username);
        if (user !=  null) {
            throw new UsernameExistsException();
        }

        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("password is empty");
        }

        if (StringUtils.isEmpty(salt)) {
            throw new IllegalArgumentException("salt is empty");
        }

        user = new User();
        user.setGender(gender);
        user.setPassword(password);
        user.setNick(nick);
        user.setGender(gender);
        user.setName(username);
        userRepository.save(user);

    }

    // 自己或者管理员才能操作
    public UserPersonalInfo getUserPersonalInfo(String operator, String username)throws NoPermissionException, UserNotFoundException{
        if (!Objects.equals(operator, username)) {
            throw new NoPermissionException();
        }

        return convertPersonalInfoFrom(getUserOrThrow(username));
    }


    private UserPersonalInfo convertPersonalInfoFrom(User user) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        userPersonalInfo.setUsername(user.getName());
        userPersonalInfo.setNick(user.getNick());
        userPersonalInfo.setPortraitUrl(user.getPortraitUrl());
        userPersonalInfo.setGender(user.getGender());
        userPersonalInfo.setEmail(user.getEmail());
        return userPersonalInfo;
    }

    public void modifyNick(String operator, String username, String newNick) throws NoPermissionException, UserNotFoundException {
        if (!Objects.equals(operator, username)) {
            throw new NoPermissionException();
        }

        User user = getUserOrThrow(username);
        if (!Objects.equals(user.getNick(), newNick)) {
            user.setNick(newNick);
            userRepository.save(user);
        }
    }

    public void modifyPortrait(String operator, String username, String newPortrait) throws NoPermissionException, UserNotFoundException {
        if (!Objects.equals(operator, username)) {
            throw new NoPermissionException();
        }

        User user = getUserOrThrow(username);
        if (!Objects.equals(user.getPortraitUrl(), newPortrait)) {
            user.setPortraitUrl(newPortrait);
            userRepository.save(user);
        }
    }

    private User getUserOrThrow(String username) throws UserNotFoundException {
        User user = getUser(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    private User getUser(String username) {
        return userRepository.findUserByName(username);
    }

    UserAuthenticationInfo getUserAuthenticationInfo(String username) throws UserNotFoundException {
        User user = getUserOrThrow(username);

        UserAuthenticationInfo authenticationInfo = new UserAuthenticationInfo();
        authenticationInfo.setUsername(user.getName());
        authenticationInfo.setPassword(user.getPassword());
        authenticationInfo.setSalt(user.getSalt());
        return authenticationInfo;
    }


}
