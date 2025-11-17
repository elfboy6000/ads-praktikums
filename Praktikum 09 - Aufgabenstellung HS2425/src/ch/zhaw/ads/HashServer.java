package ch.zhaw.ads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HashServer implements CommandExecutor {
    private final static int STARTNR = 0;
    private final static int NAME = 1;
    private final static int TIME = 2;

    private final Map<MyCompetitor, MyCompetitor> data = new MyHashtable<>(4000);

    private long parseTime(String s) throws Exception {
        DateFormat sdf = new SimpleDateFormat("HH:mm:ss.S");
        Date date = sdf.parse(s);
        return date.getTime();
    }

    public void load(Map<MyCompetitor, MyCompetitor> data, String list) {
        String[] lines = list.split("\n");
        for (String line : lines) {
            String[] items = line.split(";");
            MyCompetitor c = new MyCompetitor(Integer.parseInt(items[STARTNR]), // startNr
                    items[NAME], // name
                    items[TIME]); // time
            data.put(c, c);
        }
    }

    public String execute(String arg) {
        if (arg.toUpperCase().startsWith("GET")) {
            String[] items = arg.substring(3).trim().split(";");
            MyCompetitor key = new MyCompetitor(Integer.parseInt(items[1]), items[0], "00:00:00.0");
            MyCompetitor o = data.get(key);
            if (o != null) {
                return items[0] + " " + items[1] + " -> " + o + "\n";
            }
            return "not found\n";
        }
        load(data, arg);
        return "" + data.size() + " loaded\n";
    }
}
