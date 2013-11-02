package no.anderska.wta.questions.ts;

import java.util.*;

public class TravellingSalesmanSolver {

    private int startNode;
    private Map<Task,Solution> solutionMap;


    private CityDistances cityDistances;

    public synchronized Solution compute(CityDistances cityDistances, int start) {
        this.cityDistances = cityDistances;
        this.startNode = start;
        this.solutionMap = new HashMap<>();

        return walk(new HashSet<>(cityDistances.cities()),start);
    }

    private Solution walk(Set<Integer> cities,int start) {
        Task task = new Task(cities,start);
        Solution storedSolution = solutionMap.get(task);
        if (storedSolution != null) {
            return storedSolution;
        }

        cities.remove(start);
        if (cities.size() == 0) {
            List<Integer> lastStep = new ArrayList<>();
            lastStep.add(start);
            lastStep.add(startNode);
            int lastStepLength = cityDistances.get(start,startNode);
            return new Solution(lastStep,lastStepLength);
        }

        int best = Integer.MAX_VALUE;
        Solution restPath = null;

        for (Integer nextDestination : cities) {
            Solution rest = walk(new HashSet<Integer>(cities),nextDestination);
            if (rest.length < best) {
                restPath = rest;
                best = rest.length;
            }
        }
        List<Integer> path = new ArrayList<>();
        path.add(start);
        path.addAll(restPath.path);
        int length = cityDistances.get(start,restPath.path.get(0)) + restPath.length;

        Solution solution = new Solution(path, length);
        solutionMap.put(task,solution);
        return solution;
    }


}
