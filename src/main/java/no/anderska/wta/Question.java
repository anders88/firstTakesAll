package no.anderska.wta;

public class Question {
    private int id;
    private String text;
    private int points;
    
    public Question(int id, String text, int points) {
        if (id < 0 || id > 1000000) {
            throw new IllegalArgumentException("Id must be between 0 and 1000000");
        }
        this.id = id;
        this.text = text;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPoints() {
        return points;
    }

    
    
}
