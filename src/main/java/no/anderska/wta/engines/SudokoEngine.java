package no.anderska.wta.engines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokoEngine {

    private final int size = 9;
    private final int boxsize = 3;


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

    public List<Integer[][]> readBoardsFromResource(String resourceName) {
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

    private boolean checkPart(int strow,int stcol,Integer[][] board) {
        Set<Integer> numbers = new HashSet<>();
        for (int row = stcol; row < boxsize; row++) {
            for (int col = 0; col < boxsize; col++) {
                if (isPresent(numbers,board[row][col])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPresent(Set<Integer> numbers,Integer n) {
        if (n == null) {
            return false;
        }
        return !numbers.add(n);
    }

    public boolean isLegal(Integer[][] board) {

        for (int row = 0; row < size; row++) {
            Set<Integer> numbers = new HashSet<>();
            for (int col = 0; col < size; col++) {
                if (isPresent(numbers,board[row][col])) {
                    return false;
                }
            }
        }

        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                Set<Integer> numbers = new HashSet<>();
                if (isPresent(numbers,board[row][col])) {
                    return false;
                }
            }
        }

        for (int row = 0; row < size; row+=boxsize) {
            for (int col = 0; col < size; col+=boxsize) {
                if (!checkPart(row,col,board)) {
                    return false;
                }
            }
        }

        return true;
    }
}
