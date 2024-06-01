package com.events.api.events;

import com.events.api.events.enumerator.ERole;
import com.events.api.events.model.Role;
import com.events.api.events.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.save(Role.builder().name(ERole.ROLE_USER).build());
            roleRepository.save(Role.builder().name(ERole.ROLE_MODERATOR).build());
            roleRepository.save(Role.builder().name(ERole.ROLE_ADMIN).build());
        }
    }
}