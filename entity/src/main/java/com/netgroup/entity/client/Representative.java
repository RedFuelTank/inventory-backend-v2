package com.netgroup.entity.client;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Representative {
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private final List<String> authority = List.of("REPRESENTATIVE");
}
