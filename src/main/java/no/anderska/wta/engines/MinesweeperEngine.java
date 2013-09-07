package no.anderska.wta.engines;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperEngine {
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
}
