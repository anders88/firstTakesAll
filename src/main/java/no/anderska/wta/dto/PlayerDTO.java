package no.anderska.wta.dto;

public class PlayerDTO {
    private String name;
    private int points;

    public PlayerDTO(String name,int points) {
        this.name = name;
        this.points = points;
    }


    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}
