package ch.zhaw.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ADS9_4_test {
    Map<Town, Town> hashmap;
    List<Town> towns;

    @BeforeEach
    public void setUp() {
        towns = List.of(
                new Town(5, "Bari", "BA"),
                new Town(8, "Bologna", "BO"),
                new Town(3, "Catania", "CA"),
                new Town(9, "Firenze", "FI"),
                new Town(0, "Genova", "GV"),
                new Town(12, "Milano", "MI"),
                new Town(7, "Napoli", "NA"),
                new Town(7, "Palermo", "PA"),
                new Town(7, "Roma", "RM"),
                new Town(5, "Torino", "TO")
        );

        hashmap = new MyHashtable<>(2);
    }

    @Test
    public void testAdd() {
        hashmap.clear();
        Town t0 = towns.get(0);
        hashmap.put(t0, t0);
        Town t1 = hashmap.get(t0);
        assertEquals(t0, t1);
    }

    @Test
    public void testAdd2() {
        hashmap.clear();
        Town t0 = towns.get(0);
        Town t1 = towns.get(1);
        hashmap.put(t0, t0);
        hashmap.put(t1, t1);
        Town t2 = hashmap.get(t0);
        assertEquals(t0, t2);
        t2 = hashmap.get(t1);
        assertEquals(t1, t2);
    }

    @Test
    public void testAdd3() {
        hashmap.clear();
        Town t0 = towns.get(0);
        hashmap.remove(t0);
        hashmap.put(t0, t0);
        hashmap.put(t0, t0);
        assertEquals(1, hashmap.size());
        Town t1 = hashmap.get(t0);
        assertEquals(t0, t1);
    }

    @Test
    public void testAdd4() {
        hashmap.clear();
        Town t0 = towns.get(0);
        hashmap.put(t0, t0);
        hashmap.put(t0, t0);
        assertEquals(1, hashmap.size());
    }

    @Test
    public void testSize() {
        hashmap.clear();
        assertEquals(0, hashmap.size());
        testAdd2();
        assertEquals(2, hashmap.size());
    }

    @Test
    public void testRemove() {
        hashmap.clear();
        Town t0 = towns.get(0);
        Town t1 = towns.get(1);
        hashmap.put(t0, t0);
        hashmap.remove(t0);
        assertEquals(0, hashmap.size());
        hashmap.put(t0, t0);
        hashmap.remove(t1);
        assertEquals(1, hashmap.size());
        hashmap.remove(t0);
        assertEquals(0, hashmap.size());
    }

    @Test
    public void testMixed() {
        hashmap.clear();
        Map<Town, Town> hashmap2 = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            Town c = towns.get((int)(Math.random() * towns.size()));
            int op = (int)(Math.random() * 2);
            switch (op) {
                case 0: hashmap.put(c, c); hashmap2.put(c, c); break;
                case 1: hashmap.remove(c); hashmap2.remove(c); break;
            }
        }
        assertEquals(hashmap2.size(), hashmap.size());
        for (Town t : towns) {
            Town c1 = hashmap.get(t);
            Town c2 = hashmap2.get(t);
            assertEquals(c1, c2);
        }
    }
}
