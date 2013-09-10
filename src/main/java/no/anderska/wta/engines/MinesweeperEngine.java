package no.anderska.wta.engines;

import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.game.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinesweeperEngine implements QuestionGenerator {
    int rows;
    int cols;
    private int percentMines;
    private int numberOfBoards;


    private MinesweeperEngine(int rows, int cols, int percentMines, int numberOfBoards) {
        if (rows < 1 || cols < 1 || numberOfBoards < 1 || percentMines < 0 || percentMines > 100) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.rows = rows;
        this.cols = cols;
        this.percentMines = percentMines;
        this.numberOfBoards = numberOfBoards;
    }

    public MinesweeperEngine() {
        this(16,16,18,10);
    }

    private int mineVal(List<String> boardlines,int row,int col) {
        if (row < 0 || row >= boardlines.size() || col < 0 || col >= boardlines.get(row).length()) {
            return 0;
        }
        return boardlines.get(row).charAt(col) == '*' ? 1 : 0;
    }

    String solve(String board) {
        List<String> boardlines=lines(board);
        StringBuilder genboard=new StringBuilder();
        for (int row=0;row<boardlines.size();row++) {
            genboard.append("[");

            String line = boardlines.get(row);

            for (int col=0;col<line.length();col++) {
                if (line.charAt(col) == '*') {
                    genboard.append("*");
                    continue;
                }
                int minecount =
                        mineVal(boardlines,row-1,col-1) +
                        mineVal(boardlines,row-1,col) +
                        mineVal(boardlines,row-1,col+1) +
                        mineVal(boardlines,row,col-1) +
                        mineVal(boardlines,row,col+1) +
                        mineVal(boardlines,row+1,col-1) +
                        mineVal(boardlines,row+1,col) +
                        mineVal(boardlines,row+1,col+1);
                genboard.append(minecount);
            }

            genboard.append("]");
        }
        return genboard.toString();
    }

    List<String> lines(String board) {
        List<String> result = new ArrayList<>();
        int pos = board.indexOf("[");
        while (pos != -1) {
            int endpos=board.indexOf("]",pos);
            result.add(board.substring(pos+1,endpos));
            pos = board.indexOf("[",pos+1);
        }
        return result;
    }

    private String generateBoard() {
        StringBuilder board = new StringBuilder();
        Random random = new Random();
        for (int row=0;row<rows;row++) {
            board.append("[");
            for (int col=0;col<cols;col++) {
                boolean mineHere = (random.nextInt(100) < percentMines);
                board.append(mineHere ? "*" : "-");
            }
            board.append("]");
        }
        return board.toString();
    }

    @Override
    public List<Question> generateQuestions(String playerid) {
        List<Question> questions = new ArrayList<>();
        for (int i=0;i<numberOfBoards;i++) {
            String genboard = generateBoard();
            questions.add(new Question(genboard,solve(genboard)));
        }
        return questions;
    }

    @Override
    public String description() {
        return "Display the number of mines on a minesweeper board with mines denoted * and empty fields denoted -. [-*-] => [1*1] and [--*][*--][---] => [12*][*21][110]";
    }

    @Override
    public int points() {
        return 50;
    }
}
