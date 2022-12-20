package com.maltseva.servicestation.project.service.userdetails;

import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Value("${spring.security.user.name}")
    private String adminUserName;
    @Value("${spring.security.user.password}")
    private String adminPassword;
    @Value("${spring.security.user.roles}")
    private String adminRole;

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(adminUserName)) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(adminUserName)
                    .password(adminPassword)
                    .roles(adminRole)
                    .build();
        } else {
            User user = userRepository.findUserByLogin(username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(getRoleName(Math.toIntExact(user.getRole().getId()))));
            return new CustomUserDetails(user.getId().intValue(), username, user.getPassword(), authorities);
        }
    }

    private String getRoleName(Integer id) {
        String roleNane = switch (id) {
            case 1 -> "ROLE_USER";
            case 2 -> "ROLE_LOGIST";
            case 3 -> "ROLE_EMPLOYEE";
            case 4 -> "ROLE_DIRECTOR";
            default -> "";
        };
        return roleNane;

    }
}

