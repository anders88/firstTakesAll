package no.anderska.wta.engines;

public class SudokoEngine {

    private final int size = 9;

    public Integer[][] readBoard(String boardStr, String delimeter) {
        Integer[][] board = new Integer[size][size];

        String[] lines = boardStr.split(delimeter);
        if (lines == null || lines.length != size) {
            return null;
        }
        for (int row=0;row< size;row++) {
            if (lines[row] == null || lines[row].length() != size) {
                return null;
            }
            for (int col=0;col< size;col++) {
                String cell = lines[row].substring(col, col + 1);
                try {
                    board[row][col]="-".equals(cell) ? null : new Integer(cell);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return board;
    }
}
