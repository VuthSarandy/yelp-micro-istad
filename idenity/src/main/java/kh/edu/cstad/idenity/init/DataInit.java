package kh.edu.cstad.idenity.init;

import jakarta.annotation.PostConstruct;
import kh.edu.cstad.idenity.domain.Authority;
import kh.edu.cstad.idenity.domain.User;
import kh.edu.cstad.idenity.user.AuthorityRepository;
import kh.edu.cstad.idenity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    void init() {
        if (authorityRepository.count() > 0) {
            return;
        }
        System.out.println("Initializing authorities...");
        Authority writeAuthority = new Authority();
        writeAuthority.setName("write");

        Authority readAuthority = new Authority();
        readAuthority.setName("read");

        authorityRepository.saveAll(Set.of(writeAuthority, readAuthority));


        // Retrieve managed Authority entities from the database


        System.out.println("Initializing users...");

        User user1 = new User();
        user1.setUuid(UUID.randomUUID().toString());
        user1.setUsername("dy");
        user1.setEmail("sarandy@gmail.com");
        user1.setPassword(passwordEncoder.encode("123"));
        user1.setFamilyName("Vuth");
        user1.setGivenName("Sarandy");
        user1.setIsEnabled(true);
        user1.setAccountNonExpired(true);
        user1.setAccountNonLocked(true);
        user1.setCredentialsNonExpired(true);
        user1.setAuthorities(Set.of(writeAuthority, readAuthority));

        User user2 = new User();
        user2.setUuid(UUID.randomUUID().toString());
        user2.setUsername("Noody");
        user2.setEmail("noody@example.com");
        user2.setPassword(passwordEncoder.encode("123"));
        user2.setFamilyName("Vuth");
        user2.setGivenName("Sarandy");
        user2.setIsEnabled(true);
        user2.setAccountNonExpired(true);
        user2.setAccountNonLocked(true);
        user2.setCredentialsNonExpired(true);
        user2.setAuthorities(Set.of(readAuthority));

        userRepository.saveAll(Set.of(user1, user2));

    }
}
