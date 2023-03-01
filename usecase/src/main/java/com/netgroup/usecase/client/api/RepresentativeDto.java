package com.netgroup.usecase.client.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepresentativeDto {
    @NonNull
    private String username;

    @NonNull
    private String password;
}
