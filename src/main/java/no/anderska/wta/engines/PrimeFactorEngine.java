package no.anderska.wta.engines;

import no.anderska.wta.game.Engine;
import no.anderska.wta.game.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PrimeFactorEngine implements Engine {

    private List<Integer> primeFactor = new ArrayList<>();
    private int maxPicks;
    private int numberOfQuestions;

    private PrimeFactorEngine(int maxNumber,int maxPicks, int numberOfQuestions) {
        this.maxPicks = maxPicks;
        this.numberOfQuestions = numberOfQuestions;
        if (maxNumber <= 2 || maxPicks <= 0 || numberOfQuestions < 1) {
            throw new IllegalArgumentException("Parameters must be positive");
        }
        primeFactor.add(2);
        for (int num=3;num<=maxNumber;num++) {
            boolean found = false;
            for (int i=2;i <= num/2;i++) {
                if (num % i == 0) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                primeFactor.add(num);
            }
        }
    }

    public PrimeFactorEngine() {
        this(130,4,15);
    }

    public List<Integer> getPrimeFactor() {
        return primeFactor;
    }

    @Override
    public List<Question> generateQuestions(String playerid) {
        List<Question> result = new ArrayList<>(numberOfQuestions);
        Random random = new Random();
        for (int i=0;i<numberOfQuestions;i++) {
            int numFactors = random.nextInt(maxPicks) + 1;
            List<Integer> factors = new ArrayList<>();
            for (int j=0;j<numFactors;j++) {
                factors.add(primeFactor.get(random.nextInt(primeFactor.size())));
            }
            Collections.sort(factors);
            StringBuilder answer=new StringBuilder();
            int question = 1;
            for (Integer factor : factors) {
                if (!answer.toString().isEmpty()) {
                    answer.append("*");
                }
                answer.append(factor);
                question*=factor;
            }
            result.add(new Question("" + question,answer.toString()));
        }
        return result;
    }

    @Override
    public String description() {
        return "Return the prime factors of the number in ascending order. '7'='7', '14'='2*7' (not '7*2'), 12'='2*2*3'";
    }

    @Override
    public int points() {
        return 50;
    }
}
