package no.anderska.wta.questions;


class ReverseStringQuestionGenerator extends AbstractWordQuestionGenerator {

    public ReverseStringQuestionGenerator() {
        super(20, "Reverse the text. 'abc' => 'cba'");
    }

    @Override
    protected String createAnswer(String text) {
        return new StringBuilder(text).reverse().toString();
    }
}
