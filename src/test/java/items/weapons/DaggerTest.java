package items.weapons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DaggerTest {

    @Test
    public void testDoubleHanded() {
        Dagger target = new Dagger(8);

        assertEquals("Daggers, 8 piercing damage", target.toString());
    }
}
