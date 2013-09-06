package no.anderska.wta.engines;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperEngine {
    String solve(String board) {
        List<String> boardlines=lines(board);
        StringBuilder genboard=new StringBuilder();
        for (int row=0;row<boardlines.size();row++) {
            genboard.append("[");

            String line = boardlines.get(row);

            for (int col=0;col<line.length();col++) {
                if (line.charAt(col) == '*') {
                    genboard.append("*");
                } else {
                    genboard.append("0");
                }
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
