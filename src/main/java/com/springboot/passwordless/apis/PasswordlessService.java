package com.springboot.passwordless.apis;

import com.bitwarden.passwordless.PasswordlessClient;
import com.bitwarden.passwordless.error.PasswordlessApiException;
import com.bitwarden.passwordless.model.RegisterToken;
import com.bitwarden.passwordless.model.RegisteredToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PasswordlessService {

    @Autowired
    private PasswordlessClient passwordlessClient;

    public RegisteredToken registerUser(RegisterToken registerToken) throws PasswordlessApiException, IOException {
        return passwordlessClient.registerToken(registerToken);
    }
}
