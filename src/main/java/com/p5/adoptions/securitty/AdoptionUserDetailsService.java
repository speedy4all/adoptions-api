package com.p5.adoptions.securitty;

import com.p5.adoptions.repository.users.Role;
import com.p5.adoptions.repository.users.RoleRepository;
import com.p5.adoptions.repository.users.RolesEnum;
import com.p5.adoptions.repository.users.User;
import com.p5.adoptions.repository.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AdoptionUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AdoptionPasswordEncoder passwordEncoder;

    public AdoptionUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, AdoptionPasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (!userOptional.isPresent())
        {
            throw new UsernameNotFoundException(username);
        }

        return new AdoptionUserPrincipal(userOptional.get());
    }

    @Bean
    private CommandLineRunner setUpDefaultUser()
    {
        return args -> {
            final String defaultEmail = "animalshelter@pentastagiu.io";
            final String defaultPassword = "password";

            Role moderatorRole = roleRepository.findByRole(RolesEnum.ROLE_MOD).orElseGet(() -> {
                Role role = new Role().setRole(RolesEnum.ROLE_MOD);
                return roleRepository.save(role);
            });

            userRepository.findByEmail(defaultEmail).orElseGet(() -> {
                User user = new User().setEmail(defaultEmail)
                                      .setPassword(passwordEncoder.encode(defaultPassword))
                                      .setRoles(Collections.singleton(moderatorRole));
                return userRepository.save(user);
            });
        };
    }
}
