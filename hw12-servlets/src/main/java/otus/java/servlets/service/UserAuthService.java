package otus.java.servlets.service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}