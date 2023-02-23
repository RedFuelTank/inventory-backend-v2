package com.netgroup.config.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class LoginCredentials {
    private String username;
    private String password;
}
