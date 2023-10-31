package com.springboot.passwordless.apis;

import com.bitwarden.passwordless.error.PasswordlessApiException;
import com.bitwarden.passwordless.model.RegisterToken;
import com.bitwarden.passwordless.model.RegisteredToken;
import com.bitwarden.passwordless.model.VerifiedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class PasswordlessRestController {

    @Autowired
    private PasswordlessService passwordlessService;

    @PostMapping("/register")
    public ResponseEntity<RegisteredToken> register(@RequestBody RegisterToken registerToken) throws PasswordlessApiException, IOException {
        return new ResponseEntity<>(passwordlessService.registerUser(registerToken), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<VerifiedUser> login(@RequestParam("token") String token, @AuthenticationPrincipal VerifiedUser user) {
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
