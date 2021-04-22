package otus.java.project.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NoSuchUserException extends UsernameNotFoundException {
    public NoSuchUserException(String login) {
        super(String.format("No user with username: %s", login));
    }
}