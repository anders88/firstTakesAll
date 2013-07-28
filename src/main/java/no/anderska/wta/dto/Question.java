package no.anderska.wta.dto;

public class Question {
    
    private int id;
    private String text;
    private long point;
    private boolean answered;

    public static class QuestionFactory {
        private Question question = new Question();
        
        public QuestionFactory withId(int id) {
            question.id = id;
            return this;
        }
        
        public QuestionFactory withText(String text) {
            question.text = text;
            return this;
        }

        public QuestionFactory withPoint(long point) {
            question.point = point;
            return this;
        }

        public QuestionFactory withAnswered(boolean answered) {
            question.answered = answered;
            return this;
        }
        
        public Question create() {
            return question;
        }
    }
    
    public static QuestionFactory factory() {
        QuestionFactory questionFactory = new QuestionFactory();
        return questionFactory;
    }
    
    private Question() {
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public long getPoint() {
        return point;
    }

    public boolean isAnswered() {
        return answered;
    }
    
    
}
