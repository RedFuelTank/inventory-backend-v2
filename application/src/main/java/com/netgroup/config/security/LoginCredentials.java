package com.netgroup.config.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class LoginCredentials {
    private String name;
    private String password;
}
