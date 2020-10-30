package com.food.order.service.impl;

import com.food.order.dto.UserDto;
import com.food.order.repository.TokenRepository;
import com.food.order.repository.UserRepository;
import com.food.order.service.IUserService;
import com.food.order.storage.User;
import com.food.order.storage.VerificationToken;
import com.food.order.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    PasswordEncoder encoder;
    @Override
    public User registerUser(UserDto userDto) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()){
            return null;
        }
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .userId(IdUtils.getUserId())
                .roles("ROLE_USER")
                .enabled(false)
                .build();
        User storedUser = userRepository.save(user);
        CompletableFuture.supplyAsync(() -> generateVerificationToken(storedUser))
                .thenAccept(this::sendEmail);
        return storedUser;
    }

    @Override
    public VerificationToken getVerificationToken(String token){
        return tokenRepository.findByToken(token);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public VerificationToken generateVerificationToken(User user){
        VerificationToken token = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryTime(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .build();
        return tokenRepository.save(token);
    }

    @Override
    public void sendEmail(VerificationToken token){
        String recipient = token.getUser().getEmail();
        String subject = "Registration Conformation";
        String confirmationUrl = "/registrationConfirm?token=" + token.getToken();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText("34.227.80.189:8080" + confirmationUrl);
        mailSender.send(mailMessage);
        log.info("Email Sent to + " + recipient);
    }

}