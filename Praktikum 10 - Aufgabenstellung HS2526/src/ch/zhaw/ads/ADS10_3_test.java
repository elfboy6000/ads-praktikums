package ch.zhaw.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */
public class ADS10_3_test {
    @BeforeEach
    void init() {
        FuzzySearchServer.names.clear();
        FuzzySearchServer.trigrams.clear();
        String rangliste = "Mueller Stefan;02:31:14\n" +
                "Marti Adrian;02:30:09\n" +
                "Kiptum Daniel;02:11:31\n" +
                "Ancay Tarcis;02:20:02\n" +
                "Kreibuhl Christian;02:21:47\n" +
                "Ott Michael;02:33:48\n" +
                "Menzi Christoph;02:27:26\n" +
                "Oliver Ruben;02:32:12\n" +
                "Elmer Beat;02:33:53\n" +
                "Kuehni Martin;02:33:36\n";
        FuzzySearchServer.loadNames(rangliste);
    }

    @Test
    public void testLoadNames() {
        assertEquals(10, FuzzySearchServer.names.size(), "Länge von 'names' Liste");
        assertEquals("Mueller Stefan", FuzzySearchServer.names.get(0), "Erster Name");
    }

    @Test
    public void testTrigramForName() {
        List<String> trigList = FuzzySearchServer.trigramForName("Heinz");
        assertEquals(5, trigList.size(), "Länge von Trigram Liste");
        String[] good = {" he", "hei", "ein", "inz", "nz "};
        for (int i = 0; i < good.length; i++) {
            assertEquals(good[i], trigList.get(i), "trigram [" + i + "]");
        }
    }

    @Test
    public void testAddToTrigrams() {
        FuzzySearchServer.addToTrigrams(0, "mue");
        FuzzySearchServer.addToTrigrams(0, "uel");
        FuzzySearchServer.addToTrigrams(1, "mar");
        assertEquals(3, FuzzySearchServer.trigrams.size(), "Länge von 'trigram'");
        assertEquals(0, FuzzySearchServer.trigrams.get("mue").get(0), "mue");
        assertEquals(0, FuzzySearchServer.trigrams.get("uel").get(0), "uel");
        assertEquals(1, FuzzySearchServer.trigrams.get("mar").get(0), "mar");
    }

    @Test
    public void testFind() {
        FuzzySearchServer.constructTrigramIndex(FuzzySearchServer.names);
        assertEquals("Kiptum Daniel", FuzzySearchServer.find("Kiptum Daniel", 80));
        assertEquals("Kiptum Daniel", FuzzySearchServer.find("Daniel Kiptum", 80));
        assertEquals("Kiptum Daniel", FuzzySearchServer.find("Kip Dan", 30));
        assertEquals("Kiptum Daniel", FuzzySearchServer.find("Dan Kip", 30));
    }
}
