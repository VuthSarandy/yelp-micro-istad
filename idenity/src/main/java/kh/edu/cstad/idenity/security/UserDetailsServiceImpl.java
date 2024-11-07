package kh.edu.cstad.idenity.security;

import kh.edu.cstad.idenity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kh.edu.cstad.idenity.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "User has not been found"
                )
        );
        log.info("User :{}", user);
        CustomUserDetails customUserDetail = new CustomUserDetails();
        customUserDetail.setUser(user);
        return customUserDetail;
    }
}
