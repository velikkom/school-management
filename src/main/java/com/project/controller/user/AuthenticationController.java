package com.project.controller.user;

import com.project.entity.concretes.user.User;
import com.project.payload.authentication.LoginRequest;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.UpdatePasswordRequest;
import com.project.payload.response.authentication.AuthResponse;
import com.project.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * http://localhost:8080/auth/login
     * postmapping
     */

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>  authenticateUser(@RequestBody @Valid LoginRequest loginRequest)
    {
       return authenticationService.authenticateUser(loginRequest);

    }

    /**
     * Not: ODEV : updatePassword() --> Controller ve Service
     * old password new password
     * old password plain text but in db it is hashed int that reason
     *
     * login service
     *
     */

    @PatchMapping("/updatePassword")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<String> updatePassword (@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest, HttpServletRequest request)
    {
        authenticationService.updatePassword(updatePasswordRequest,request);
        String response = SuccessMessages.PASSWORD_CHANGED_RESPONSE_MESSAGE;
        return  ResponseEntity.ok(response);
    }





/*    private final AuthenticationService authenticationService;

    @PostMapping("/login") // http://localhost:8080/auth/login + POST + JSON
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        return authenticationService.authenticateUser(loginRequest);
    }

    //Not: ODEV : updatePassword() --> Controller ve Service
    @PatchMapping("/updatePassword") // http://localhost:8080/auth/updatePassword
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
                                                 HttpServletRequest request ){
        authenticationService.updatePassword(updatePasswordRequest , request);
        String response = SuccessMessages.PASSWORD_CHANGED_RESPONSE_MESSAGE; // paylod.messages
        return ResponseEntity.ok(response);
    }*/

}










