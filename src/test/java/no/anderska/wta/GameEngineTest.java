package no.anderska.wta;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import no.anderska.wta.dto.QuestionCategoryDTO;
import no.anderska.wta.servlet.PlayerHandler;

import org.junit.Test;

public class GameEngineTest {
    @Test
    public void shouldCallDescription() {
        QuestionCategoryEngine dummyQCE = mock(QuestionCategoryEngine.class);
        PlayerHandler playerHandler = mock(PlayerHandler.class);
        GameEngine gameEngine = new GameEngine(Arrays.asList(dummyQCE),playerHandler);
        when(dummyQCE.getDescription()).thenReturn("The description");
        
        List<QuestionCategoryDTO> allCategories = gameEngine.allCategories();
        
        verify(dummyQCE).getDescription();
        
        assertThat(allCategories).hasSize(1);
        QuestionCategoryDTO questionCategoryDTO = allCategories.get(0);
        assertThat(questionCategoryDTO.getDescription()).isEqualTo("The description");
    }
}
