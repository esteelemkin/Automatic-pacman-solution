package exe.ex3;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

class MapTest {

    @Test
    /**
     * This test checks that the init() method creates a map with the correct width and height.
     */
    public void testInit() {
        Map map = new Map(10, 10, 0);

        Assertions.assertEquals(10, map.getWidth());
        Assertions.assertEquals(10, map.getHeight());

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Assertions.assertEquals(0, map.getPixel(i, j));
            }
        }
    }
    @Test
    /**
     * This test checks that the init2() method creates a map with the correct width, height, and initial value.
     */

    public void testInit2() {
        Map map = new Map(5, 7, 10);

        Assertions.assertEquals(5, map.getWidth());
        Assertions.assertEquals(7, map.getHeight());

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                Assertions.assertEquals(10, map.getPixel(i, j));
            }
        }
    }

    @Test
    /**
     * This test checks that the setPixel() method sets the pixel at the specified coordinates to the specified value.
     */
    public void testSetPixel() {
        Map map = new Map(10, 10, 0);

        map.setPixel(5, 5, 1);

        Assertions.assertEquals(1, map.getPixel(5, 5));
    }
    @Test
    public void testSetPixel2() {
        Map map = new Map(10, 10, 0);

        map.setPixel(5, 5, 1);

        Assertions.assertEquals(1, map.getPixel(5, 5));
    }

    @Test
    /**
     * This test checks that the getPixel() method returns the value of the pixel at the specified coordinates.
     */
    public void testGetPixel() {
        Map map = new Map(10, 10, 0);

        map.setPixel(5, 5, 1);

        assertEquals(1, map.getPixel(5, 5));
    }

    @Test

    public void testGetPixel2() {
        Map map = new Map(10, 10, 0);

        map.setPixel(5, 5, 1);

        assertEquals(1, map.getPixel(5, 5));
    }


    @Test
    /**
     * This test checks that the shortestPath() method returns an array of Pixel2D objects that represent the shortest path between the two specified points.
     */
    public void testShortestPath() {


        Map map = new Map(10, 10, 0);

        map.setPixel(5, 5, 1);
        map.setPixel(0, 0, 2);

        Pixel2D[] path = map.shortestPath(new Index2D(0, 0), new Index2D(5, 5), 1);

        Assertions.assertNull(path);
    }
    @Test
    public void testShortestPath2() {
        Map map = new Map(10, 10, 0);


        map.setPixel(0, 0, 1);
        map.setPixel(1, 0, 1);
        map.setPixel(1, 1, 1);
        map.setPixel(1, 2, 1);
        map.setPixel(1, 3, 1);
        map.setPixel(2, 3, 1);
        map.setPixel(3, 3, 1);
        map.setPixel(3, 4, 1);
        map.setPixel(4, 4, 1);
        map.setPixel(5, 4, 1);
        map.setPixel(5, 5, 1);

        Pixel2D[] path = map.shortestPath(new Index2D(0, 0), new Index2D(5, 5), 0);

        Assertions.assertEquals(new Index2D(0, 0), path[0]);
        Assertions.assertEquals(new Index2D(1, 0), path[1]);
        Assertions.assertEquals(new Index2D(1, 1), path[2]);
        Assertions.assertEquals(new Index2D(1, 2), path[3]);
        Assertions.assertEquals(new Index2D(1, 3), path[4]);
        Assertions.assertEquals(new Index2D(2, 3), path[5]);
        Assertions.assertEquals(new Index2D(3, 3), path[6]);
        Assertions.assertEquals(new Index2D(3, 4), path[7]);
        Assertions.assertEquals(new Index2D(4, 4), path[8]);
        Assertions.assertEquals(new Index2D(5, 4), path[9]);
        Assertions.assertEquals(new Index2D(5, 5), path[10]);

    }


    @Test
/**
 * This test checks that the getWidth() method returns the correct width of the map.
 */
    public void testGetWidth() {
        Map map = new Map(10, 10, 0);
        Assertions.assertEquals(10, map.getWidth());
    }

    @Test
/**
 * This test checks that the getHeight() method returns the correct height of the map.
 */
    public void testGetHeight() {
        Map map = new Map(10, 10, 0);
        Assertions.assertEquals(10, map.getHeight());
    }

    @Test
/**
 * This test checks that the getMap() method returns the correct map representation.
 */
    public void testGetMap() {
        Map map = new Map(10, 10, 0);
        int[][] expectedMap = new int[10][10];
        Assertions.assertArrayEquals(expectedMap, map.getMap());
    }


    @Test
    /**
     * checks that the fill functions fills the right pixels
     */
    public void testFill(){
        int[][] pixels = {
            {0, 0, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 1, 0},
            {0, 1, 0, 1, 1, 0},
            {0, 1, 0, 1, 0, 0},
            {0, 1, 1, 1, 0, 0}
        };

        Map map = new Map(pixels);
        map.fill(new Index2D(2,3), 2);

        Assertions.assertEquals(map.getPixel(1, 2), 2);
        Assertions.assertEquals(map.getPixel(1, 3), 2);
        Assertions.assertEquals(map.getPixel(2, 2), 2);
        Assertions.assertEquals(map.getPixel(2, 3), 2);
        Assertions.assertEquals(map.getPixel(3, 2), 2);
        Assertions.assertEquals(map.getPixel(4, 2), 2);
    }

    @Test
    /**
     * cheks that fill function dosent cross the border and fills the right pixels
     */
    public void testFill2(){
        int[][] pixels = {
            {0, 0, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 2, 0},
            {0, 1, 0, 1, 1, 0},
            {0, 1, 0, 1, 0, 0},
            {0, 1, 1, 1, 0, 0}
        };

        Map map = new Map(pixels);
        map.fill(new Index2D(2,3), 2);

        Assertions.assertEquals(map.getPixel(1, 2), 2);
        Assertions.assertEquals(map.getPixel(1, 3), 2);
        Assertions.assertEquals(map.getPixel(2, 2), 2);
        Assertions.assertEquals(map.getPixel(2, 3), 2);
        Assertions.assertEquals(map.getPixel(3, 2), 2);
        Assertions.assertEquals(map.getPixel(4, 2), 2);

        Assertions.assertEquals(map.getPixel(2, 5), 0);
    }

    @Test
    /**
     * checks that the function compuets the correct distance for a cyclic map
     */
    public void testAllDistances(){
        int[][] pixels = {
            {0, 0, 1, 1, 2, 0},
            {0, 1, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0},
            {0, 1, 0, 1, 1, 0},
            {0, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0}
        };

        int[][] allDistancesExpected = {
            {0, 1, -1, -1, 16, 15},
            {1, -1, 17, 16, 15, 14},
            {2, -1, -1, 17, -1, 13},
            {3, -1, -1, -1, -1, 12},
            {4, -1, -1, -1, 10, 11},
            {5, 6, 7, 8, 9, 10}
        };

        Map map = new Map(pixels);
        map.setCyclic(false);
        int[][] mapAllDist = map.allDistance(new Index2D(0,0) , 1).getMap();

        for (int i = 0; i < map.getHeight(); i++) {
            for(int j = 0; j < map.getWidth(); j++) {
                Assertions.assertEquals(mapAllDist[i][j], allDistancesExpected[i][j]);
            }
        }
    }


    @Test
    /**
     * checking that the alldistance function compuets the right distances for a non cyclic map
     */
    public void testAllDistances2(){
        int[][] pixels = {
                {0, 0, 1, 1, 2, 0},
                {0, 1, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 0},
                {0, 1, 0, 1, 1, 0},
                {0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        int[][] allDistancesExpected = {
                {0, 1, -1, -1, 2, 1},
                {1, -1, 5, 4, 3, 2},
                {2, -1, -1, 5, -1, 3},
                {3, -1, -1, -1, -1, 4},
                {2, -1, -1, -1, 4, 3},
                {1, 2, 3, 4, 3, 2}
        };

        Map map = new Map(pixels);
        int[][] mapAllDist = map.allDistance(new Index2D(0,0) , 1).getMap();

        for (int i = 0; i < map.getHeight(); i++) {
            for(int j = 0; j < map.getWidth(); j++) {
                Assertions.assertEquals(mapAllDist[i][j], allDistancesExpected[i][j]);
            }
        }
    }
}
