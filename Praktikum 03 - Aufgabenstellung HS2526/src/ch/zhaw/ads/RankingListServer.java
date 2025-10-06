package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.List;

public class RankingListServer implements CommandExecutor {
    public List<Competitor> createList(String rankingText) {
        List<Competitor> competitorList = new LinkedList<>();
        String[] lines = rankingText.split("\n");
        for (String line : lines) {
            String name = line.split(";")[0];
            String time = line.split(";")[1];
            competitorList.add(new Competitor(0, name,  time));
        }
        return competitorList;
    }

    public String createSortedText(List<Competitor> competitorList) {
        competitorList.sort(Competitor::compareTo);
        int rank = 1;
        StringBuilder sb = new StringBuilder();
        for (Competitor c : competitorList) {
            c.setRank(rank++);
            sb.append(c).append("\n");
        }
        return sb.toString();
    }


    public String createNameList(List<Competitor> competitorList) {
        competitorList.sort((c1, c2) -> {
            int nameComp = c1.getName().compareTo(c2.getName());
            if (nameComp == 0) {
                return c1.compareTo(c2);
            } else {
                return nameComp;
            }});
        StringBuilder tmp = new StringBuilder();
        for (Competitor c : competitorList) {
            tmp.append(0).append(" ").append(c.getName()).append(" ").append(c.getTime()).append("\n");
        };
        return tmp.toString();
    }

    public String execute(String rankingList) {
        List<Competitor> competitorList = createList(rankingList);
        return "Rangliste\n" + createSortedText(competitorList);
    }
}