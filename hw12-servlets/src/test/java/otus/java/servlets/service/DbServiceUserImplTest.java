package otus.java.servlets.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.java.servlets.dao.Dao;
import otus.java.servlets.exception.DbServiceException;
import otus.java.servlets.model.User;
import otus.java.servlets.sessionmanager.SessionManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceUserImplTest {
    private static final long CLIENT_ID = 1L;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private Dao<User, Long> userDao;
    private InOrder inOrder;
    private DbService<User, Long> userService;

    @BeforeEach
    public void setUp() {
        given(userDao.getSessionManager()).willReturn(sessionManager);
        inOrder = inOrder(userDao, sessionManager);
        userService = new DbServiceImpl<>(userDao);
    }

    @Test
    void shouldCorrectSaveUser() {
        var mark = new User("Mark");
        given(userDao.insertOrUpdate(mark)).willReturn(CLIENT_ID);
        long id = userService.save(mark);
        assertThat(id).isEqualTo(CLIENT_ID);
    }

    @Test
    void shouldCorrectSaveUserAndOpenAndCommitTranInExpectedOrder() {
        userService.save(new User());
        inOrder.verify(userDao, times(1)).getSessionManager();
        inOrder.verify(sessionManager, times(1)).beginSession();
        inOrder.verify(sessionManager, times(1)).commitSession();
        inOrder.verify(sessionManager, never()).rollbackSession();
    }

    @Test
    void shouldOpenAndRollbackTranWhenExceptionInExpectedOrder() {
        doThrow(IllegalArgumentException.class).when(userDao).insertOrUpdate(any());

        assertThatThrownBy(() -> userService.save(null))
                .isInstanceOf(DbServiceException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);

        inOrder.verify(userDao, times(1)).getSessionManager();
        inOrder.verify(sessionManager, times(1)).beginSession();
        inOrder.verify(sessionManager, times(1)).rollbackSession();
        inOrder.verify(sessionManager, never()).commitSession();
    }

    @Test
    void shouldLoadCorrectUserById() {
        User expectedClient = new User(CLIENT_ID, "Mark");
        given(userDao.findById(CLIENT_ID)).willReturn(Optional.of(expectedClient));
        Optional<User> mayBeClient = userService.getById(CLIENT_ID);
        assertThat(mayBeClient).isPresent().get().isEqualTo(expectedClient);
    }
}