package no.anderska.wta.dto;

public class CategoryDTO {
    private String id;
    private String name;
    private int points;
    private String answeredBy;

    public CategoryDTO(String id, String name, int points, String answeredBy) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.answeredBy = answeredBy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public String getAnsweredBy() {
        return answeredBy;
    }
}
