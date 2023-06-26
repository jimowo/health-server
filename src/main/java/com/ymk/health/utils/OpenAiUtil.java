package com.ymk.health.utils;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenAiUtil {

    @Value("${openai.model}")
    private String openAiModel;

    @Autowired
    private OpenAiService openAiService;

    public String chat(String prompt) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .model(openAiModel)
                .echo(true)
                .temperature(0.7)
                .topP(1d)
                .frequencyPenalty(0d)
                .presencePenalty(0d)
                .maxTokens(2048)
                .build();
        CompletionResult result = openAiService.createCompletion(completionRequest);
        return result.getChoices().get(0).getText();
    }

}
