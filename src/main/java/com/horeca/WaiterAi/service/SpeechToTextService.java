package com.horeca.WaiterAi.service;


import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
public class SpeechToTextService {


    public String transcribe(MultipartFile audioFile) {
        // 1. Configure Authentication (if not already set up)
        try  {
            // 2. Read Audio Data
            byte[] audioData = audioFile.getBytes();

            // 3. Instantiate Speech-to-Text Client
            SpeechClient speechClient = SpeechClient.create();
            // 4. Create RecognitionAudio object
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(ByteString.copyFrom(audioData))
                    .build();

            // 5. Configure Recognition Request
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16) // Adjust based on your audio
                    .setSampleRateHertz(24000) // Adjust based on your audio
                    .setLanguageCode("en-US")  // Set language
                    .build();

            LongRunningRecognizeRequest request = LongRunningRecognizeRequest.newBuilder()
                    .setConfig(config)
                    .setAudio(audio)
                    .build();

            // 6. Send Request and Get Response (Asynchronous for long audio)
            OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response =
                    speechClient.longRunningRecognizeAsync(request);

            // 7. Wait for Operation to Complete
            System.out.println("Waiting for operation to complete...");
            LongRunningRecognizeResponse operationResponse = response.get();

            // 8. Process Results
            List<SpeechRecognitionResult> results = operationResponse.getResultsList();
            StringBuilder transcript = new StringBuilder();

            for (SpeechRecognitionResult result : results) {
                List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
                if (!alternatives.isEmpty()) {
                    transcript.append(alternatives.get(0).getTranscript()).append(" ");
                }
            }
            return transcript.toString();
        }
        catch (Exception exception){
            throw  new RuntimeException(exception);
        }
    }

}
