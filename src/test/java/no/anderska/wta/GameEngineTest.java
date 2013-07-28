package no.anderska.wta;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import no.anderska.wta.dto.QuestionCategoryDTO;
import no.anderska.wta.dto.QuestionDTO;
import no.anderska.wta.servlet.PlayerHandler;

import org.junit.Test;

public class GameEngineTest {
    private PlayerHandler playerHandler = mock(PlayerHandler.class);
    private QuestionCategoryEngine dummyQCE = mock(QuestionCategoryEngine.class);

    @Test
    public void shouldCallDescription() {
        List<QuestionCategoryEngine> dummyCategories = Arrays.asList(dummyQCE);
        GameEngine gameEngine = new GameEngine(dummyCategories,playerHandler);
        when(dummyQCE.getDescription()).thenReturn("The description");
        
        List<QuestionCategoryDTO> allCategories = gameEngine.allCategories();
        
        verify(dummyQCE).getDescription();
        
        assertThat(allCategories).hasSize(1);
        QuestionCategoryDTO questionCategoryDTO = allCategories.get(0);
        assertThat(questionCategoryDTO.getDescription()).isEqualTo("The description");
    }
    
    @Test
    public void shouldMapQuestionsAtCreation() {
        QuestionCategoryEngine anotherDummyQCE = mock(QuestionCategoryEngine.class);
        
        when(dummyQCE.myQuestions()).thenReturn(Arrays.asList(new Question(1,"Question one",10)));
        when(anotherDummyQCE.myQuestions()).thenReturn(Arrays.asList(new Question(1,"Question two",10)));
        
        List<QuestionCategoryEngine> dummyCategories = Arrays.asList(dummyQCE,anotherDummyQCE);
        
        GameEngine gameEngine = new GameEngine(dummyCategories,playerHandler);
        
        List<QuestionCategoryDTO> allCategories = gameEngine.allCategories();
        
        assertThat(allCategories).hasSize(2);
        
        QuestionCategoryDTO qcatOne = allCategories.get(0);
        QuestionCategoryDTO qcatTwo = allCategories.get(1);
        
        assertThat(qcatOne.getId()).isNotEqualTo(qcatTwo.getId());
        
        List<QuestionDTO> questionsCatOne = gameEngine.listCategory(qcatOne.getId());
        List<QuestionDTO> questionsCatTwo = gameEngine.listCategory(qcatTwo.getId());
        
        assertThat(questionsCatOne).hasSize(1);
        assertThat(questionsCatTwo).hasSize(1);
        
        assertThat(questionsCatOne.get(0).getId()).isNotEqualTo(questionsCatTwo.get(0).getId());
    }
}
