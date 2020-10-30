package com.food.order.service.impl;

import com.food.order.repository.UserRepository;
import com.food.order.storage.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with this username: " + email));
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonBlocked = true;
        return new org.springframework.security.core.userdetails.User(user.getEmail()
                    ,user.getPassword()
                    , user.isEnabled(), true, true, true
                    , getAuthorities(user.getRoles())) ;



    }
    private List<GrantedAuthority> getAuthorities(String role){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
