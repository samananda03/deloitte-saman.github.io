
package com.cloudthat.productsappv2.service;


import com.cloudthat.productsappv2.entity.User;
import com.cloudthat.productsappv2.entity.VerificationToken;
import com.cloudthat.productsappv2.model.UserModel;
import com.cloudthat.productsappv2.model.UserProfile;

public interface UserService {

    User registerUser(UserModel userModel);

    String validateVerificationToken(String token);

    com.cloudthat.productsappv2.entity.VerificationToken generateNewVerificationToken(String oldToken);

    void saveVerificationTokenForUser(String token, User user);

    Boolean existsByEmail(String email);

    UserProfile getUserProfile(Long id);

    UserProfile updateUserProfile(Long id, UserProfile userProfile);

    void deleteUserProfile(Long id);

    UserProfile getUserProfile(String email);

    VerificationToken getVerificationTokenForUser(Long id);


}
