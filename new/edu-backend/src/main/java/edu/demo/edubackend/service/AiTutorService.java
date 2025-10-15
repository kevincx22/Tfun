package edu.demo.edubackend.service;

import edu.demo.edubackend.ai.AiClient;
import edu.demo.edubackend.entity.ChatTurn;
import edu.demo.edubackend.repo.ChatTurnRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiTutorService {
    private final ChatTurnRepo chatRepo;
    private final AiClient ai;

    @Value("${ai.model}") String model;
    @Value("${ai.history-limit:4}") int limit;

    public String ask(Long userId, String question, String rubric, String context, Integer maxTokens) {
        var history = chatRepo.findTop10ByUserIdOrderByCreatedAtDesc(userId).stream().limit(limit).toList();
        StringBuilder sb = new StringBuilder("You are an AI tutor. Provide concise, actionable guidance.");
        if (rubric != null && !rubric.isBlank()) sb.append("\nRubric:\n").append(rubric);
        if (context != null && !context.isBlank()) sb.append("\nAssignment Context:\n").append(context);
        if (!history.isEmpty()) {
            sb.append("\nRecent Conversation (latest first):\n");
            history.forEach(h -> sb.append("- Q: ").append(h.getQuestion()).append("\n  A: ").append(h.getAnswer()).append("\n"));
        }
        sb.append("\nStudent Question:\n").append(question)
                .append("\nOutput short bullets. Do not assign a final grade.");
        String answer = ai.chat(model, sb.toString(), maxTokens);

        var turn = new ChatTurn();
        turn.setUserId(userId); turn.setQuestion(question); turn.setAnswer(answer);
        chatRepo.save(turn);

        return answer;
    }
}