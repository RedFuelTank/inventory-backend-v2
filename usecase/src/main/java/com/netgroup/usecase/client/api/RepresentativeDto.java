package com.netgroup.usecase.client.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RepresentativeDto {
    @NonNull
    private String name;

    @NonNull
    private String password;
}
