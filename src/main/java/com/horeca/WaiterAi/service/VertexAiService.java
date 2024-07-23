package com.horeca.WaiterAi.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VertexAiService {

    private VertexAI vertexAI;
    GenerationConfig generationConfig;
    List<SafetySetting> safetySettings;
    GenerativeModel model;

    public VertexAiService() {
        vertexAI = new VertexAI("pro-tracker-428817-p3", "us-central1");
        generationConfig =
                GenerationConfig.newBuilder()
                        .setMaxOutputTokens(8192)
                        .setTemperature(0F)
                        .setTopP(0F)
                        .build();
        safetySettings = Arrays.asList(
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                        .build()
        );
         model =
                new GenerativeModel.Builder()
                        .setModelName("gemini-1.5-pro-001")
                        .setVertexAi(vertexAI)
                        .setGenerationConfig(generationConfig)
                        .setSafetySettings(safetySettings)
                        .build();
    }

    @SneakyThrows
    public String getResponse(String textContent){
        return ResponseHandler.getText(model.generateContent(textContent));
    }




}
