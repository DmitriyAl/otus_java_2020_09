package otus.java.messages.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otus.java.messages.cache.*;
import otus.java.messages.dto.UserDto;
import otus.java.messages.handler.GetUsersRequestHandler;
import otus.java.messages.handler.GetUsersResponseHandler;
import otus.java.messages.handler.SaveUserRequestHandler;
import otus.java.messages.model.Address;
import otus.java.messages.model.Phone;
import otus.java.messages.model.User;
import otus.java.messages.service.DbServiceImpl;
import otus.java.messages.service.FrontendService;
import otus.java.messages.service.FrontendServiceImpl;
import otus.java.messages.service.UserService;
import otus.java.messages.sessionmanager.SessionManagerHibernate;
import otus.java.messages.util.HibernateUtils;
import otus.java.messages.util.MigrationsExecutorFlyway;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

@Configuration
public class BeanConfig {

    @Bean
    public MigrationsExecutorFlyway migrationsExecutorFlyway() {
        final MigrationsExecutorFlyway executorFlyway = new MigrationsExecutorFlyway();
        executorFlyway.cleanDb();
        executorFlyway.executeMigrations();
        return executorFlyway;
    }

    @Bean
    public SessionManagerHibernate sessionManager() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(User.class, Address.class, Phone.class);
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean
    public UserCacheListener userCacheListener(UserCache userCache, PhoneCache phoneCache, AddressCache addressCache) {
        final UserCacheListener userCacheListener = new UserCacheListener(phoneCache, addressCache);
        userCache.addListener(userCacheListener);
        return userCacheListener;
    }

    @Bean
    public PhoneCacheListener phoneCacheListener(UserCache userCache, PhoneCache phoneCache) {
        final PhoneCacheListener phoneCacheListener = new PhoneCacheListener(userCache);
        phoneCache.addListener(phoneCacheListener);
        return phoneCacheListener;
    }

    @Bean
    public AddressCacheListener addressCacheListener(UserCache userCache, AddressCache addressCache) {
        final AddressCacheListener addressCacheListener = new AddressCacheListener(userCache);
        addressCache.addListener(addressCacheListener);
        return addressCacheListener;
    }

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public CallbackRegistry callbackRegistry() {
        return new CallbackRegistryImpl();
    }

    @Bean
    @Qualifier("db")
    public MsClient dbClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry, DbServiceImpl<UserDto, User, Long> userService) {
        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.GET_USERS, new GetUsersRequestHandler(userService));
        requestHandlerDatabaseStore.addHandler(MessageType.SAVE_USER, new SaveUserRequestHandler(userService));
        MsClient dbClient = new MsClientImpl(UserService.SERVICE_NAME, messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        messageSystem.addClient(dbClient);
        return dbClient;
    }

    @Bean
    @Qualifier("frontend")
    public MsClient frontendClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry, DbServiceImpl<UserDto, User, Long> userService) {
        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        final GetUsersResponseHandler getUsersResponseHandler = new GetUsersResponseHandler(callbackRegistry);
        requestHandlerFrontendStore.addHandler(MessageType.GET_USERS, getUsersResponseHandler);
        requestHandlerFrontendStore.addHandler(MessageType.SAVE_USER, getUsersResponseHandler);
        MsClient frontendClient = new MsClientImpl(FrontendServiceImpl.SERVICE_NAME, messageSystem, requestHandlerFrontendStore, callbackRegistry);
        messageSystem.addClient(frontendClient);
        return frontendClient;
    }

    @Bean
    public FrontendService frontendService(@Qualifier("frontend") MsClient frontendClient) {
        return new FrontendServiceImpl(frontendClient);
    }
}