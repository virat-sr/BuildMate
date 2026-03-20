package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.service.AiGenerationService;
import reactor.core.publisher.Flux;

public class AiGenerationServiceImpl implements AiGenerationService {

    @Override
    public Flux<String> streamResponse(String message, Long projectId) {

        return null;
    }

}
