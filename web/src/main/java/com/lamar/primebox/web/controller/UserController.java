package com.lamar.primebox.web.controller;

import com.lamar.primebox.web.dto.model.UserBasicDto;
import com.lamar.primebox.web.dto.model.UserDto;
import com.lamar.primebox.web.dto.request.UserUpdateRequest;
import com.lamar.primebox.web.dto.response.UserDeleteResponse;
import com.lamar.primebox.web.dto.response.UserUpdateResponse;
import com.lamar.primebox.web.model.User;
import com.lamar.primebox.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH})
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PatchMapping("")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateRequest updateRequest) throws Exception {
        final UserBasicDto userBasicDto = modelMapper.map(updateRequest, UserBasicDto.class)
                .setUsername(getUsernameFromSecurityContext());
        final UserDto userDto = this.userService.updateUser(userBasicDto);
        final UserUpdateResponse updateResponse = modelMapper.map(userDto, UserUpdateResponse.class);
        return ResponseEntity.ok(updateResponse);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deactivateUser() throws Exception {
        final UserDto userDto = userService.deactivateUser(getUsernameFromSecurityContext());
        final UserDeleteResponse userDeleteResponse = modelMapper.map(userDto, UserDeleteResponse.class);
        return ResponseEntity.ok(userDeleteResponse);
    }

    private String getUsernameFromSecurityContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }

}
