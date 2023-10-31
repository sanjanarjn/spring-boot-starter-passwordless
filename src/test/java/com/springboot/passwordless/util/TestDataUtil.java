package com.springboot.passwordless.util;

import com.bitwarden.passwordless.model.RegisterToken;
import com.bitwarden.passwordless.model.RegisteredToken;
import com.bitwarden.passwordless.model.VerifiedUser;

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
}
