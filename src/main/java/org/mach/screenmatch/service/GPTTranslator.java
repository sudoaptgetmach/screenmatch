package org.mach.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class GPTTranslator {

    public static String translate(String texto) {
        OpenAiService service = new OpenAiService("cole aqui sua chave da OpenAI");

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("Traduza o seguinte texto para o português: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().getFirst().getText();
    }
}
