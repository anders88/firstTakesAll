package no.anderska.wta.questions;

import no.anderska.wta.game.Question;
import no.anderska.wta.questions.timegen.City;
import no.anderska.wta.questions.timegen.RandomTimeGeneration;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

class TimeCalculationGenerator extends AbstractQuestionGenerator {
    private RandomTimeGeneration random = new RandomTimeGeneration();

    public TimeCalculationGenerator() {
        super(30, "A plane travels to and from a place. Calculate the local time when it lands on format 'yyyy-mm-dd at hh:mm' eg. '2013-09-03 at 16:34'");
    }

    Question makeRandomTrip() {
        City from = random.pickRandomCity();
        City to = random.pickRandomCity();

        DateTime starting = new DateTime(2013, 9, 12, 0, 0, from.getTimeZone()).plusMinutes(random.pickMinutes());
        int durationMinutes=random.pickMinutes();

        StringBuilder quesText=new StringBuilder("From ");
        quesText.append(from.getName());
        quesText.append(" ");

        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd 'at' HH:mm");
        quesText.append(dateFormat.print(starting));
        quesText.append(" to ");
        quesText.append(to.getName());
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        quesText.append(". Flighttime " + hours + "h " + minutes + "m");

        DateTime landing = starting.plusMinutes(durationMinutes).withZone(to.getTimeZone());

        return new Question(quesText.toString(),dateFormat.print(landing));
    }

    public void setRandom(RandomTimeGeneration random) {
        this.random = random;
    }

    @Override
    protected Question createQuestion() {
        return makeRandomTrip();
    }
}
