package items.weapons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwordTest {
    
    private Sword target;
    
    @Test
    public void testSingleHanded() {
        target = new Sword(5, false);

        assertEquals("Sword, 5 slashing damage", target.toString());
    }

    @Test
    public void testDoubleHanded() {
        target = new Sword(8, true);

        assertEquals("Zweihänder, 8 slashing damage", target.toString());
    }
}
