package no.anderska.wta;

import java.util.List;


public interface QuestionCategoryEngine {
    public boolean checkAnswer(String gamerId, int questionId,String answer);
    public String getDescription();
    public List<Question> myQuestions();
    
}
