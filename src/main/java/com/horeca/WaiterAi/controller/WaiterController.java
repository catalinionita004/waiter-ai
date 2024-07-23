package com.horeca.WaiterAi.controller;


import com.horeca.WaiterAi.api.WaiterControllerApi;
import com.horeca.WaiterAi.dtos.ApiResponse;
import com.horeca.WaiterAi.service.SpeechToTextService;
import com.horeca.WaiterAi.service.WaiterSevice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
public class WaiterController implements WaiterControllerApi {

    private final WaiterSevice waiterSevice;

    public WaiterController(WaiterSevice waiterSevice) {
        this.waiterSevice = waiterSevice;
    }

    @PostMapping("/takeOrder")
    public ApiResponse takeOrder(@RequestParam("audioFile") MultipartFile audioFile) {
        return new ApiResponse(true, LocalDateTime.now().toString(),
                "Take order successfully", waiterSevice.getOrder(audioFile));
    }





}
