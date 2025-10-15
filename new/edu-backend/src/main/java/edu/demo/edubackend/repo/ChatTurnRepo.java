package edu.demo.edubackend.repo;

import edu.demo.edubackend.entity.ChatTurn;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatTurnRepo extends JpaRepository<ChatTurn, Long> {
    List<ChatTurn> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
}