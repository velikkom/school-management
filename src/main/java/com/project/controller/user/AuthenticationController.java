package com.project.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

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










