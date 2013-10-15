package no.anderska.wta.questions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import no.anderska.wta.game.QuestionGenerator;

public class QuestionGeneratorFactory {

    private final Map<String, Class<? extends QuestionGenerator>> allGenerators = new HashMap<>();

    public QuestionGenerator createGenerator(String category) {
        Class<? extends QuestionGenerator> generatorClass = allGenerators.get(category);
        if (generatorClass == null) {
            throw new IllegalArgumentException("Unknown category " + category);
        }
        try {
            return generatorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<String> getAllCategoryNames() {
        return allGenerators.keySet();
    }

    public void put(String category, Class<? extends QuestionGenerator> questionGenerator) {
        this.allGenerators.put(category, questionGenerator);
    }

    public static QuestionGeneratorFactory withAllQuestions() {
        QuestionGeneratorFactory questionFactory = new QuestionGeneratorFactory();

        questionFactory.put("Echo", EchoQuestionGenerator.class);
        questionFactory.put("Length", WordCountQuestionGenerator.class);
        questionFactory.put("First", FirstCharacterQuestionGenerator.class);
        questionFactory.put("Last", LastCharacterQuestionGenerator.class);
        questionFactory.put("Reverse", ReverseStringQuestionGenerator.class);
        questionFactory.put("HtmlEscape", HtmlEscapeQuestionGenerator.class);
        questionFactory.put("Addition", AdditionQuestionGenerator.class);
        questionFactory.put("MaxOccurence", MaxLetterOccurenceQuestionGenerator.class);
        questionFactory.put("Fibonacci", FibonacciQuestionGenerator.class);
        questionFactory.put("Minesweeper", MinesweeperQuestionGenerator.class);
        questionFactory.put("PrimeFactor", PrimeFactorQuestionGenerator.class);
        questionFactory.put("ToRoman", RomanQuestionGenerator.class);
        questionFactory.put("FromRoman", ToRomanNumberQuestionGenerator.class);
        questionFactory.put("Computation", ComputationQuestionGenerator.class);
        questionFactory.put("Equation", EquationQuestionGenerator.class);
        questionFactory.put("TimeCalc", TimeCalculationGenerator.class);
        questionFactory.put("Sudoku", FixedSudokoGenerator.class);

        return questionFactory;
    }

}
