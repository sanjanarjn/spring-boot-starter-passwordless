package com.springboot.passwordless.authentication;

import com.bitwarden.passwordless.PasswordlessClient;
import com.bitwarden.passwordless.error.PasswordlessApiException;
import com.bitwarden.passwordless.model.VerifiedUser;
import com.bitwarden.passwordless.model.VerifySignIn;
import com.springboot.passwordless.util.ServiceConstants;
import com.springboot.passwordless.util.TestDataUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordlessAuthenticationFilterTest {

    @InjectMocks
    PasswordlessAuthenticationFilter authenticationFilter;

    @Mock
    private PasswordlessClient passwordlessClient;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    public void testSuccessfulFilter() throws ServletException, IOException, PasswordlessApiException {

        VerifiedUser user = TestDataUtil.goodVerifiedUser();
        when(passwordlessClient.signIn(TestDataUtil.inputForGoodVerifySignin())).thenReturn(user);
        when(request.getParameter(ServiceConstants.TOKEN_PARAM_KEY)).thenReturn(TestDataUtil.inputForGoodVerifySignin().getToken());

        authenticationFilter.doFilterInternal(request, response, filterChain);

        VerifiedUser verifiedUser = (VerifiedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Assert.assertTrue(verifiedUser != null);
        Assert.assertEquals(verifiedUser.getUserId(), user.getUserId());
    }

    @Test
    public void testUnsuccessfulFilter() throws ServletException, IOException {

        VerifiedUser user = TestDataUtil.goodVerifiedUser();
        VerifySignIn inputForBadVerifySignin = TestDataUtil.inputForBadVerifySignin();
        try {
            when(passwordlessClient.signIn(inputForBadVerifySignin)).thenThrow(TestDataUtil.badSigninException());
            when(request.getParameter(ServiceConstants.TOKEN_PARAM_KEY)).thenReturn(inputForBadVerifySignin.getToken());

            authenticationFilter.doFilterInternal(request, response, filterChain);
        } catch (RuntimeException | PasswordlessApiException e) {
            Throwable cause = e.getCause();
            Assert.assertTrue(cause instanceof PasswordlessApiException);
            PasswordlessApiException passwordlessApiException = (PasswordlessApiException) cause;
            Assert.assertTrue(ServiceConstants.PASSWORDLESS_ERROR_CODE.UNKNOWN_CREDENTIAL.name().equalsIgnoreCase(passwordlessApiException.getProblemDetails().getErrorCode()));
        }
    }

}
