package com.horeca.WaiterAi.utils;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MultiModal {
    public static void main(String[] args) throws IOException {
        try (VertexAI vertexAi = new VertexAI("pro-tracker-428817-p3", "us-central1"); ) {
            GenerationConfig generationConfig =
                    GenerationConfig.newBuilder()
                            .setMaxOutputTokens(2192)
                            .setTemperature(0F)
                            .setTopP(0F)
                            .build();
            List<SafetySetting> safetySettings = Arrays.asList(
                    SafetySetting.newBuilder()
                            .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
                            .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
                            .build(),
                    SafetySetting.newBuilder()
                            .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
                            .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
                            .build(),
                    SafetySetting.newBuilder()
                            .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
                            .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
                            .build(),
                    SafetySetting.newBuilder()
                            .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
                            .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
                            .build()
            );
            GenerativeModel model =
                    new GenerativeModel.Builder()
                            .setModelName("gemini-1.5-pro-001")
                            .setVertexAi(vertexAi)
                            .setGenerationConfig(generationConfig)
                            .setSafetySettings(safetySettings)
                            .build();


            String prompt = "You are a helpful waiter taking a customer's order. Your task is to convert the customer's spoken order into a JSON object.\n Input: %s ";

            var text = "Hi, could I get the chicken parmesan with a side of roasted vegetables and a glass of red wine, please? And for my friend, he'll have the pasta primavera with extra parmesan cheese on top. Also, could you bring us a side of garlic bread to share?";

            var generatedContent = model.generateContent(String.format(prompt,text));
            String generatedText = ResponseHandler.getText(generatedContent);


            // Print the response text
            System.out.println(generatedText);


        }
    }
}
