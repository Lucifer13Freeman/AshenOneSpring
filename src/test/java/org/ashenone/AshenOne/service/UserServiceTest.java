package org.ashenone.AshenOne.service;

import org.ashenone.AshenOne.domain.Role;
import org.ashenone.AshenOne.domain.User;
import org.ashenone.AshenOne.repos.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;

@SpringBootTest
public class UserServiceTest
{
    @Autowired
    private UserService userSevice;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private MailService mailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUser()
    {
        User user = new User();

        user.setEmail("hell@mail.com");

        boolean isUserCreated = userSevice.addUser(user);

        Assertions.assertTrue(isUserCreated);
        Assertions.assertNotNull(user.getActivationCode());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailService, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void addUserFailTest()
    {
        User user = new User();

        user.setUsername("Lucifer");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("Lucifer");

        boolean isUserCreated = userSevice.addUser(user);

        Assertions.assertFalse(isUserCreated);

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailService, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void activateUser()
    {
        User user = new User();

        user.setActivationCode("activation_code");

        Mockito.doReturn(user)
                .when(userRepo)
                .findByActivationCode("activate");

        boolean isUserActivated = userSevice.activateUser("activate");

        Assertions.assertTrue(isUserActivated);
        Assertions.assertNull(user.getActivationCode());

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    public void activateUserFailTest()
    {
        boolean isUserActivated = userSevice.activateUser("activate me");
        Assertions.assertFalse(isUserActivated);
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}
