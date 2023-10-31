package com.springboot.passwordless.apis;

import com.bitwarden.passwordless.error.PasswordlessApiException;
import com.bitwarden.passwordless.model.RegisteredToken;
import com.bitwarden.passwordless.model.VerifiedUser;
import com.springboot.passwordless.util.TestDataUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordlessRestControllerTest {

    @InjectMocks
    private PasswordlessRestController passwordlessRestController;

    @Mock
    private PasswordlessService passwordlessService;

    @Test
    public void testRegister() throws PasswordlessApiException, IOException {
        RegisteredToken sampleToken = TestDataUtil.successfullyRegisteredToken();
        when(passwordlessService.registerUser(any())).thenReturn(sampleToken);

        ResponseEntity<RegisteredToken> registeredTokenResponse  = passwordlessRestController.register(TestDataUtil.inputForRegistration());

        RegisteredToken registeredToken = registeredTokenResponse.getBody();
        Assert.assertEquals(registeredToken.getToken(), sampleToken.getToken());
    }


    @Test
    public void testSuccessfulLogin() throws PasswordlessApiException, IOException {

        RegisteredToken sampleToken = TestDataUtil.goodVerifyToken();
        VerifiedUser user = TestDataUtil.goodVerifiedUser();
        ResponseEntity<VerifiedUser> verifiedUserResponse  = passwordlessRestController.login(sampleToken.getToken(), user);

        Assert.assertTrue(verifiedUserResponse.hasBody());
        Assert.assertTrue(verifiedUserResponse.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(verifiedUserResponse.getBody().getUserId(), user.getUserId());
    }

    @Test
    public void testBadLogin() throws PasswordlessApiException, IOException {

        ResponseEntity<VerifiedUser> verifiedUserResponse  = passwordlessRestController.login(null, null);

        Assert.assertFalse(verifiedUserResponse.hasBody());
        Assert.assertTrue(verifiedUserResponse.getStatusCode().isSameCodeAs(HttpStatus.FORBIDDEN));
    }
}
