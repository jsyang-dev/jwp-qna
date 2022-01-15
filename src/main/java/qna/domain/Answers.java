package qna.domain;

import org.hibernate.annotations.BatchSize;
import qna.CannotDeleteException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Embeddable
public class Answers {

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Answer> answers = new HashSet<>();

    public void add(Answer answer) {
        answers.add(answer);
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void deleteAnswers(User writer) {
        validateAnswers(writer);
        for (Answer answer : getAnswers()) {
            answer.delete();
        }
    }

    public List<DeleteHistory> getDeleteHistories() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        for (Answer answer : getAnswers()) {
            deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter()));
        }
        return deleteHistories;
    }

    private void validateAnswers(User writer) {
        for (Answer answer : answers) {
            validateOwner(writer, answer);
        }
    }

    private void validateOwner(User writer, Answer answer) {
        if (!answer.isOwner(writer)) {
            throw new CannotDeleteException(ErrorMessage.EXISTS_ANSWER_OF_OTHER);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answers answers1 = (Answers) o;
        return Objects.equals(answers, answers1.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answers);
    }
}
