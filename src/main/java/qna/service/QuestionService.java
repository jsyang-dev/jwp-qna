package qna.service;

import org.springframework.stereotype.Service;
import qna.domain.Question;
import qna.domain.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question findQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
