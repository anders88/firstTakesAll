package no.anderska.wta.engines;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SudokoEngineTest {

    private final SudokoEngine sudokoEngine = new SudokoEngine();

    @Test
    public void shouldParseStringToBoard() throws Exception {
        String boardStr =
                "123456789\n" +
                "234567891\n" +
                "234567891\n" +
                "234567891\n" +
                "234567891\n" +
                "234567891\n" +
                "234567891\n" +
                "---------\n" +
                "345678912\n";

        Integer [][] board = sudokoEngine.readBoard(boardStr, "\n");

        assertThat(board).hasSize(9);
        assertThat(board[0]).hasSize(9);


        assertThat(board[0][0]).isEqualTo(1);

    }


}
