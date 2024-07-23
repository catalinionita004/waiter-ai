package com.horeca.WaiterAi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horeca.WaiterAi.domain.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WaiterSevice {

    private final SpeechToTextService speechToTextService;
    private final VertexAiService vertexAiService;


    //private String text = "Hi, could I get the chicken parmesan with a side of roasted vegetables and a glass of red wine, please? And for my friend, he'll have the pasta primavera with extra parmesan cheese on top. Also, could you bring us a side of garlic bread to share?";

    public WaiterSevice(SpeechToTextService speechToTextService, VertexAiService vertexAiService) {
        this.speechToTextService = speechToTextService;
        this.vertexAiService = vertexAiService;
    }

    public Order getOrder(MultipartFile audioFile) {
        String rawOrder = speechToTextService.transcribe(audioFile);
        String prompt = "You are a helpful waiter taking a customer's order. Your task is to convert the customer's spoken order into a JSON object with the following format:\n" +
                "\n" +
                "{\n" +
                "\"itemList\": [\n" +
                "{\n" +
                "\"name\": \"Dish/Drink Name\",\n" +
                "\"quantity\": \"Number of items\"\n" +
                "},\n" +
                "// ... more items\n" +
                "]\n" +
                "}\n" +
                "\n" +
                "\n" +
                "Input: \"Hi, I'd like the steak and fries, and a coke please. My friend will have the salmon with mashed potatoes and a glass of water.\"\n" +
                "\n" +
                "JSON Output:\n" +
                "\n" +
                "{\n" +
                "\"itemList\": [\n" +
                "{\n" +
                "\"name\": \"Steak and Fries\",\n" +
                "\"quantity\": \"1\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Coke\",\n" +
                "\"quantity\": \"1\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Salmon\",\n" +
                "\"quantity\": \"1\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Mashed Potatoes\",\n" +
                "\"quantity\": \"1\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Water\",\n" +
                "\"quantity\": \"1\"\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "\n" +
                "\n" +
                "Input: %s";
        String response = vertexAiService.getResponse(String.format(prompt, rawOrder));
        response = response.replaceAll("```json", "");

        System.out.println(response);


        ObjectMapper objectMapper = new ObjectMapper();

        Order order;
        try {
            order = objectMapper.readValue(response, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return order;
    }


}
