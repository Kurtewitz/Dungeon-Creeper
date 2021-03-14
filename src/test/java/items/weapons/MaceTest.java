package items.weapons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaceTest {

    private Mace target;

    @Test
    public void testSingleHanded() {
        target = new Mace(8, false);

        assertEquals("Mace, 8 blunt damage", target.toString());
    }

    @Test
    public void testDoubleHanded() {
        target = new Mace(17, true);

        assertEquals("War hammer, 17 blunt damage", target.toString());
    }
}
