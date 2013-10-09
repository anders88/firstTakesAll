package no.anderska.wta.questions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SudokoEngine {

    private final int size = 9;

    public Integer[][] readBoard(String boardStr, String delimeter) {
        if (boardStr == null) {
            return null;
        }
        Integer[][] board = new Integer[size][size];

        String[] lines = boardStr.split(delimeter);
        if (lines == null || lines.length != size) {
            return null;
        }
        for (int row = 0; row < size; row++) {
            if (lines[row] == null || lines[row].length() != size) {
                return null;
            }
            for (int col = 0; col < size; col++) {
                String cell = lines[row].substring(col, col + 1);
                if ("0".equals(cell)) {
                    return null;
                }
                try {
                    board[row][col] = "-".equals(cell)  ? null : new Integer(cell);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return board;
    }

    List<Integer[][]> readBoardsFromResource(String resourceName) {
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(resourceName)))) {
            List<Integer[][]> res = new ArrayList<>();
            int onRow = 0;
            StringBuilder boardStr = new StringBuilder();
            for (String line=reader.readLine();line != null;line=reader.readLine()) {
                if (line.startsWith(";")) {
                    continue;
                }
                if ("+++".equals(line))  {
                    break;
                }
                onRow++;
                boardStr.append(line + "\n");
                if (onRow == size) {
                    Integer[][] board = readBoard(boardStr.toString(), "\n");
                    if (board == null) {
                        throw new RuntimeException("Illegal board " + boardStr);
                    }
                    res.add(board);
                    boardStr = new StringBuilder();
                    onRow=0;
                }
            }

            return res;
        } catch (IOException e) {
            return null;
        }
    }

}
