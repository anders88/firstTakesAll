package no.anderska.wta.dto;

public class QuestionDTO {
    
    private int id;
    private String text;
    private long point;
    private boolean answered;

    public static class QuestionFactory {
        private QuestionDTO question = new QuestionDTO();
        
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
        
        public QuestionDTO create() {
            return question;
        }
    }
    
    public static QuestionFactory factory() {
        QuestionFactory questionFactory = new QuestionFactory();
        return questionFactory;
    }
    
    private QuestionDTO() {
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
