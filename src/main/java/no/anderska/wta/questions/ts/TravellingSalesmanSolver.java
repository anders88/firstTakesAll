package no.anderska.wta.questions.ts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TravellingSalesmanSolver {
    private Set<Integer> allCities;
    private List<Integer> visited;

    public synchronized List<Integer> compute(CityDistances cityDistances, int start) {
        allCities = cityDistances.cities();
        visited = new ArrayList<>();

        allCities.add(start);
        walk();

        return visited;
    }

    private void walk() {
        if (visited.size() == allCities.size()) {
            visited.add(visited.get(0));
            return;
        }
        for (int next : allCities) {
            if (visited.contains(next)) {
                continue;
            }
            visited.add(next);
            walk();
        }

    }
}
