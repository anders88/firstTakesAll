package no.anderska.wta.questions.ts;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.fest.assertions.Assertions.assertThat;

public class TravellingSalesmanSolverTest {

    private final TravellingSalesmanSolver solver = new TravellingSalesmanSolver();
    private final CityDistances cityDistances = new CityDistances();

    @Test
    public void twoCitiesHasOneSolution() throws Exception {
        cityDistances.add(1,2,5);
        assertThat(solver.compute(cityDistances, 1).path).containsExactly(1, 2, 1);
    }

    @Test
    public void findPathWithFourCities() throws Exception {
        cityDistances.add(1,2,50);
        cityDistances.add(1,3,1);
        cityDistances.add(1,4,1);
        cityDistances.add(2,3,1);
        cityDistances.add(2,4,1);
        cityDistances.add(3,4,50);
        Solution so = solver.compute(cityDistances, 1);
        assertThat(so.path).containsExactly(1, 3, 2, 4, 1);
        assertThat(so.length).isEqualTo(4);
    }

    private void randomize(int numNodes) {
        Random rnd = new Random();
        for (int from=1;from<numNodes;from++) {
            for (int to=from+1;to<=numNodes;to++) {
                cityDistances.add(from,to,rnd.nextInt(40)+1);
            }
        }
    }

    @Test
    public void shuoldHandleBiggerBoards() throws Exception {
        randomize(12);

        Solution compute = solver.compute(cityDistances, 1);
        assertThat(compute.path.size()).isEqualTo(13);

    }
}
