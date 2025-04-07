package fr.codecake.com.data;

import fr.codecake.com.model.Role;
import fr.codecake.com.model.User;
import fr.codecake.com.repository.RoleRepository;
import fr.codecake.com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultUserIfNotExists();

        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists(){
        Role userRole = roleRepository.findByName("ROLE_USER").get();

        for(int i = 1; i <= 5; i++){
            String defaultEmail = "user" + i + "@gmail.com";

            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(userRole));
            userRepository.save(user);

        }
    }

    private void createDefaultAdminIfNotExists(){
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for(int i = 1; i <= 2; i++){
            String defaultEmail = "admin" + i + "@gmail.com";

            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The Admin");
            user.setLastName("Admin");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);

        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
