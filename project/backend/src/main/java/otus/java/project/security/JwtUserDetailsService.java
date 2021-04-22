package otus.java.project.security;

import otus.java.project.dao.UserDao;
import otus.java.project.entity.User;
import otus.java.project.exception.NoSuchUserException;
import otus.java.project.security.model.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByLogin(s).orElseThrow(() -> new NoSuchUserException(s));
        return new UserPrincipal(user);
    }
}