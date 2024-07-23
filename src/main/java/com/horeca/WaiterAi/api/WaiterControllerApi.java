package com.horeca.WaiterAi.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name = "Product Controller", description = "the Product Controller Api")
public interface WaiterControllerApi {

    @Operation(
            summary = "Take order",
            description = "take order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    com.horeca.WaiterAi.dtos.ApiResponse takeOrder(MultipartFile audioFile);


}
