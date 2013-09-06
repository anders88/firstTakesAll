package no.anderska.wta.engines;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class MinesweeperEngineTest {
    private MinesweeperEngine engine = new MinesweeperEngine();


    @Test
    public void singleMineIsSingleMine() throws Exception {
        assertThat(engine.solve("[*]")).isEqualTo("[*]");
    }

    @Test
    public void singeleEmptyCell() throws Exception {
        assertThat(engine.solve("[-]")).isEqualTo("[0]");
    }

    @Test
    public void shouldSplitLines() throws Exception {
        List<String> lines = engine.lines("[12]");
        assertThat(lines).hasSize(1);
        assertThat(lines.get(0)).isEqualTo("12");
        lines = engine.lines("[12][34]");
        assertThat(lines).hasSize(2);
        assertThat(lines.get(0)).isEqualTo("12");
        assertThat(lines.get(1)).isEqualTo("34");

    }
}
