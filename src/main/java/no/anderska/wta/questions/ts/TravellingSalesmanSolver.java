package no.anderska.wta.questions.ts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TravellingSalesmanSolver {
    private Set<Integer> allCities;
    private List<Integer> visited;
    private List<Integer> bestRoute;
    private int best;
    private int sum;
    private CityDistances cityDistances;


    public synchronized List<Integer> compute(CityDistances cityDistances, int start) {
        this.cityDistances = cityDistances;
        allCities = cityDistances.cities();
        visited = new ArrayList<>();

        visited.add(start);
        best = Integer.MAX_VALUE;
        sum = 0;

        walk();

        return bestRoute;
    }

    private void walk() {
        if (visited.size() == allCities.size()) {
            bestRoute = new ArrayList<>(visited);
            bestRoute.add(visited.get(0));
            best = sum;
            return;
        }
        if (sum < best) {
            for (int next : allCities) {
                if (visited.contains(next)) {
                    continue;
                }
                int distance = cityDistances.get(visited.get(visited.size()-1),next);
                sum = sum + distance;
                visited.add(next);

                walk();

                visited.remove(visited.size()-1);
                sum = sum - distance;
            }
        }

    }
}
