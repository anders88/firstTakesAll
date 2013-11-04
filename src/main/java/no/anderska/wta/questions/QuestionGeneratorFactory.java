package no.anderska.wta.questions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import no.anderska.wta.game.QuestionGenerator;

public class QuestionGeneratorFactory {

    private final Map<String, Class<? extends QuestionGenerator>> allGenerators = new HashMap<>();
    private final Map<String, Integer> allPoints = new HashMap<>();

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

    public void put(String category, Class<? extends QuestionGenerator> questionGenerator,int points) {
        this.allGenerators.put(category, questionGenerator);
        this.allPoints.put(category,points);
    }

    public int getPoint(String category) {
        Integer p = allPoints.get(category);
        if (p == null) {
            throw new IllegalArgumentException("Unknown category " + category);
        }
        return p;
    }


    public static QuestionGeneratorFactory withAllQuestions() {
        QuestionGeneratorFactory questionFactory = new QuestionGeneratorFactory();

        questionFactory.put("Echo", EchoQuestionGenerator.class, 2);
        questionFactory.put("Length", WordCountQuestionGenerator.class, 5);
        questionFactory.put("First", FirstCharacterQuestionGenerator.class, 5);
        questionFactory.put("Last", LastCharacterQuestionGenerator.class, 5);
        questionFactory.put("Reverse", ReverseStringQuestionGenerator.class, 10);
        questionFactory.put("HtmlEscape", HtmlEscapeQuestionGenerator.class, 20);
        questionFactory.put("Sort", SortQuestionGenerator.class, 15);
        questionFactory.put("NumberSort", SortNumericQuestionGenerator.class, 25);
        questionFactory.put("RomanSort", SortRomanQuestionGenerator.class, 50);
        questionFactory.put("Addition", AdditionQuestionGenerator.class, 10);
        questionFactory.put("MaxOccurence", MaxLetterOccurenceQuestionGenerator.class, 15);
        questionFactory.put("Fibonacci", FibonacciQuestionGenerator.class, 20);
        questionFactory.put("Minesweeper", MinesweeperQuestionGenerator.class, 50);
        questionFactory.put("PrimeFactor", PrimeFactorQuestionGenerator.class, 50);
        questionFactory.put("ToRoman", ToRomanQuestionGenerator.class, 40);
        questionFactory.put("ToSmallRoman", ToRomanQuestionGenerator.Small.class, 15);
        questionFactory.put("FromRoman", FromRomanNumberQuestionGenerator.class, 40);
        questionFactory.put("FromSmallRoman", FromRomanNumberQuestionGenerator.Small.class, 15);
        questionFactory.put("Computation", ComputationQuestionGenerator.class, 25);
        questionFactory.put("Equation", EquationQuestionGenerator.class, 45);
        questionFactory.put("TimeCalc", TimeCalculationGenerator.class, 30);
        questionFactory.put("Sudoku", FixedSudokoGenerator.class, 65);
        questionFactory.put("TrivialNumbers", NumberAsTextQuestionGenerator.TrivialNumbers.class, 10);
        questionFactory.put("SmallNumbers", NumberAsTextQuestionGenerator.SmallNumbers.class, 20);
        questionFactory.put("NormalNumbers", NumberAsTextQuestionGenerator.NormalNumbers.class, 30);
        questionFactory.put("LargeNumbers", NumberAsTextQuestionGenerator.LargeNumbers.class, 40);
        questionFactory.put("TravellingSalesman", TravellingSalesmanQuestionGenerator.class, 100);


        return questionFactory;
    }


}
