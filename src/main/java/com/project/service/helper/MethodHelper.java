package com.project.service.helper;

import com.project.contactmessage.messages.Messages;
import com.project.entity.concretes.user.User;

import com.project.exception.BadRequestexception;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {
    private final UserRepository userRepository;

    // !!! isUserExist
    public User isUserExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,
                        userId)));
    }

    // !!! checkBuiltIn
    public void checkBuiltIn(User user){
        if(Boolean.TRUE.equals(user.getBuilt_in())) {
            throw new BadRequestexception(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }
    //is user exist with username
    public User isUserExistByUsername(String username)
    {
        User user = userRepository.findByUsernameEquals(username);
        if (user.getId() == null)
        {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }
        return user;
    }
    //Advisor or not
    public void checkadvisor(User user)
    {
        if (Boolean.FALSE.equals(user.getIsAdvisor()))

            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_ADVISOR_MESSAGE,user.getId()));
    }
}
