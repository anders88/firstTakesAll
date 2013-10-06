package no.anderska.wta.questions;

import no.anderska.wta.game.Question;
import no.anderska.wta.game.QuestionGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixedSudokoGenerator implements QuestionGenerator {
    private final List<Question> questions;

    public FixedSudokoGenerator() {
        questions = new ArrayList<>();
        List<Integer[][]> boards = new SudokoEngine().readBoardsFromResource("sudukoBoards.txt");
        for (int boardNo = 0;boardNo<boards.size()-1;boardNo+=2) {
            String question = boardToString(boards.get(boardNo));
            String fact = boardToString(boards.get(boardNo+1));
            questions.add(new Question(question,fact));
        }

    }

    private String boardToString(Integer[][] board) {
        StringBuilder boardStr = new StringBuilder();
        for (int y=0;y<board.length;y++) {
            boardStr.append("[");
            for (int x=0;x<board.length;x++) {
                String cell = board[y][x] != null ? "" + board[y][x] : "-";
                boardStr.append(cell);
            }
            boardStr.append("]");
        }
        return boardStr.toString();
    }

    @Override
    public List<Question> generateQuestions(String playerid) {
        ArrayList<Question> result = new ArrayList<>(questions);
        Collections.shuffle(result);
        return result;
    }

    @Override
    public String description() {
        return "Solve a 9X9 sudoku board. Each row, column and 3X3 square should have numbers 1-9 Board is denoted [123456789][---------]... The - should be replaced with correct numbers.";
    }

    @Override
    public int points() {
        return 50;
    }
}
