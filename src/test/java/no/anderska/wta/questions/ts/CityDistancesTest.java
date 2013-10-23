package no.anderska.wta.questions.ts;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CityDistancesTest {
    private CityDistances distances = new CityDistances();

    @Test
    public void shoudReadDistances() throws Exception {
        distances.add(1,2,5);
        assertThat(distances.get(1, 2)).isEqualTo(5);
    }

    @Test
    public void shoudBeSameReverse() throws Exception {
        distances.add(1,2,5);
        assertThat(distances.get(2, 1)).isEqualTo(5);
    }


}
