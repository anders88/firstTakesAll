package no.anderska.wta.questions.ts;

import org.junit.Test;

public class TravellingSalesmanSolverTest {

    private final TravellingSalesmanSolver solver = new TravellingSalesmanSolver();
    private final CityDistances cityDistances = new CityDistances();

    @Test
    public void twoCitiesHasOneSolution() throws Exception {
        cityDistances.add(1,2,5);
    }
}
