package com.ecommerce.component;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecommerce.userModule.entity.Role;
import com.ecommerce.userModule.entity.User;
import com.ecommerce.userModule.repository.RoleRepository;
import com.ecommerce.userModule.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

@Component
public class InitializeDatabase implements CommandLineRunner {


    final
    UserRepository userRepository;

    final
    RoleRepository roleRepository;

    final
    PasswordEncoder passwordEncoder;





    public InitializeDatabase(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void run(String... args) {
        loadRoleData();
        loadUserData();


    }
    private void loadRoleData() {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Role>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/db/roles.json");
        try {
            List<Role> roleList = mapper.readValue(inputStream, typeReference);

            for (Role role : roleList) {



                try{
                    roleRepository.save(role);
                    System.out.println("role Saved!");
                }
                catch (Exception e){
                    System.out.println("duplicated role: " + e.getMessage());
                }


            }


        } catch (IOException e) {
            System.out.println("duplicated role: " + e.getMessage());
        }

    }

    private void loadUserData() {

        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN").get();
            Role userRole = roleRepository.findByName("USER").get();

            User u1 = new User();
            u1.setFirstName("Admin");
            u1.setLastName("Adminson");
            u1.setEmail("admin@com");
            u1.setPassword(passwordEncoder.encode("admin"));
            u1.setColor("purple");
            u1.setRoles(new HashSet<>());
            u1.getRoles().add(adminRole);

            User u3 = new User();
            u3.setFirstName("user");
            u3.setLastName("user");
            u3.setEmail("user@com");
            u3.setPassword(passwordEncoder.encode("user"));
            u3.setColor("cyan");
            u3.setRoles(new HashSet<>());
            u3.getRoles().add(userRole);

            userRepository.save(u1);
            userRepository.save(u3);

        }
    }




}
