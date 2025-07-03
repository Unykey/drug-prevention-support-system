package com.swp08.dpss.dto.responses;

import lombok.*;

@Getter
@AllArgsConstructor
public class ApiResponse<T>{
    private final boolean success;
    private final T data;
    private final String message;
}
