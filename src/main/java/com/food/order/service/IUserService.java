package com.food.order.service;

import com.food.order.dto.UserDto;
import com.food.order.storage.User;
import com.food.order.storage.VerificationToken;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User registerUser(UserDto userDto) throws Exception;
    VerificationToken getVerificationToken(String token);
    void saveRegisteredUser(User user);
    VerificationToken generateVerificationToken(User user);
    void sendEmail(VerificationToken token);
}
