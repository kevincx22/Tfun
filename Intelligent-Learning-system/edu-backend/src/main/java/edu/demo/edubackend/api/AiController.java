package edu.demo.edubackend.api;

import edu.demo.edubackend.dto.*;
import edu.demo.edubackend.service.AiTutorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {
    private final AiTutorService service;

    @PostMapping("/ask")
    public edu.demo.edubackend.dto.AskResp ask(HttpServletRequest req, @RequestBody @Valid edu.demo.edubackend.dto.AskReq body) {
        Long userId = (Long) req.getAttribute("userId");
        String ans = service.ask(userId, body.question(), body.rubric(), body.context(), body.maxTokens());
        return new edu.demo.edubackend.dto.AskResp(ans);
    }
}