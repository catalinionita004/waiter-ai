package com.horeca.WaiterAi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String timestamp;
    private String message;
    private Object body;
}
