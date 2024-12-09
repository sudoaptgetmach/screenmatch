package org.mach.screenmatch.service;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;

import java.util.List;

public class GPTTranslator {

    private static final String API_KEY = System.getenv("GPT_API_KEY");

    public static String translate(String texto) {
        OpenAiService service = new OpenAiService(API_KEY);

        CompletionRequest request = CompletionRequest.builder()
                .prompt("Traduza o seguinte texto para o português: " + texto)
                .model("gpt-3.5-turbo")
                .maxTokens(1000)
                .build();

        CompletionResult result = service.createCompletion(request);
        return result.getChoices().get(0).getText().trim();
    }
}