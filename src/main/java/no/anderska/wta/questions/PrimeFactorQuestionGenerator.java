package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import no.anderska.wta.game.Question;

class PrimeFactorQuestionGenerator extends AbstractQuestionGenerator {

    private static final String DESCRIPTION = "Return the prime factors of the number in ascending order. '7'='7', '14'='2*7' (not '7*2'), 12'='2*2*3'";
    private final List<Integer> primeFactor = new ArrayList<>();
    private final int maxPicks;

    private PrimeFactorQuestionGenerator(int maxNumber,int maxPicks, int numberOfQuestions) {
        super(numberOfQuestions, 50, DESCRIPTION);
        this.maxPicks = maxPicks;
        if (maxNumber <= 2 || maxPicks <= 0) {
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

    public PrimeFactorQuestionGenerator() {
        this(130,4,15);
    }

    public List<Integer> getPrimeFactor() {
        return primeFactor;
    }

    @Override
    protected Question createQuestion() {
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
        return new Question("" + question,answer.toString());
    }

}
