package com.netgroup.entity.client;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Business {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private Representative representative;
}
