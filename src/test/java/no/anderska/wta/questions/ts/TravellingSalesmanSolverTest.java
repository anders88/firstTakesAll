package no.anderska.wta.questions.ts;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TravellingSalesmanSolverTest {

    private final TravellingSalesmanSolver solver = new TravellingSalesmanSolver();
    private final CityDistances cityDistances = new CityDistances();

    @Test
    public void twoCitiesHasOneSolution() throws Exception {
        cityDistances.add(1,2,5);
        assertThat(solver.compute(cityDistances, 1)).containsExactly(1,2,1);
    }


}
