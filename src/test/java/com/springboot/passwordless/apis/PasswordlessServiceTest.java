package com.springboot.passwordless.apis;

import com.bitwarden.passwordless.PasswordlessClient;
import com.bitwarden.passwordless.error.PasswordlessApiException;
import com.bitwarden.passwordless.model.RegisteredToken;
import com.springboot.passwordless.util.TestDataUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordlessServiceTest {

    @InjectMocks
    private PasswordlessService passwordlessService;

    @Mock
    private PasswordlessClient passwordlessClient;

    @Test
    public void testRegisterUser() throws PasswordlessApiException, IOException {
        RegisteredToken sampleToken = TestDataUtil.successfullyRegisteredToken();
        when(passwordlessClient.registerToken(any())).thenReturn(sampleToken);

        RegisteredToken registeredToken = passwordlessService.registerUser(TestDataUtil.inputForRegistration());
        Assert.assertEquals(registeredToken.getToken(), sampleToken.getToken());
    }

}
