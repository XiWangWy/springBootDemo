package com.bless.Service;

import com.bless.Entity.MyUserDetails;
import com.bless.Entity.User;
import com.bless.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by wangxi on 18/8/8.
 */
@Service
@Slf4j
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException(username + " not found");
        }

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        ArrayList<String> userRoles = user.getRoles();

        for (String role:userRoles) {
            authorities.add(new SimpleGrantedAuthority(role));
            log.info("username is " + username + ", " + role);
        }

       return  new MyUserDetails(username, user.getPassWord(), authorities);
    }
}
