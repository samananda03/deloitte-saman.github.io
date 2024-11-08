
package com.cloudthat.productsappv2.controller;


import com.cloudthat.productsappv2.model.ApiResponse;
import com.cloudthat.productsappv2.model.UserProfile;
import com.cloudthat.productsappv2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ApiResponse> getUserProfile(@PathVariable("userId") Long userId){
        UserProfile userProfile = userService.getUserProfile(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User Fetched Successfully", userProfile), HttpStatus.OK);
    }


    @PutMapping("/profile/{id}")
    public ResponseEntity<ApiResponse> updateUserProfile(@PathVariable("id") Long id, @RequestBody UserProfile userProfile){
        UserProfile updatedUser = userService.updateUserProfile(id, userProfile);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User Updated Successfully", updatedUser), HttpStatus.OK);
    }


    @DeleteMapping("/profile/{id}")
    public ResponseEntity<ApiResponse> deleteUserProfile(@PathVariable("id") Long id){
        userService.deleteUserProfile(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
    }


}
