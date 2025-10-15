package edu.demo.edubackend.dto;

import jakarta.validation.constraints.NotBlank;

public record AskReq(
        @NotBlank String question,
        String rubric,
        String context,
        Integer maxTokens
) {}