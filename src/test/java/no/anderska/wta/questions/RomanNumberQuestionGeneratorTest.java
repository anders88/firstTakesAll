package no.anderska.wta.questions;

import no.anderska.wta.questions.RomanNumberSolver;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RomanNumberQuestionGeneratorTest {

    private final RomanNumberSolver solver = new RomanNumberSolver();

    @Test
    public void oneIsI() throws Exception {
        assertThat(solver.romanNumber(1)).isEqualTo("I");
    }

    @Test
    public void twoIsII() throws Exception {
        assertThat(solver.romanNumber(2)).isEqualTo("II");
    }

    @Test
    public void threeIsIII() throws Exception {
        assertThat(solver.romanNumber(3)).isEqualTo("III");
    }

    @Test
    public void fourIsIV() throws Exception {
        assertThat(solver.romanNumber(4)).isEqualTo("IV");
    }

    @Test
    public void fiveIsV() throws Exception {
        assertThat(solver.romanNumber(5)).isEqualTo("V");
    }

    @Test
    public void shouldMatch() throws Exception {
        assertThat(solver.romanNumber(9)).isEqualTo("IX");
        assertThat(solver.romanNumber(10)).isEqualTo("X");
        assertThat(solver.romanNumber(30)).isEqualTo("XXX");
        assertThat(solver.romanNumber(40)).isEqualTo("XL");
        assertThat(solver.romanNumber(42)).isEqualTo("XLII");
        assertThat(solver.romanNumber(50)).isEqualTo("L");
        assertThat(solver.romanNumber(56)).isEqualTo("LVI");
        assertThat(solver.romanNumber(99)).isEqualTo("XCIX");
        assertThat(solver.romanNumber(200)).isEqualTo("CC");
        assertThat(solver.romanNumber(400)).isEqualTo("CD");
        assertThat(solver.romanNumber(500)).isEqualTo("D");
        assertThat(solver.romanNumber(744)).isEqualTo("DCCXLIV");
        assertThat(solver.romanNumber(999)).isEqualTo("CMXCIX");
    }

}
