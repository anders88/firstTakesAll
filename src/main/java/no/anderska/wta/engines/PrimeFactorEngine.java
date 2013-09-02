package no.anderska.wta.engines;

import no.anderska.wta.game.Engine;
import no.anderska.wta.game.Question;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactorEngine implements Engine {

    private List<Integer> primeFactor = new ArrayList<>();

    public PrimeFactorEngine(int maxNumber,int maxPicks) {
        if (maxNumber <= 2 || maxPicks <= 0) {
            throw new IllegalArgumentException("Parameters must be positive");
        }
        primeFactor.add(1);
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

    public List<Integer> getPrimeFactor() {
        return primeFactor;
    }

    @Override
    public List<Question> generateQuestions(String playerid) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String description() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int points() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
