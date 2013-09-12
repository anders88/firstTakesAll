package no.anderska.wta.engines;

import no.anderska.wta.game.Engine;
import no.anderska.wta.game.Question;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimePrinter;

import java.util.List;

public class TimeCalculationEngine implements Engine {
    private RandomTimeGeneration random = new RandomTimeGeneration();



    @Override
    public List<Question> generateQuestions(String playerid) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String description() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int points() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Question sameDaySameZoneoneQuestion() {
        City from = random.pickRandomCity();
        City to = random.pickRandomCity();

        DateTime starting = new DateTime(2013, 9, 12, 0, 0, from.getTimeZone()).plusMinutes(random.pickMinutes(1,720));
        int durationMinutes=random.pickMinutes(60,240);

        StringBuilder quesText=new StringBuilder("From ");
        quesText.append(from.getName());
        quesText.append(" ");

        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd 'at' HH:mm");
        quesText.append(dateFormat.print(starting));
        quesText.append(" to ");
        quesText.append(to.getName());
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        quesText.append(". Flighttime is " + hours + "h " + minutes + "m");

        return new Question(quesText.toString(),"");
    }

    public void setRandom(RandomTimeGeneration random) {
        this.random = random;
    }
}
