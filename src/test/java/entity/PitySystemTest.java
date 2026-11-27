package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PitySystemTest {

    private final PitySystem genshinCharacter = new PitySystem(0.006f, 74, 0.06f, 90);

    @Test
    void rateTest() {
        assertEquals(0.006f, genshinCharacter.getRateAt(73), 1e-6);
        assertEquals(0.066f, genshinCharacter.getRateAt(74), 1e-6);
        assertEquals(1f, genshinCharacter.getRateAt(90), 1e-6);
    }

    @Test
    void probabilityBeforeSoftPityTest() {
        double expected = 1 - Math.pow(1 - 0.006, 10);
        assertEquals(expected, genshinCharacter.probabilityWithin(0, 10), 1e-5);
    }

    // TODO: test more pity cases (rate capped at 100%, reaching hard pity, 0 pulls).
}
