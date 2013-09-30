package no.anderska.wta.questions;

import no.anderska.wta.game.Question;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static no.anderska.wta.questions.CalculusGenerator.TYPE_ADD;
import static no.anderska.wta.questions.CalculusGenerator.TYPE_NUMBER;
import static no.anderska.wta.questions.CalculusGenerator.TYPE_MULT;
import static no.anderska.wta.questions.CalculusGenerator.TYPE_SUBSTR;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComputationQuestionGenerationTest {
    CalculusGenerator generator = new CalculusGenerator();
    Random random = mock(Random.class);

    @Before
    public void setUp() throws Exception {
        generator.setRandom(random);
    }

    @Test
    @Ignore
    public void shouldGenerate() throws Exception {
        CalculusGenerator generator = new CalculusGenerator();

        Random rnd = new Random() {
            @Override
            public int nextInt(int n) {
                int res = super.nextInt(n);
                System.out.println(n + ";" + res);
                return res;
            }
        };

        generator.setRandom(rnd);

        String[] qanda = generator.generateComputation();
        System.out.println(qanda[0] + "->" + qanda[1]);
    }

    @Test
    public void shouldHandleSimple() throws Exception {
        when(random.nextInt(anyInt())).thenReturn(0,41);
        checkEqAndAnswer("42", "42");
    }

    @Test
    public void basicAddition() throws Exception {
        when(random.nextInt(anyInt())).thenReturn(TYPE_ADD,TYPE_NUMBER,3,TYPE_NUMBER,2);
        checkEqAndAnswer("4+3", "7");

    }

    private void checkEqAndAnswer(String expectedEq, String eqAnswer) {
        String[] qanda = generator.generateComputation();
        Assertions.assertThat(qanda[0]).isEqualTo(expectedEq);
        Assertions.assertThat(qanda[1]).isEqualTo(eqAnswer);
    }

    @Test
    @Ignore
    public void shouldCompute() throws Exception {
        CalculusGenerator generator = new CalculusGenerator();
        Random random = mock(Random.class);
        when(random.nextInt(anyInt())).thenReturn(1,
                2,
                0,
                40,
                2,
                3,
                0,
                8,
                3,
                12,
                0,
                22,
                2,
                1,
                2,
                0,
                12,
                3,
                3,
                41,
                0,
                28,
                1,
                1,
                0,
                8,
                3,
                0,
                29,
                1,
                3,
                1,
                0,
                1,
                0,
                31,
                3,
                3,
                33,
                1,
                0);
        generator.setRandom(random);

        //for (int i=0;i<42;i++) {
        //    System.out.println(random.nextInt(3));
        //}


        String[] qanda = generator.generateComputation();
        Assertions.assertThat(qanda[0]).isEqualTo("41-9*13-23+13-42*29+9+30*4+2+32-34*1");
        Assertions.assertThat(qanda[1]).isEqualTo("-1175");

    }
}
