package no.anderska.wta.game;

import java.util.ArrayList;
import java.util.List;

import no.anderska.wta.AnswerStatus;

import org.joda.time.DateTime;

public class QuestionSet {
    private final List<Question> questions;
    private final QuestionGenerator generator;
    private final String categoryName;
    private final DateTime limit;

    public QuestionSet(List<Question> questions, QuestionGenerator generator, String categoryName) {
        this.questions = questions;
        this.generator = generator;
        this.categoryName = categoryName;
        this.limit = new DateTime().plusSeconds(8);
    }

    public AnswerStatus validateAnswer(List<String> answers) {
        DateTime answered = new DateTime();

        if (answers == null) {
            return AnswerStatus.ERROR;
        }
        if (questions.size() != answers.size()) {
            return AnswerStatus.WRONG;
        }
        for (int i=0;i<answers.size();i++) {
            if (!questions.get(i).isCorrect(answers.get(i))) {
                return AnswerStatus.WRONG;
            }
        }
        if (answered.isAfter(limit)) {
            return AnswerStatus.LATE;
        }
        return AnswerStatus.OK;
    }

    public QuestionGenerator getGenerator() {
        return generator;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<String> expectedAnswers() {
        List<String> result = new ArrayList<>();
        for (Question question : questions) {
            result.add(question.getFact());
        }
        return result;
    }
}
