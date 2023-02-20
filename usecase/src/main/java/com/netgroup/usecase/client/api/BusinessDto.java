package com.netgroup.usecase.client.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BusinessDto {
    @NonNull
    private String name;
    @NonNull
    private String password;
}
