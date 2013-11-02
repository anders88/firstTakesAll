package no.anderska.wta.questions;

import no.anderska.wta.game.Question;
import no.anderska.wta.questions.ts.CityDistances;
import no.anderska.wta.questions.ts.Solution;
import no.anderska.wta.questions.ts.TravellingSalesmanSolver;

public class TravellingSalesmanQuestionGenerator extends AbstractQuestionGenerator {

    private static final String DESCRIPTION =
            "Find the shortest path. Cities have numbers from 1 to 12. Start in one city, go between all cities and come back to the starting city." +
                    "Distancences are given on the form '1-2=8,1-3=11'. Meaning that the distance from 1 to 2 is 8 and from 1 to 3 is 11. (Obiously the " +
                    "distance from 2 to one will also be 8). Example '1-2=50,1-3=1,1-4=1,2-3=1,2-4=1,3-4=50' has correct answer '4' with a path of" +
                    " for example '1-3-2-4-1'. (Only the distance should be provided in the answer - not the path)";

    public TravellingSalesmanQuestionGenerator() {
        super(4, DESCRIPTION);
    }

    @Override
    protected Question createQuestion() {
        CityDistances distances = new CityDistances();
        StringBuilder qustr = new StringBuilder();
        boolean first = true;
        for (int i=1;i<12;i++) {
            for (int j=i+1;j<=12;j++) {
                if (!first) {
                    qustr.append(",");
                }
                first=false;

                int distance = random.nextInt(40)+1;
                distances.add(i,j,distance);
                qustr.append(i);
                qustr.append("-");
                qustr.append(j);
                qustr.append("=");
                qustr.append(distance);
            }
        }
        TravellingSalesmanSolver travellingSalesmanSolver = new TravellingSalesmanSolver();
        Solution solution = travellingSalesmanSolver.compute(distances, 1);

        return new Question(qustr.toString(),"" + solution.length);
    }
}
