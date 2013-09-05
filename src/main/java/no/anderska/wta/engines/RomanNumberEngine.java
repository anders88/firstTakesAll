package no.anderska.wta.engines;

import no.anderska.wta.game.Engine;
import no.anderska.wta.game.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RomanNumberEngine implements Engine {

    public RomanNumberEngine(int maxNumber, int numberOfQuestions) {
        if (maxNumber < 1 || maxNumber > 1000 || numberOfQuestions < 1) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.maxNumber = maxNumber;
        this.numberOfQuestions = numberOfQuestions;
    }

    private int maxNumber;
    private int numberOfQuestions;


    @Override
    public List<Question> generateQuestions(String playerid) {

        List<Question> result = new ArrayList<>();
        Random random = new Random();

        for (int i=0;i<numberOfQuestions;i++) {
            int pickedNumber = random.nextInt(maxNumber) + 1;
            result.add(new Question("" + pickedNumber,romanNumber(pickedNumber)));
        }

        return result;
    }

    @Override
    public String description() {
        return "Compute the Roman number of a given number (max " + maxNumber + "). E.g. '3' = 'III' or '42'='XLII'";
    }

    @Override
    public int points() {
        return 4;
    }

    private static class Number {
        private int num;
        StringBuilder res = new StringBuilder();

        private Number(int num) {
            this.num = num;
        }

        public boolean match(int val, String sign) {
            if (num < val) {
                return false;
            }
            res.append(sign);
            num-=val;
            return true;
        }

        private StringBuilder getRes() {
            return res;
        }
    }

    public String romanNumber(int num) {
        Number n = new Number(num);
        while (
                n.match(1000, "M") ||
                n.match(900, "CM") ||
                n.match(500, "D") ||
                n.match(400, "CD") ||
                n.match(100, "C") ||
                n.match(90, "XC") ||
                n.match(50, "L") ||
                n.match(40, "XL") ||
                n.match(10, "X") ||
                n.match(9, "IX") ||
                n.match(5, "V") ||
                n.match(4, "IV") ||
                n.match(1, "I")
                ) {}
        return n.getRes().toString();
    }


}
