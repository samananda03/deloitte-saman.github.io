
package com.cloudthat.productsappv2.controller;


import com.cloudthat.productsappv2.entity.User;
import com.cloudthat.productsappv2.entity.VerificationToken;
import com.cloudthat.productsappv2.event.RegistrationCompleteEvent;
import com.cloudthat.productsappv2.model.*;
import com.cloudthat.productsappv2.service.CustomUserDetailsService;
import com.cloudthat.productsappv2.service.UserService;
import com.cloudthat.productsappv2.utility.JWTUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager  authenticationProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("register")
    public ResponseEntity<ApiResponse> registeruser(@RequestBody UserModel userModel, final HttpServletRequest request){
        // add check for email exists in DB
        if(userService.existsByEmail(userModel.getEmail())){
            return new ResponseEntity<ApiResponse>(new ApiResponse("Email is already taken!", false), HttpStatus.BAD_REQUEST);
        }
        User user = userService.registerUser(userModel);
        // Instead of publishing from backend we will send the verification link from the front end
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        String verificationLink = applicationUrl(request)+ "/verifyRegistration?token="+userService.getVerificationTokenForUser(user.getId()).getToken();
        final RegisterUserResponse registeredUser = new RegisterUserResponse(verificationLink);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User created successfully", registeredUser), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{
        UsernamePasswordAuthenticationToken unauthenticatedToken = UsernamePasswordAuthenticationToken.unauthenticated(
                jwtRequest.getUsername(), jwtRequest.getPassword());
        try {
            authenticationProvider.authenticate(
                    unauthenticatedToken
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<JwtResponse>(new JwtResponse(null,e.getMessage(),false,null,null),HttpStatus.UNAUTHORIZED);
        }catch(NullPointerException ex) {
            return new ResponseEntity<JwtResponse>(new JwtResponse(null,"User Name Not Found",false,null,null),HttpStatus.UNAUTHORIZED);
        }catch(DisabledException ex) {
            return new ResponseEntity<JwtResponse>(new JwtResponse(null,"User Account is disabled",false,null,null),HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails
                = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return new ResponseEntity<JwtResponse>(new JwtResponse(token,"Token generated Successfully",true, userDetails.getUsername(),userDetails.getAuthorities().iterator().next().toString()),HttpStatus.OK);
    }


    @GetMapping("verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")) {
            return "User verified Succesfully";
        }
        return "Bad user";

    }

    @GetMapping("resendVerifytoken")
    public String resendVerificaionToken(@RequestParam("token") String oldToken, HttpServletRequest httpServletRequest) {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user,applicationUrl(httpServletRequest), verificationToken);
        return "Verification link Sent";
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        // TODO Auto-generated method stub
        String url = applicationUrl+ "/verifyRegistration?token="+verificationToken.getToken();
        // just mimicking email sending here
        Logger log = null;
        log.info("URL link to verify: {}",url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
