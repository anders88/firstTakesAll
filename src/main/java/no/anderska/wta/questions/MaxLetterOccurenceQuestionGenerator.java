package no.anderska.wta.questions;

import java.util.Arrays;

class MaxLetterOccurenceQuestionGenerator extends AbstractWordQuestionGenerator {
    public MaxLetterOccurenceQuestionGenerator() {
        super(25, 15, "Return the letter with most occurenses. If equal return first in alphabet' I.e 'abbc' => 'b' and 'accee' => 'c'");
    }

    @Override
    protected String createAnswer(String question) {
        return newImpl(question);
    }

    private String newImpl(String question) {
        char[] charArray = question.toCharArray();
        Arrays.sort(charArray);

        char result = 'a';
        int count = 0;

        char currentChar = 'a';
        int currentCount = 0;

        for (char c : charArray) {
            if (c == currentChar) {
                currentCount++;
            } else {
                if (currentCount > count) {
                    result = currentChar;
                    count = currentCount;
                }
                currentChar = c;
                currentCount = 1;
            }
        }
        if (currentCount > count) {
            result = currentChar;
            count = currentCount;
        }

        return String.valueOf(result);
    }

}
