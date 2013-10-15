package no.anderska.wta.game;

import java.util.ArrayList;
import java.util.List;

import no.anderska.wta.AnswerStatus;

import org.joda.time.DateTime;

public class QuestionSet {
    private final List<String> questionTexts = new ArrayList<>();
    private final List<String> answers = new ArrayList<>();
    private final QuestionGenerator generator;
    private final String categoryName;
    private final DateTime limit;

    public QuestionSet(List<?> questions, List<?> answers, QuestionGenerator generator, String categoryName) {
        for (Object question : questions) {
            this.questionTexts.add(question.toString());
        }
        for (Object answer : answers) {
            this.answers.add(answer.toString());
        }
        this.generator = generator;
        this.categoryName = categoryName;
        this.limit = new DateTime().plusSeconds(8);
    }

    public QuestionSet(List<Question> questions, QuestionGenerator generator, String categoryName) {
        for (Question question : questions) {
            questionTexts.add(question.getQuestion());
            answers.add(question.getCorrectAnswer());
        }
        this.generator = generator;
        this.categoryName = categoryName;
        this.limit = new DateTime().plusSeconds(8);
    }

    AnswerStatus validateAnswer(List<String> answers) {
        if (!this.answers.equals(answers)) {
            return AnswerStatus.WRONG;
        }
        if (new DateTime().isAfter(limit)) {
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
        return answers;
    }

    public List<String> questions() {
        return questionTexts;
    }
}
