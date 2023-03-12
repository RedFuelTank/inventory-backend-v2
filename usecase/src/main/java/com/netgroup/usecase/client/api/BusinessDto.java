package com.netgroup.usecase.client.api;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessDto {
    @NonNull
    private String name;
    @NonNull
    private String password;
}
