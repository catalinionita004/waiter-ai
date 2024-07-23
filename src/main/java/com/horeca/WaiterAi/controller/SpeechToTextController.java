package com.horeca.WaiterAi.controller;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.LongRunningRecognizeMetadata;
import com.google.cloud.speech.v1.LongRunningRecognizeRequest;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;
import com.horeca.WaiterAi.service.SpeechToTextService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class SpeechToTextController {

    private final SpeechToTextService speechToTextService;

    public SpeechToTextController(SpeechToTextService speechToTextService) {
        this.speechToTextService = speechToTextService;
    }


    @PostMapping("/transcribe")
    public String transcribe(@RequestParam("audioFile") MultipartFile audioFile) {
        return speechToTextService.transcribe(audioFile);
    }






}