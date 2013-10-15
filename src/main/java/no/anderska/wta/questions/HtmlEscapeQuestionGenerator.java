package no.anderska.wta.questions;


public class HtmlEscapeQuestionGenerator extends AbstractWordQuestionGenerator {

    public HtmlEscapeQuestionGenerator() {
        super(10, 20, "Escape HTML text. <b>Scream & Should</b> => &lt;b&gt;Scream &amp; Shout&lt;/b&gt;");
    }

    @Override
    protected String createQuestionString() {
        StringBuilder html = new StringBuilder();

        if (random.nextBoolean()) html.append(randomSentence());

        String outerTag = randomTag();
        html.append("<" + outerTag +  ">");

        if (random.nextBoolean()) html.append(randomSentence());

        if (random.nextBoolean()) {
            String innerTag = randomTag();
            if (random.nextBoolean()) {
                html.append("<" + innerTag +  ">");
                html.append(randomSentence());
                html.append("</" + innerTag +  ">");
            } else {
                html.append("<" + innerTag + "/>");
            }
        }

        if (random.nextBoolean()) html.append(randomSentence());
        html.append("</" + outerTag +  ">");

        if (random.nextBoolean()) html.append(randomSentence());

        return html.toString();
    }

    private String randomTag() {
        return random("em", "b", "strong", "i", "h2", "img");
    }

    @Override
    protected String createAnswer(String question) {
        return question.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }

}
