package no.anderska.wta.questions.ts;

import java.util.HashSet;
import java.util.Set;

public class Task {
    private final Set<Integer> cities;
    private final int start;

    public Task(Set<Integer> cities, int start) {
        this.cities = new HashSet<>(cities);
        this.start = start;
    }

    @Override
    public int hashCode() {
        return new Integer(start).hashCode() + new Integer(cities.size()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof  Task)) {
            return false;
        }
        Task task = (Task) obj;
        if (start != task.start) {
            return false;
        }
        if (cities.size() != task.cities.size()) {
            return false;
        }
        return cities.containsAll(task.cities);
    }
}
