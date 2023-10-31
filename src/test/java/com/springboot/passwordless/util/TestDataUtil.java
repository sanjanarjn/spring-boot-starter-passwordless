package com.springboot.passwordless.util;

import com.bitwarden.passwordless.error.PasswordlessApiException;
import com.bitwarden.passwordless.error.PasswordlessProblemDetails;
import com.bitwarden.passwordless.model.RegisterToken;
import com.bitwarden.passwordless.model.RegisteredToken;
import com.bitwarden.passwordless.model.VerifiedUser;
import com.bitwarden.passwordless.model.VerifySignIn;

import java.util.Locale;

public class TestDataUtil {


    public static RegisterToken inputForRegistration() {
        return new RegisterToken("sample_user_id", "Sample User");
    }

    public static RegisteredToken successfullyRegisteredToken() {
        return new RegisteredToken("register_mock_token");
    }

    public static RegisteredToken goodVerifyToken() {
        return new RegisteredToken("verify_mock_token");
    }

    public static VerifiedUser goodVerifiedUser() {
        RegisterToken registerToken = inputForRegistration();
        return VerifiedUser.builder().userId(registerToken.getUserId()).build();
    }

    public static VerifySignIn inputForGoodVerifySignin() {

        return new VerifySignIn("verify_good_mock_token");
    }

    public static VerifySignIn inputForBadVerifySignin() {

        return new VerifySignIn("verify_bad_mock_token");
    }

    public static PasswordlessApiException badSigninException() {
        PasswordlessProblemDetails problemDetails = PasswordlessProblemDetails.builder().errorCode(ServiceConstants.PASSWORDLESS_ERROR_CODE.UNKNOWN_CREDENTIAL.name().toLowerCase(Locale.ROOT)).build();
        return new PasswordlessApiException(problemDetails);
    }
}
