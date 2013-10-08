package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

import java.util.HashMap;
import java.util.Map;

public class MaxLetterOccurenceQuestionGenerator extends AbstractQuestionGenerator {
    public MaxLetterOccurenceQuestionGenerator() {
        super(25, 15, "Return the letter with most occurenses. If equal return first in alphabet' I.e 'abbc' => 'b' and 'accee' => 'c'");
    }

    private String randomString(int length) {
        StringBuilder res = new StringBuilder();
        for (int i=0;i<length;i++) {
            char c = (char) ('a' + random.nextInt(26));
            res.append(c);
        }
        return res.toString();
    }

    @Override
    protected Question createQuestion() {
        String question = randomString(50);
        String answer = findMaxOccurence(question);
        return new Question(question,answer);
    }

    public String findMaxOccurence(String text) {
        Map<Character,Integer> occurences = new HashMap<>();
        for (Character c : text.toCharArray()) {
            Integer occ = occurences.get(c);
            if (occ == null) {
                occ = 0;
            }
            occ++;
            occurences.put(c,occ);
        }
        Map.Entry<Character,Integer> max = null;
        for (Map.Entry<Character,Integer> entry : occurences.entrySet()) {
            if (max == null) {
                max = entry;
                continue;
            }
            if (entry.getValue() > max.getValue() || (entry.getValue() == max.getValue() && entry.getKey() < max.getKey())) {
                max = entry;
            }
        }
        return "" + max.getKey();
    }

}
