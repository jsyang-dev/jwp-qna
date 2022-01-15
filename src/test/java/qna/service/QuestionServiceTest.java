package qna.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.Answer;
import qna.domain.Comment;
import qna.domain.Question;
import qna.domain.QuestionRepository;
import qna.domain.User;
import qna.domain.UserRepository;

import javax.persistence.EntityManager;

@SpringBootTest
@EnableJpaAuditing
@Transactional
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private Question question;

    @BeforeEach
    void setUp() {
        question = saveQuestion(saveUser(1), 1);
        entityManager.clear();
    }

    @Test
    @DisplayName("OneToMany 여러 필드 조회 테스트")
    void findQuestion() {
        // when
        Question savedQuestion = questionService.findQuestion(question.getId());

        // then
        System.out.println("savedQuestion = " + savedQuestion);
    }

    private User saveUser(int seq) {
        User user = new User("user" + seq, "password", "name", "email");
        userRepository.save(user);
        return user;
    }

    private Question saveQuestion(User user, int seq) {
        Question question = new Question("title" + seq, "contents" + seq);
        question.addAnswer(new Answer(user, "contents"));
        question.addComment(new Comment("comment"));
        return questionRepository.save(question);
    }
}
