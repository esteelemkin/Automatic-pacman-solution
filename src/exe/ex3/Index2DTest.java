package exe.ex3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Index2DTest {

    @Test
    /**
     * This test checks that the distance2D() method calculates the distance between two indexes correctly.
     */
    void testDistance2D() {
        Index2D index2D1 = new Index2D(1, 2);
        Index2D index2D2 = new Index2D(3, 4);
        assertEquals(5.0, index2D1.distance2D(index2D2));
    }

    @Test
    /**
     * This test checks that the toString() method returns a string representation of the index correctly.
     */
    void testToString() {
        Index2D index2D = new Index2D(1, 2);
        assertEquals("1,2", index2D.toString());
    }

    @Test
    /**
     * This test checks that the equals() method compares two indexes for equality correctly.
     */
    void testEquals() {
        Index2D index2D1 = new Index2D(1, 2);
        Index2D index2D2 = new Index2D(1, 2);
        assertTrue(index2D1.equals(index2D2));

        Index2D index2D3 = new Index2D(3, 4);
        assertFalse(index2D1.equals(index2D3));
    }
}