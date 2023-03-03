package com.netgroup.entity.client;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Business {
    @NonNull
    private String name;
    @NonNull
    private String password;
    @NonNull
    private Representative representative;

    @NonNull
    private final List<String> authority = List.of("BUSINESS");
}
