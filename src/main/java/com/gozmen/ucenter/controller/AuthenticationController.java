package com.gozmen.ucenter.controller;

import com.gozmen.common.api.ApiResponse;
import com.gozmen.common.data.ObjectValue;
import com.gozmen.ucenter.controller.model.LoginRequest;
import com.gozmen.ucenter.controller.model.RegisterRequest;
import com.gozmen.ucenter.service.AuthenticationService;
import com.gozmen.ucenter.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(path = "/v1/auth/register", method = {RequestMethod.POST})
    public ApiResponse<?> register(@RequestBody RegisterRequest request) throws Exception {
        authenticationService.register(request.getUsername(), request.getPassword(), request.getNick(), request.getGender());
        return ApiResponse.success();

    }

    @RequestMapping(path = "/v1/auth/login", method = {RequestMethod.POST})
    public ApiResponse<?> login(@RequestBody LoginRequest request) throws Exception {
        String token = authenticationService.login(request.getUsername(), request.getPassword());
        return ApiResponse.success(ObjectValue.stringVlue(token));
    }

    @RequestMapping(path = "/v1/auth/logout", method = {RequestMethod.POST})
    public ApiResponse<?> logout(@RequestHeader(name = Constants.HEADER_USERNAME) String username) {
        authenticationService.logout(username);
        return ApiResponse.success();
    }


}
