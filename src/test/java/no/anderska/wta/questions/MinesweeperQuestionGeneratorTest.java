package no.anderska.wta.questions;

import no.anderska.wta.questions.MinesweeperQuestionGenerator;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class MinesweeperQuestionGeneratorTest {
    private MinesweeperQuestionGenerator questionGenerator = new MinesweeperQuestionGenerator();


    @Test
    public void singleMineIsSingleMine() throws Exception {
        assertThat(questionGenerator.solve("[*]")).isEqualTo("[*]");
    }

    @Test
    public void singeleEmptyCell() throws Exception {
        assertThat(questionGenerator.solve("[-]")).isEqualTo("[0]");
    }

    @Test
    public void shouldSplitLines() throws Exception {
        List<String> lines = questionGenerator.lines("[12]");
        assertThat(lines).hasSize(1);
        assertThat(lines.get(0)).isEqualTo("12");
        lines = questionGenerator.lines("[12][34]");
        assertThat(lines).hasSize(2);
        assertThat(lines.get(0)).isEqualTo("12");
        assertThat(lines.get(1)).isEqualTo("34");
    }

    @Test
    public void shouldCountMines() throws Exception {
        assertThat(questionGenerator.solve("[-*-]")).isEqualTo("[1*1]");
        assertThat(questionGenerator.solve("[--*][*--][---]")).isEqualTo("[12*][*21][110]");

    }
}
