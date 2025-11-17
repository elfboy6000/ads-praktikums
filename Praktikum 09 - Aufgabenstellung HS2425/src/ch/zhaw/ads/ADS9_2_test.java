package ch.zhaw.ads;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */
public class ADS9_2_test {
    MyCompetitor c1 = new MyCompetitor(1, "Kiptum Daniel", "02:11:31");
    MyCompetitor c2 = new MyCompetitor(1, "Kiptum Daniel", "02:11:31");
    MyCompetitor c3 = new MyCompetitor(2, "Ancay Tarcis", "02:20:02");

    @Test
    public void testEquals() {
        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
    }

    @Test
    public void testCompareTo() {
        assertEquals(0, c1.compareTo(c2));
        assertNotEquals(0, c1.compareTo(c3));
    }

    @Test
    public void testHashcode() {
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1.hashCode(), c3.hashCode());
    }
}
