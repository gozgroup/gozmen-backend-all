package com.gozmen.ucenter.controller;

import com.gozmen.common.api.ApiResponse;
import com.gozmen.common.data.StringValue;
import com.gozmen.common.exception.ServerException;
import com.gozmen.ucenter.controller.model.GetUserInfoResponse;
import com.gozmen.ucenter.service.model.UserInfo;
import com.gozmen.ucenter.service.UserService;
import com.gozmen.ucenter.service.model.UserPersonalInfo;
import com.gozmen.ucenter.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/", method = {RequestMethod.GET})
    public ApiResponse<?> index() {
        throw new ServerException();
    }

    @RequestMapping(path = "/v1/user/getUserInfo", method = {RequestMethod.POST})
    public ApiResponse<?> getUserInfo(@RequestBody StringValue value) throws Exception {
        UserInfo userInfo = userService.getUserInfo(value.getValue());
        GetUserInfoResponse response = new GetUserInfoResponse();
        response.setPortraitUrl(userInfo.getPortraitUrl());
        response.setGender(userInfo.getGender());
        response.setPortraitUrl(userInfo.getPortraitUrl());
        return ApiResponse.success(response);
    }

    @RequestMapping(path = "/v1/user/getPersonalInfo", method = {RequestMethod.POST})
    public ApiResponse<?> getPersonalInfo(@RequestHeader(name = Constants.HEADER_USERNAME) String username,
                                          @RequestBody StringValue value) throws Exception {
        UserPersonalInfo userPersonalInfo = userService.getUserPersonalInfo(username, value.getValue());
        return ApiResponse.success(userPersonalInfo);
    }

    @RequestMapping(path = "/v1/user/modifyNick", method = {RequestMethod.POST})
    public ApiResponse<?> modifyNick(@RequestHeader(name = Constants.HEADER_USERNAME) String username, 
                                     @RequestBody StringValue value) throws Exception {
        userService.modifyNick(username, username, value.getValue());
        return ApiResponse.success();
    }

    @RequestMapping(path = "/v1/user/modifyPortrait", method = {RequestMethod.POST})
    public ApiResponse<?> modifyPortrait(@RequestHeader(name = Constants.HEADER_USERNAME) String username, 
                                         @RequestBody StringValue value) throws Exception {
        userService.modifyPortrait(username, username, value.getValue());
        return ApiResponse.success();
    }

}
