import java.io.PrintWriter;
import java.util.*;
import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public Main() {
    }

    static LinkedHashMap<People, Double> sortHashMapByValues(
            HashMap<People, Double> passedMap) {
        List<People> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Double> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        //Collections.sort(mapKeys);

        LinkedHashMap<People, Double> sortedMap =
                new LinkedHashMap<>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<People> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                People key = keyIt.next();
                Double comp1 = passedMap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    static LinkedHashMap<Player, Double> sortHashMapByPlayers(
            HashMap<Player, Double> passedMap) {
        List<Player> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Double> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        //Collections.sort(mapKeys);

        LinkedHashMap<Player, Double> sortedMap =
                new LinkedHashMap<>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<Player> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Player key = keyIt.next();
                Double comp1 = passedMap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    static LinkedHashMap<Player, Integer> sortHashMapByInts(
            HashMap<Player, Integer> passedMap) {
        List<Player> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        //Collections.sort(mapKeys);

        LinkedHashMap<Player, Integer> sortedMap =
                new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<Player> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Player key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    static int findIdx(String info, Elements tr) {
        for (int x = 0; x < tr.size(); x++) {
            String col_title = tr.get(x).text();
            if (col_title.equals(info)) {
                return x - 1;
            }
        }
        return -1;
    }

    static int getLineToSkip(Elements trows) {
        for (int x = 0; x < trows.size(); x++) {
            if (trows.get(x).hasClass("thead")) {
                return x;
            }
        }
        return -1;
    }

    static double getPlayerWar(String href) throws IOException {
        Document playerDoc = Jsoup.connect(href).get();
        Elements divs = playerDoc.select("div");
        for (Element div : divs) {
            if (div.attr("class").equals("p1")) {
                Elements subDivs = div.select("div");
                Elements ps = subDivs.get(1).select("p");
                double war = Double.parseDouble(ps.get(0).text());
                return war;
            }
        }
        return 0.0;
    }

    static String getIsAllStar(String href) throws IOException {
        Document playerDoc = Jsoup.connect(href).get();
        Elements tables = playerDoc.select("table");
        for (Element table : tables) {
            if (table.id().equals("batting_standard")) {
                Elements b_rows = table.select("tr");

                Elements headers = b_rows.get(0).select("th");

                for (int i = 0; i < headers.size(); i++) {
                    if (headers.get(i).text().equals("Awards")) {
                        for (Element year_row : b_rows) {
                            Elements years = year_row.select("th");
                            if (years.get(0).text().equals("2018")) {
                                Elements columns = year_row.select("td");
                                if (!columns.get(1).text().contains("-min")) {
                                    String ret = columns.get(i - 1).text();
                                    return ret;
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    static String pitcherIsAllStar(String href) throws IOException {
        Document playerDoc = Jsoup.connect(href).get();
        Elements tables = playerDoc.select("table");
        for (Element table : tables) {
            if (table.id().equals("pitching_standard")) {
                Elements b_rows = table.select("tr");

                Elements headers = b_rows.get(0).select("th");

                for (int i = 0; i < headers.size(); i++) {
                    if (headers.get(i).text().equals("Awards")) {
                        for (Element year_row : b_rows) {
                            Elements years = year_row.select("th");
                            if (years.get(0).text().equals("2018")) {
                                Elements columns = year_row.select("td");
                                if (!columns.get(1).text().contains("-min")) {
                                    String ret = columns.get(i - 1).text();
                                    return ret;
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    static int getNumOfPagesF(int t_idx) throws IOException {
        String href = "https://www.fangraphs.com/leaders.aspx?pos=all&stats=fld&lg=all&qual=0&type=8&season=2018&" +
                "month=0&season1=2018&ind=0&team=" + t_idx + "&rost=0&age=0&filter=&players=0";
        Document teamDoc = Jsoup.connect(href).get();
        int numOfPages = 0;
        Elements divs = teamDoc.select("div");
        for (Element div : divs) {
            if (div.hasClass("rgWrap rgNumPart")) {
                Elements as = div.select("a");
                for (Element a : as) {
                    numOfPages = Integer.parseInt(a.text());
                }
            }
        }
        return numOfPages;
    }

  static int getNumOfPagesB(int t_idx) throws IOException {
    String href = "https://www.fangraphs.com/leaders.aspx?pos=all&stats=bat&lg=all&qual=0&type=8&season=2018&" +
            "month=0&season1=2018&ind=0&team=" + t_idx + "&rost=0&age=0&filter=&players=0";
    Document teamDoc = Jsoup.connect(href).get();
    int numOfPages = 0;
    Elements divs = teamDoc.select("div");
    for (Element div : divs) {
      if (div.hasClass("rgWrap rgNumPart")) {
        Elements as = div.select("a");
        for (Element a : as) {
          numOfPages = Integer.parseInt(a.text());
        }
      }
    }
    return numOfPages;
  }

  static int getNumOfPagesP(int t_idx) throws IOException {
    String href = "https://www.fangraphs.com/leaders.aspx?pos=all&stats=pit&lg=all&qual=0&type=8&season=2018&" +
            "month=0&season1=2018&ind=0&team=" + t_idx + "&rost=0&age=0&filter=&players=0";
    Document teamDoc = Jsoup.connect(href).get();
    int numOfPages = 0;
    Elements divs = teamDoc.select("div");
    for (Element div : divs) {
      if (div.hasClass("rgWrap rgNumPart")) {
        Elements as = div.select("a");
        for (Element a : as) {
          numOfPages = Integer.parseInt(a.text());
        }
      }
    }
    return numOfPages;
  }

    static double findFWarB(String name, int numOfPages, int t_idx) throws IOException {
        if (name.contains("(10-day dl)") || name.contains("(60-day dl)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 13);
            }
            else {
                name = name.substring(0, name.length() - 12);
            }
        }
        else if (name.contains("(7-day dl)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 12);
            }
            else {
                name = name.substring(0, name.length() - 11);
            }
        }
        else if (name.contains("(40-man)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 10);
            }
            else {
                name = name.substring(0, name.length() - 9);
            }
        }
        else if (name.contains("(BrvList)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 11);
            }
            else {
                name = name.substring(0, name.length() - 10);
            }
        }
        else if (name.contains("(DFA)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 7);
            }
            else {
                name = name.substring(0, name.length() - 6);
            }
        }
        else if (name.contains("*") || name.contains("#")) {
            name = name.substring(0, name.length() - 1);
        }

        if (name.contains("Carlos Sanchez")) {
            name = "Yolmer Sanchez";
        }
        else if (name.equals("Nick Castellanos")) {
            name = "Nicholas Castellanos";
        }
        else if (name.equals("Matthew Joyce")) {
            name = "Matt Joyce";
        }
        else if (name.equals("JT Riddle")) {
            name = "J.T. Riddle";
        }
        else if (name.equals("JB Shuck")) {
            name = "J.B. Shuck";
        }
        else if (name.equals("Phil Ervin")) {
            name = "Phillip Ervin";
        }
        else if (name.equals("Michael Taylor")) {
            name = "Michael A. Taylor";
        }
        else if (name.equals("Lourdes Gurriel Jr.")) {
            name = "Lourdes Gurriel";
        }
        int x = 1;
        while (x <= numOfPages) {
          String f_href = "https://www.fangraphs.com/leaders.aspx?pos=all&stats=bat&lg=all&qual=0&type=8&season=2018&" +
                  "month=0&season1=2018&ind=0&team=" + t_idx + "&rost=0&age=0&filter=&players=0&page=" + x + "_30";
          Document teamDoc = Jsoup.connect(f_href).get();
          Elements tables = teamDoc.select("table");
          for (Element table : tables) {
            if (table.id().equals("LeaderBoard1_dg1_ctl00")) {
              Elements bodies = table.select("tbody");
              Elements rows = bodies.get(0).select("tr");
              for (Element row : rows) {
                Elements cells = row.select("td");
                if (cells.get(1).text().contains(name)) {
                  String fwar = cells.get(cells.size() - 1).text();
                  if (name.equals("Shohei Otani")) {
                    int new_numOfPages = getNumOfPagesP(t_idx);
                    return Double.parseDouble(fwar) + findFWarP(name, new_numOfPages, t_idx);
                  } else {
                    return Double.parseDouble(fwar);
                  }
                }
              }
            }
          }
          x++;
        }
        System.out.println(name + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return 0.0;
    }

    static int findDRS(String name, int numOfPages, int t_idx) throws IOException {
        int drs = 0;
        if (name.contains("(10-day dl)") || name.contains("(60-day dl)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 13);
            }
            else {
                name = name.substring(0, name.length() - 12);
            }
        }
        else if (name.contains("(7-day dl)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 12);
            }
            else {
                name = name.substring(0, name.length() - 11);
            }
        }
        else if (name.contains("(40-man)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 10);
            }
            else {
                name = name.substring(0, name.length() - 9);
            }
        }
        else if (name.contains("(BrvList)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 11);
            }
            else {
                name = name.substring(0, name.length() - 10);
            }
        }
        else if (name.contains("(DFA)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 7);
            }
            else {
                name = name.substring(0, name.length() - 6);
            }
        }
        else if (name.contains("*") || name.contains("#")) {
            name = name.substring(0, name.length() - 1);
        }

        if (name.contains("Carlos Sanchez")) {
            name = "Yolmer Sanchez";
        }
        else if (name.equals("Nick Castellanos")) {
            name = "Nicholas Castellanos";
        }
        else if (name.equals("Matthew Joyce")) {
            name = "Matt Joyce";
        }
        else if (name.equals("JT Riddle")) {
            name = "J.T. Riddle";
        }
        else if (name.equals("JB Shuck")) {
            name = "J.B. Shuck";
        }
        else if (name.equals("Phil Ervin")) {
            name = "Phillip Ervin";
        }
        else if (name.equals("Michael Taylor")) {
            name = "Michael A. Taylor";
        }
        else if (name.equals("Lourdes Gurriel Jr.")) {
            name = "Lourdes Gurriel";
        }
        int x = 1;
        while (x <= numOfPages) {
            String f_href = "https://www.fangraphs.com/leaders.aspx?pos=all&stats=fld&lg=all&qual=0&type=8&season=2018&" +
                    "month=0&season1=2018&ind=0&team=" + t_idx + "&rost=0&age=0&filter=&players=0&page=" + x + "_30";
            Document teamDoc = Jsoup.connect(f_href).get();
            Elements tables = teamDoc.select("table");
            for (Element table : tables) {
                if (table.id().equals("LeaderBoard1_dg1_ctl00")) {
                    Elements bodies = table.select("tbody");
                    Elements rows = bodies.get(0).select("tr");
                    for (Element row : rows) {
                        Elements cells = row.select("td");
                        if (cells.get(1).text().contains(name)) {
                            String s_drs = cells.get(8).text();
                            drs += Integer.parseInt(s_drs);
                        }
                    }
                }
            }
            x++;
        }
        return drs;
    }

    static double findFWarP(String name, int numOfPages, int t_idx) throws IOException {
        if (name.contains("(10-day dl)") || name.contains("(60-day dl)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 13);
            }
            else {
                name = name.substring(0, name.length() - 12);
            }
        }
        if (name.contains("(7-day dl)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 12);
            }
            else {
                name = name.substring(0, name.length() - 11);
            }
        }
        else if (name.contains("(40-man)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 10);
            }
            else {
                name = name.substring(0, name.length() - 9);
            }
        }
        else if (name.contains("(BrvList)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 11);
            }
            else {
                name = name.substring(0, name.length() - 10);
            }
        }
        else if (name.contains("(DFA)")) {
            if (name.contains("*") || name.contains("#")) {
                name = name.substring(0, name.length() - 7);
            }
            else {
                name = name.substring(0, name.length() - 6);
            }
        }
        else if (name.contains("*") || name.contains("#")) {
            name = name.substring(0, name.length() - 1);
        }

        if (name.equals("Mike Wright Jr.")) {
            name = "Mike Wright";
        }
        else if (name.equals("Seunghwan Oh")) {
            name = "Seung Hwan Oh";
        }
        else if (name.equals("Chase Bradford")) {
            name = "Chasen Bradford";
        }
        else if (name.equals("JT Chargois")) {
            name = "J.T. Chargois";
        }
      int x = 1;
      while (x <= numOfPages) {
        String f_href = "https://www.fangraphs.com/leaders.aspx?pos=all&stats=pit&lg=all&qual=0&type=8&season=2018&" +
                "month=0&season1=2018&ind=0&team=" + t_idx + "&rost=0&age=0&filter=&players=0&page=" + x + "_30";
        Document teamDoc = Jsoup.connect(f_href).get();
        Elements tables = teamDoc.select("table");
        for (Element table : tables) {
          if (table.id().equals("LeaderBoard1_dg1_ctl00")) {
            Elements bodies = table.select("tbody");
            Elements rows = bodies.get(0).select("tr");
            for (Element row : rows) {
              Elements cells = row.select("td");
              if (cells.get(1).text().contains(name)) {
                String fwar = cells.get(cells.size() - 1).text();
                return Double.parseDouble(fwar);
              }
            }
          }
        }
        x++;
      }
      System.out.println(name + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      return 0.0;
    }

    static String GetTeamRecord(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements ps = doc.select("p");
        for (Element p : ps) {
            if (p.text().contains("Record:")) {
                String hello = p.text().substring(0, p.text().length() - 23);
                hello = hello.replace("_", " ");
                return hello;
//                String[] sArr = p.text().split(" ");
//                String wl = sArr[1].substring(0, sArr[1].length() - 1);
//                return wl;
            }
        }
        return "";
    }

    static int GetTeamGames(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements ps = doc.select("p");
        for (Element p : ps) {
            if (p.text().contains("Record:")) {
                String[] sArr = p.text().split(" ");
                String wl = sArr[1].substring(0, sArr[1].length() - 1);
                String[] wAndl = wl.split("-");
                return Integer.parseInt(wAndl[0]) + Integer.parseInt(wAndl[1]);
            }
        }
        return 0;
    }

    static Player updateBatter(Player batter) {
        String name = batter.getOriginalName();
        if (name.contains("(10-day dl)") || name.contains("(60-day dl)")) {
            if (name.contains("*") || name.contains("#")) {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 13), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
            else {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 12), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
        }
        else if (name.contains("(7-day dl)")) {
            if (name.contains("*") || name.contains("#")) {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 12), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
            else {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 11), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
        }
        else if (name.contains("(40-man)")) {
            if (name.contains("*") || name.contains("#")) {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 10), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
            else {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 9), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
        }
        else if (name.contains("(BrvList)")) {
            if (name.contains("*") || name.contains("#")) {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 11), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
            else {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 10), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
        }
        else if (name.contains("(DFA)")) {
            if (name.contains("*") || name.contains("#")) {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 7), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
            else {
                return new Player(batter.getHref(),
                        name.substring(0, name.length() - 6), batter.getTeam(), batter.getPos(), batter.getG(),
                        batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                        batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                        batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                        batter.getAwards(), batter.getDrs());
            }
        }
        else if (name.contains("*") || name.contains("#")) {
            return new Player(batter.getHref(),
                    name.substring(0, name.length() - 1), batter.getTeam(), batter.getPos(), batter.getG(),
                    batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                    batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                    batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                    batter.getAwards(), batter.getDrs());
        }
        else {
            return new Player(batter.getHref(),
                    name, batter.getTeam(), batter.getPos(), batter.getG(),
                    batter.getWar(), batter.getFwar(), batter.getH(), batter.getAb(), batter.getR(), batter.getHr(),
                    batter.getRbi(), batter.getSb(), batter.getBb(), batter.getTb(), batter.getPa(),
                    batter.getHbp(), batter.getAvg(), batter.getObp(), batter.getSlg(),
                    batter.getAwards(), batter.getDrs());
        }
    }

    static Pitcher updatePitcher(Pitcher pitcher) {
      String name = pitcher.getOriginalName();
      if (name.contains("(10-day dl)") || name.contains("(60-day dl)")) {
        if (name.contains("*") || name.contains("#")) {
          return new Pitcher(pitcher.getHref(),
                  name.substring(0, name.length() - 13), pitcher.getTeam(), pitcher.getPos(),
                  pitcher.getG(), pitcher.getGS(), pitcher.getWar(), pitcher.getFwar(),
                  pitcher.getERA(), pitcher.getIP(), pitcher.getBB(), pitcher.getK(),
                  pitcher.getSV(), pitcher.getAwards(), pitcher.getFIP(), pitcher.getERAPlus());
        }
        else {
          return new Pitcher(pitcher.getHref(),
                  name.substring(0, name.length() - 12), pitcher.getTeam(), pitcher.getPos(),
                  pitcher.getG(), pitcher.getGS(), pitcher.getWar(), pitcher.getFwar(),
                  pitcher.getERA(), pitcher.getIP(), pitcher.getBB(), pitcher.getK(),
                  pitcher.getSV(), pitcher.getAwards(), pitcher.getFIP(), pitcher.getERAPlus());
        }
      }
      else if (name.contains("(40-man)")) {
        if (name.contains("*") || name.contains("#")) {
          return new Pitcher(pitcher.getHref(),
                  name.substring(0, name.length() - 10), pitcher.getTeam(), pitcher.getPos(),
                  pitcher.getG(), pitcher.getGS(), pitcher.getWar(), pitcher.getFwar(),
                  pitcher.getERA(), pitcher.getIP(), pitcher.getBB(), pitcher.getK(),
                  pitcher.getSV(), pitcher.getAwards(), pitcher.getFIP(), pitcher.getERAPlus());
        }
        else {
          return new Pitcher(pitcher.getHref(),
                  name.substring(0, name.length() - 9), pitcher.getTeam(), pitcher.getPos(),
                  pitcher.getG(), pitcher.getGS(), pitcher.getWar(), pitcher.getFwar(),
                  pitcher.getERA(), pitcher.getIP(), pitcher.getBB(), pitcher.getK(),
                  pitcher.getSV(), pitcher.getAwards(), pitcher.getFIP(), pitcher.getERAPlus());
        }
      }
      else if (name.contains("(BrvList)")) {
        if (name.contains("*") || name.contains("#")) {
          return new Pitcher(pitcher.getHref(),
                  name.substring(0, name.length() - 11), pitcher.getTeam(), pitcher.getPos(),
                  pitcher.getG(), pitcher.getGS(), pitcher.getWar(), pitcher.getFwar(),
                  pitcher.getERA(), pitcher.getIP(), pitcher.getBB(), pitcher.getK(),
                  pitcher.getSV(), pitcher.getAwards(), pitcher.getFIP(), pitcher.getERAPlus());
        }
        else {
          return new Pitcher(pitcher.getHref(),
                  name.substring(0, name.length() - 10), pitcher.getTeam(), pitcher.getPos(),
                  pitcher.getG(), pitcher.getGS(), pitcher.getWar(), pitcher.getFwar(),
                  pitcher.getERA(), pitcher.getIP(), pitcher.getBB(), pitcher.getK(),
                  pitcher.getSV(), pitcher.getAwards(), pitcher.getFIP(), pitcher.getERAPlus());
        }
      }
      else if (name.contains("*") || name.contains("#")) {
        return new Pitcher(pitcher.getHref(),
                name.substring(0, name.length() - 1), pitcher.getTeam(), pitcher.getPos(),
                pitcher.getG(), pitcher.getGS(), pitcher.getWar(), pitcher.getFwar(),
                pitcher.getERA(), pitcher.getIP(), pitcher.getBB(), pitcher.getK(),
                pitcher.getSV(), pitcher.getAwards(), pitcher.getFIP(), pitcher.getERAPlus());
      }
      else {
        return new Pitcher(pitcher.getHref(), name, pitcher.getTeam(), pitcher.getPos(), pitcher.getG(),
                pitcher.getGS(), pitcher.getWar(), pitcher.getFwar(), pitcher.getERA(),
                pitcher.getIP(), pitcher.getBB(), pitcher.getK(), pitcher.getSV(),
                pitcher.getAwards(), pitcher.getFIP(), pitcher.getERAPlus());
      }
    }

    static void tradedP(Pitcher p, String[] fTeams, ArrayList<Pitcher> retArr, Elements bold,
                        Elements cols, int g_idx, int gs_idx, int era_idx, int ip_idx, int bb_idx,
                        int k_idx, int sv_idx, int erap_idx, int fip_idx) throws IOException {
      int g, gs, k, sv, erap, bb;
      double era, fip, ip;
      String name = p.getName();
      String href = p.getHref();
      String team = p.getTeam();
      String pos = p.getPos();
      double war = p.getWar();
      double fwar = p.getFwar();
      String awards = p.getAwards();
      String originalName = p.getName();
      if (bold.size() == 0 && !(name.contains("DFA") ||
              name.contains("-day dl)") || name.contains("BrvList") || name.contains("40-man"))) {
        Document doc = Jsoup.connect(href).get();
        Elements tables = doc.select("table");
        name = name + " (";
        String p_team = team;
        for (Element table : tables) {
          if (table.id().equals("pitching_standard")) {
            Elements p_rows = table.select("tr");
            Elements p_headers = p_rows.get(0).select("th");

            g_idx = findIdx("G", p_headers);
            gs_idx = findIdx("GS", p_headers);
            era_idx = findIdx("ERA", p_headers);
            ip_idx = findIdx("IP", p_headers);
            k_idx = findIdx("SO", p_headers);
            sv_idx = findIdx("SV", p_headers);
            erap_idx = findIdx("ERA+", p_headers);
            fip_idx = findIdx("FIP", p_headers);
            bb_idx = findIdx("BB", p_headers);

            for (int y = 1; y < p_rows.size(); y++) {
              Element p_row = p_rows.get(y);
              Elements p_cols = p_row.select("td");
              Elements year = p_row.select("th");
              if (year.get(0).text().equals("2018") && p_cols.get(1).text().equals("TOT")) {
                g = Integer.parseInt(p_cols.get(g_idx).text());
                gs = Integer.parseInt(p_cols.get(gs_idx).text());
                era = Double.parseDouble(p_cols.get(era_idx).text());
                ip = Double.parseDouble(p_cols.get(ip_idx).text());
                k = Integer.parseInt(p_cols.get(k_idx).text());
                sv = Integer.parseInt(p_cols.get(sv_idx).text());
                erap = Integer.parseInt(p_cols.get(erap_idx).text());
                fip = Double.parseDouble(p_cols.get(fip_idx).text());
                bb = Integer.parseInt(p_cols.get(bb_idx).text());
                int z = 1;

                while (!(p_rows.get(y + z).select("th").text().contains("Yr"))) {
                  p_row = p_rows.get(y + z);
                  p_cols = p_row.select("td");
                  if (!(team.equals(p_cols.get(1).text()))) {
                    String newTeam = p_cols.get(1).text();
                    int t_idx = java.util.Arrays.asList(fTeams).indexOf(newTeam);
                    t_idx = t_idx + 1;
                    int numOfPages = getNumOfPagesP(t_idx);
                    p.fwar += findFWarP(originalName, numOfPages, t_idx);
                    p_team += ", " + newTeam;
                  }
                  name = name + p_cols.get(1).text() + ", ";
                  z++;
                }
                name = name.substring(0, name.length() - 2) + ")";
                retArr.add(new Pitcher(href, name, p_team, pos, g, gs, war, fwar, era, ip, bb, k, sv,
                        awards, fip, erap, originalName));
                System.out.println(name);
              } else if (year.get(0).text().equals("2018") && p_cols.get(1).text().equals(team) &&
                      (p_rows.get(y - 1).select("th").get(0).equals("2017") || (y > 1 &&
                              p_rows.get(y - 1).select("td").get(1).text().contains("-min")))) {
                g = Integer.parseInt(p_cols.get(g_idx).text());
                gs = Integer.parseInt(p_cols.get(gs_idx).text());
                era = Double.parseDouble(p_cols.get(era_idx).text());
                ip = Double.parseDouble(p_cols.get(ip_idx).text());
                k = Integer.parseInt(p_cols.get(k_idx).text());
                sv = Integer.parseInt(p_cols.get(sv_idx).text());
                erap = Integer.parseInt(p_cols.get(erap_idx).text());
                fip = Double.parseDouble(p_cols.get(fip_idx).text());
                bb = Integer.parseInt(p_cols.get(bb_idx).text());
                name = name.substring(0, name.length() - 2);
                retArr.add(new Pitcher(href, name, team, pos, g, gs, war, fwar, era, ip, bb, k, sv,
                        awards, fip, erap, originalName));
                System.out.println(name);
              }
            }

          }
        }
      }
      else {
        g = Integer.parseInt(cols.get(g_idx).text());
        gs = Integer.parseInt(cols.get(gs_idx).text());
        era = Double.parseDouble(cols.get(era_idx).text());
        ip = Double.parseDouble(cols.get(ip_idx).text());
        erap = Integer.parseInt(cols.get(erap_idx).text());
        fip = Double.parseDouble(cols.get(fip_idx).text());
        bb = Integer.parseInt(cols.get(bb_idx).text());
        k = Integer.parseInt(cols.get(k_idx).text());
        sv = Integer.parseInt(cols.get(sv_idx).text());
        retArr.add(new Pitcher(href, name, team, pos, g, gs, war, fwar, era, ip, bb, k, sv, awards, fip, erap));
      }
    }

    static ArrayList<Player> batterStats(int numOfBatters, int numOfPagesF, int numOfPagesB, Elements rows, int g_idx, int h_idx, int ab_idx, int r_idx, int hr_idx,
                                         int rbi_idx, int sb_idx, int bb_idx, int tb_idx, int pa_idx, int hbp_idx,
                                         int name_idx, int avg_idx, int obp_idx, int slg_idx, String team, int pos_idx, int t_idx,
                                         Document bat_teamDoc1, Document bat_teamDoc2, String[] fTeams)
            throws IOException{
        ArrayList<Player> retArr = new ArrayList<>();
        for (int x = 1; x < numOfBatters + 1; x++) {
            Element row = rows.get(x);
            Elements cols = row.select("td");
            Element col = cols.get(name_idx);
            Elements a = col.select("a");
            String href = a.get(0).attr("href");
            href = "https://www.baseball-reference.com" + href;
            System.out.println(href);
            double war = getPlayerWar(href);
            String awards = getIsAllStar(href);
            String name = cols.get(name_idx).text();
            double fwar = findFWarB(name, numOfPagesB, t_idx);
            int drs = findDRS(name, numOfPagesF, t_idx);
            Element cell = cols.get(name_idx);
            String pos = cols.get(pos_idx).text();
            Elements bold = cell.select("strong");
            if (bold.size() == 0 && !(name.contains("DFA") ||
                    name.contains("-day dl)") || name.contains("BrvList") || name.contains("40-man"))) {
                Document doc = Jsoup.connect(href).get();
                Elements tables = doc.select("table");
                int g; int hits; int ab; int r; int hr; int rbi; int sb; int bb; int tb; int pa; int hbp; double avg;
                double obp; double slg;
                String originalName = name;
                name = name + " (";
                String p_team = team;
                for (Element table : tables) {
                    if (table.id().equals("batting_standard")) {
                        Elements p_rows = table.select("tr");
                        Elements p_headers = p_rows.get(0).select("th");

                        g_idx = findIdx("G", p_headers);
                        avg_idx = findIdx("BA", p_headers);
                        obp_idx = findIdx("OBP", p_headers);
                        slg_idx = findIdx("SLG", p_headers);
                        h_idx = findIdx("H", p_headers);
                        ab_idx = findIdx("AB", p_headers);
                        r_idx = findIdx("R", p_headers);
                        hr_idx = findIdx("HR", p_headers);
                        rbi_idx = findIdx("RBI", p_headers);
                        sb_idx = findIdx("SB", p_headers);
                        bb_idx = findIdx("BB", p_headers);
                        tb_idx = findIdx("TB", p_headers);
                        pa_idx = findIdx("PA", p_headers);
                        hbp_idx = findIdx("HBP", p_headers);

                        for (int y = 1; y < p_rows.size(); y++) {
                            Element p_row = p_rows.get(y);
                            Elements p_cols = p_row.select("td");
                            Elements year = p_row.select("th");

                            if (year.get(0).text().equals("2018") && p_cols.get(1).text().equals("TOT") &&
                                    p_cols.get(2).text().equals("MLB")) {
                                g = Integer.parseInt(p_cols.get(g_idx).text());
                                hits = Integer.parseInt(p_cols.get(h_idx).text());
                                ab = Integer.parseInt(p_cols.get(ab_idx).text());
                                r = Integer.parseInt(p_cols.get(r_idx).text());
                                hr = Integer.parseInt(p_cols.get(hr_idx).text());
                                rbi = Integer.parseInt(p_cols.get(rbi_idx).text());
                                sb = Integer.parseInt(p_cols.get(sb_idx).text());
                                bb = Integer.parseInt(p_cols.get(bb_idx).text());
                                tb = Integer.parseInt(p_cols.get(tb_idx).text());
                                pa = Integer.parseInt(p_cols.get(pa_idx).text());
                                hbp = Integer.parseInt(p_cols.get(hbp_idx).text());
                                avg = Double.parseDouble(p_cols.get(avg_idx).text());
                                obp = Double.parseDouble(p_cols.get(obp_idx).text());
                                slg = Double.parseDouble(p_cols.get(slg_idx).text());
                                int z = 1;

                                while (!(p_rows.get(y + z).select("th").text().contains("Yrs"))) {
                                    p_row = p_rows.get(y + z);
                                    p_cols = p_row.select("td");
                                    if (!(p_cols.get(1).text().equals("TOT"))) {
                                        if (!(team.equals(p_cols.get(1).text()))) {
                                            String newTeam = p_cols.get(1).text();
                                            int new_t_idx = java.util.Arrays.asList(fTeams).indexOf(newTeam);
                                            new_t_idx = new_t_idx + 1;
                                            int new_numOfPagesB = getNumOfPagesB(t_idx);
                                            fwar += findFWarB(originalName, new_numOfPagesB, new_t_idx);
                                            int new_numOfPages = getNumOfPagesF(new_t_idx);
                                            drs += findDRS(originalName, new_numOfPages, new_t_idx);
                                            p_team += ", " + p_cols.get(1).text();
                                        }
                                        name = name + p_cols.get(1).text() + ", ";

                                    }
                                    z++;
                                }
                                name = name.substring(0, name.length() - 2) + ")";
                                retArr.add(new Player(href, name, p_team, pos, g, war, fwar, hits, ab, r, hr, rbi, sb, bb, tb, pa, hbp, avg, obp, slg,
                                        awards, drs, originalName));
                                System.out.println(name);
                            }

                            else if (year.get(0).text().equals("2018") && p_cols.get(1).text().equals(team) &&
                                    (p_rows.get(y - 1).select("th").get(0).text().equals("2017") || (y > 1 &&
                                    p_rows.get(y - 1).select("td").get(1).text().contains("-min")))) {
                                g = Integer.parseInt(p_cols.get(g_idx).text());
                                hits = Integer.parseInt(p_cols.get(h_idx).text());
                                ab = Integer.parseInt(p_cols.get(ab_idx).text());
                                r = Integer.parseInt(p_cols.get(r_idx).text());
                                hr = Integer.parseInt(p_cols.get(hr_idx).text());
                                rbi = Integer.parseInt(p_cols.get(rbi_idx).text());
                                sb = Integer.parseInt(p_cols.get(sb_idx).text());
                                bb = Integer.parseInt(p_cols.get(bb_idx).text());
                                tb = Integer.parseInt(p_cols.get(tb_idx).text());
                                pa = Integer.parseInt(p_cols.get(pa_idx).text());
                                hbp = Integer.parseInt(p_cols.get(hbp_idx).text());
                                avg = Double.parseDouble(p_cols.get(avg_idx).text());
                                obp = Double.parseDouble(p_cols.get(obp_idx).text());
                                slg = Double.parseDouble(p_cols.get(slg_idx).text());
                                name = name.substring(0, name.length() - 2);
                                retArr.add(new Player(href, name, team, pos, g, war, fwar, hits, ab, r, hr, rbi, sb, bb, tb, pa, hbp, avg, obp, slg,
                                        awards, drs, originalName));
                                System.out.println(name);
                            }
                        }

                    }
                }

            }
            else {
                int g = Integer.parseInt(cols.get(g_idx).text());
                int hits = Integer.parseInt(cols.get(h_idx).text());
                int ab = Integer.parseInt(cols.get(ab_idx).text());
                int r = Integer.parseInt(cols.get(r_idx).text());
                int hr = Integer.parseInt(cols.get(hr_idx).text());
                int rbi = Integer.parseInt(cols.get(rbi_idx).text());
                int sb = Integer.parseInt(cols.get(sb_idx).text());
                int bb = Integer.parseInt(cols.get(bb_idx).text());
                int tb = Integer.parseInt(cols.get(tb_idx).text());
                int pa = Integer.parseInt(cols.get(pa_idx).text());
                int hbp = Integer.parseInt(cols.get(hbp_idx).text());
                double avg = Double.parseDouble(cols.get(avg_idx).text());
                double obp = Double.parseDouble(cols.get(obp_idx).text());
                double slg = Double.parseDouble(cols.get(slg_idx).text());
                retArr.add(new Player(href, name, team, pos, g, war, fwar, hits, ab, r, hr, rbi, sb, bb, tb, pa, hbp,
                        avg, obp, slg, awards, drs));
                //lastSeasonAvg, lastSeasonObp, lastSeasonSlg));
            }
        }
        for (int x = numOfBatters + 2; x < numOfBatters + 5; x++) {
            Element row = rows.get(x);
            Elements cols = row.select("td");
            Element col = cols.get(name_idx);
            Elements a = col.select("a");
            String href = a.get(0).attr("href");
            href = "https://www.baseball-reference.com" + href;
            System.out.println(href);
//            double lastSeasonAvg = getBatterLastSeasonAvg(href);
//            double lastSeasonObp = getBatterLastSeasonObp(href);
//            double lastSeasonSlg = getBatterLastSeasonSlg(href);
            double war = getPlayerWar(href);
            String awards = getIsAllStar(href);
            String name = cols.get(name_idx).text();
            String pos = cols.get(pos_idx).text();
            double fwar = findFWarB(name, numOfPagesB, t_idx);
            int drs = findDRS(name, numOfPagesF, t_idx);
            int g = Integer.parseInt(cols.get(g_idx).text());
            int hits = Integer.parseInt(cols.get(h_idx).text());
            int ab = Integer.parseInt(cols.get(ab_idx).text());
            int r = Integer.parseInt(cols.get(r_idx).text());
            int hr = Integer.parseInt(cols.get(hr_idx).text());
            int rbi = Integer.parseInt(cols.get(rbi_idx).text());
            int sb = Integer.parseInt(cols.get(sb_idx).text());
            int bb = Integer.parseInt(cols.get(bb_idx).text());
            int tb = Integer.parseInt(cols.get(tb_idx).text());
            int pa = Integer.parseInt(cols.get(pa_idx).text());
            int hbp = Integer.parseInt(cols.get(hbp_idx).text());
            double avg = Double.parseDouble(cols.get(avg_idx).text());
            double obp = Double.parseDouble(cols.get(obp_idx).text());
            double slg = Double.parseDouble(cols.get(slg_idx).text());
            retArr.add(new Player(href, name, team, pos, g, war, fwar, hits, ab, r, hr, rbi, sb, bb, tb, pa, hbp, avg, obp, slg,
                    awards, drs));
            //lastSeasonAvg, lastSeasonObp, lastSeasonSlg));
        }
        return retArr;
    }

    static ArrayList<Pitcher> pitcherStats(Elements rows, int name_idx, int g_idx, int gs_idx, int era_idx, int ip_idx, int bb_idx,
                                                 int k_idx, int sv_idx, int line_to_skip, String team, int pos_idx, int erap_idx,
                                           int fip_idx, String[] fTeams, int numOfPages, int t_idx)
            throws IOException {
        ArrayList<Pitcher> retArr = new ArrayList<>();
        for (int x = 1; x < line_to_skip; x++) {
            Element row = rows.get(x);
            Elements cols = row.select("td");
            Element col = cols.get(name_idx);
            Elements a = col.select("a");
            String href = a.get(0).attr("href");
            href = "https://www.baseball-reference.com" + href;
            String name = cols.get(name_idx).text();
            double fwar = findFWarP(name, numOfPages, t_idx);
            String awards = pitcherIsAllStar(href);
            double war = getPlayerWar(href);
            String pos = cols.get(pos_idx).text();
            Element cell = cols.get(name_idx);
            Elements bold = cell.select("strong");
            Pitcher tp = new Pitcher(href, name, team, pos, war, fwar, awards);
            tradedP(tp, fTeams, retArr, bold, cols, g_idx, gs_idx,
                    era_idx, ip_idx, bb_idx, k_idx, sv_idx, erap_idx, fip_idx);
        }
        for (int x = line_to_skip + 1; x < line_to_skip + 6; x++) {
            Element row = rows.get(x);
            Elements cols = row.select("td");
            Element col = cols.get(name_idx);
            Elements a = col.select("a");
            String href = a.get(0).attr("href");
            href = "https://www.baseball-reference.com" + href;
            String name = cols.get(name_idx).text();
            String pos = cols.get(pos_idx).text();
            String awards = pitcherIsAllStar(href);
            double war = getPlayerWar(href);
            double fwar = findFWarP(name, numOfPages, t_idx);
            Element cell = cols.get(name_idx);
            Elements bold = cell.select("strong");
            Pitcher tp = new Pitcher(href, name, team, pos, war, fwar, awards);
            tradedP(tp, fTeams, retArr, bold, cols, g_idx, gs_idx,
                  era_idx, ip_idx, bb_idx, k_idx, sv_idx, erap_idx, fip_idx);
        }
        return retArr;
    }

    static BattingTeam writeBatterTable(ArrayList<Player> batters, PrintWriter pw, String team, String href, String league) throws IOException {
        double teamWar = 0.0;
        double teamfWar = 0.0;
        int teamH = 0;
        int teamAB = 0;
        int teamR = 0;
        int teamHR = 0;
        int teamRBI = 0;
        int teamSB = 0;
        int teamTB = 0;
        int teamBB = 0;
        int teamPA = 0;
        int teamHBP = 0;
        int teamDRS = 0;
        int numOfPlayers = 0;
        int teamG = GetTeamGames(href);
        String record = GetTeamRecord(href);
        pw.println("<div class='media-item logo'>");
        pw.println("<a href=" + href + ">");
        pw.println("<img class='teamlogo' itemscope='image' " +
                "src='https://d2p3bygnnzw9w3.cloudfront.net/req/201806122/tlogo/br/" + team +
                "-2018.png' alt='Logo' >");
        pw.println("</a>");
        pw.println("</div>");
        pw.println("<h5>" + record + "</h5>");
        pw.println("<div>");
        pw.println("<TABLE BORDER><TR style=\"background-color: lightgray\"><TH>Pos<TH>Name<TH>bWAR<TH>fWAR<TH>G<TH>H/AB<TH>R" +
                "<TH>HR<TH>RBI<TH>SB<TH>BA<TH>OBP<TH>SLG<TH>OPS<TH>DRS<TH>Awards</TR>");
        for (Player batter : batters) {
            numOfPlayers += 1;
            String b_href = batter.getHref();
            String name = batter.getName();
            String pos = batter.getPos();
            double war = batter.getWar();
            double fwar = batter.getFwar();
            int g = batter.getG();
            int h = batter.getH();
            int ab = batter.getAb();
            int r = batter.getR();
            int hr = batter.getHr();
            int rbi = batter.getRbi();
            int sb = batter.getSb();
            int bb = batter.getBb();
            int tb = batter.getTb();
            int pa = batter.getPa();
            int hbp = batter.getHbp();
            double avg = batter.getAvg();
            double obp = batter.getObp();
            double slg = batter.getSlg();
            double ops = batter.getOps();
            int drs = batter.getDrs();
            String awards = batter.getAwards();
//            double ba_2017 = batter.getLastAvg();
//            double obp_2017 = batter.getLastObp();
//            double slg_2017 = batter.getLastSlg();
//            double ops_2017 = batter.getLastOps();
            if (league.equals("nl") && batter.getPos().equals("RF") && numOfPlayers == 8) {
                pw.format("<TR ALIGN=RIGHT><TD><strong>%s</strong><TD><a href=%s>%s</a><TD>%.1f<TD>%.1f<TD>%d<TD>%d/%d<TD>%d<TD>%d<TD>%d" +
                                "<TD>%d<TD>%.3f<TD>%.3f<TD>%.3f<TD>%.3f<TD>%d<TD>%s</TR>",/*<TD>%.3f<TD>%.3f<TD>%.3f<TD>%.3f",*/ pos, b_href,
                        name, war, fwar, g, h, ab, r, hr, rbi, sb, avg, obp, slg, ops, drs, awards);//, ba_2017, obp_2017, slg_2017, ops_2017);
                pw.println("<TR ALIGN=RIGHT style=\"background-color: lightgray\"><TD><strong>Pos</strong><TD>" +
                        "<strong>Name</strong><TD><strong>bWAR</strong><TD><strong>fWAR</strong><TD><strong>G</strong><TH><strong>H/AB</strong>" +
                        "<TD><strong>R</strong><TD><strong>HR</strong><TD><strong>RBI</strong><TD><strong>SB</strong>" +
                        "<TD><strong>BA</strong><TD><strong>OBP</strong><TD><strong>SLG</strong><TD><strong>OPS</strong>" +
                        "<TD><strong>DRS</strong><TD><strong>Awards</strong></TR>");
            }
            else if (league.equals("al") && batter.getPos().equals("DH") && numOfPlayers == 9) {
                pw.format("<TR ALIGN=RIGHT><TD><strong>%s</strong><TD><a href=%s>%s</a><TD>%.1f<TD>%.1f<TD>%d<TD>%d/%d<TD>%d<TD>%d<TD>%d" +
                                "<TD>%d<TD>%.3f<TD>%.3f<TD>%.3f<TD>%.3f<TD>%d<TD>%s</TR>",/*<TD>%.3f<TD>%.3f<TD>%.3f<TD>%.3f",*/ pos, b_href,
                        name, war, fwar, g, h, ab, r, hr, rbi, sb, avg, obp, slg, ops, drs, awards);//, ba_2017, obp_2017, slg_2017, ops_2017);
                pw.println("<TR ALIGN=RIGHT style=\"background-color: lightgray\"><TD><strong>Pos</strong><TD>" +
                        "<strong>Name</strong><TD><strong>bWAR</strong><TD><strong>fWAR</strong><TD><strong>G</strong><TH><strong>H/AB</strong>" +
                        "<TD><strong>R</strong><TD><strong>HR</strong><TD><strong>RBI</strong><TD><strong>SB</strong>" +
                        "<TD><strong>BA</strong><TD><strong>OBP</strong><TD><strong>SLG</strong><TD><strong>OPS</strong>" +
                        "<TD><strong>DRS</strong><TD><strong>Awards</strong></TR>");
            }
            else {
                pw.format("<TR ALIGN=RIGHT><TD><strong>%s</strong><TD><a href=%s>%s</a><TD>%.1f<TD>%.1f<TD>%d<TD>%d/%d<TD>%d<TD>%d<TD>%d" +
                                "<TD>%d<TD>%.3f<TD>%.3f<TD>%.3f<TD>%.3f<TD>%d<TD>%s</TR>",/*<TD>%.3f<TD>%.3f<TD>%.3f<TD>%.3f",*/ pos, b_href,
                        name, war, fwar, g, h, ab, r, hr, rbi, sb, avg, obp, slg, ops, drs, awards);//, ba_2017, obp_2017, slg_2017, ops_2017);
            }
            teamWar = teamWar + war;
            teamfWar = teamfWar + fwar;
            teamH = teamH + h;
            teamAB = teamAB + ab;
            teamR = teamR + r;
            teamHR = teamHR + hr;
            teamRBI = teamRBI + rbi;
            teamSB = teamSB + sb;
            teamBB = teamBB + bb;
            teamTB = teamTB + tb;
            teamPA = teamPA + pa;
            teamHBP = teamHBP + hbp;
            teamDRS = teamDRS + drs;
        }
        double avg = (double)teamH / teamAB;
        double obp = (double)(teamH + teamBB + teamHBP) / teamPA;
        double slg = (double)teamTB / teamAB;
        double ops = obp + slg;
        pw.format("<TR ALIGN=RIGHT><TD><TD>Total<TD>%.1f<TD>%.1f<TD>%d<TH>%d/%d<TD>%d<TD>%d<TD>%d<TD>%d<TD>%.3f<TD>%.3f<TD>" +
                        "%.3f<TD>%.3f<TD>%d",
                teamWar, teamfWar, teamG, teamH, teamAB, teamR, teamHR, teamRBI, teamSB, avg, obp, slg, ops, teamDRS);
        pw.println("</TABLE>");
        pw.println("<caption>*-Bats Left Handed #-Switch Hitter");
        System.out.println(teamWar);
        return new BattingTeam(team, href, teamWar, teamfWar, teamAB, teamH, teamR, teamHR, teamRBI, teamSB, avg, obp, slg, ops);
    }

    public static void main(String[] args) throws IOException {
        String[] NLteams = {"ARI", "ATL", "CHC", "CIN", "COL", "LAD", "MIA", "MIL", "NYM", "PHI", "PIT", "SDP", "SFG",
                "STL", "WSN"};
        String[] ALteams = {"BAL", "BOS", "CHW", "CLE", "DET", "HOU", "KCR", "LAA", "MIN", "NYY", "OAK", "SEA", "TBR",
                "TEX", "TOR"};
        String[] teams = {"ARI", "ATL", "BAL", "BOS", "CHC", "CHW", "CIN", "CLE", "COL", "DET", "HOU", "KCR", "LAA",
                "LAD", "MIA", "MIL", "MIN", "NYM", "NYY", "OAK", "PHI", "PIT", "SDP", "SFG", "SEA", "STL", "TBR", "TEX",
                "TOR", "WSN"};
        String[] fTeams = {"LAA", "BAL", "BOS", "CHW", "CLE", "DET", "KCR", "MIN", "NYY", "OAK", "SEA", "TBR", "TEX",
                "TOR", "ARI", "ATL", "CHC", "CIN", "COL", "MIA", "HOU", "LAD", "MIL", "WSN", "NYM", "PHI", "PIT", "STL",
                "SDP", "SFG"};

        HashMap<People, Double> players = new HashMap<People, Double>();
        HashMap<People, Double> nlPlayers = new HashMap<People, Double>();
        HashMap<People, Double> alPlayers = new HashMap<People, Double>();
        HashMap<Player, Integer> homerLeaders = new HashMap<Player, Integer>();
        HashMap<Player, Integer> nlHomerLeaders = new HashMap<Player, Integer>();
        HashMap<Player, Integer> alHomerLeaders = new HashMap<Player, Integer>();
        HashMap<People, Double> nlAllStars = new HashMap<People, Double>();
        HashMap<People, Double> alAllStars = new HashMap<People, Double>();
        HashMap<People, Double> diffInWar = new HashMap<People, Double>();
        ArrayList<BattingTeam> mlbTeams = new ArrayList<>(30);
        HashMap<People, Double> fWars = new HashMap<People, Double>();
        HashMap<People, Double> nlfWars = new HashMap<People, Double>();
        HashMap<People, Double> alfWars = new HashMap<People, Double>();
        HashMap<Player, Double> btqualified = new HashMap<Player, Double>();
        HashMap<Player, Double> nlbtqualified = new HashMap<Player, Double>();
        HashMap<Player, Double> albtqualified = new HashMap<Player, Double>();
        HashMap<Player, Double> opsLeaders = new HashMap<Player, Double>();
        HashMap<Player, Double> nlopsLeaders = new HashMap<Player, Double>();
        HashMap<Player, Double> alopsLeaders = new HashMap<Player, Double>();
        HashMap<Player, Integer> catchers = new HashMap<Player, Integer>();
        HashMap<Player, Integer> firstB = new HashMap<Player, Integer>();
        HashMap<Player, Integer> secondB = new HashMap<Player, Integer>();
        HashMap<Player, Integer> ss = new HashMap<Player, Integer>();
        HashMap<Player, Integer> thirdB = new HashMap<Player, Integer>();
        HashMap<Player, Integer> lf = new HashMap<Player, Integer>();
        HashMap<Player, Integer> cf = new HashMap<Player, Integer>();
        HashMap<Player, Integer> rf = new HashMap<Player, Integer>();
        ArrayList<Player> didiVxander = new ArrayList<Player>(2);
        LocalDate date = java.time.LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedDate = date.format(formatter);
        DateTimeFormatter fileNameFormat = DateTimeFormatter.ofPattern("MM-dd");
        String filenamedate = date.format(fileNameFormat);
        PrintWriter pw = new PrintWriter(new FileWriter(filenamedate + ".html"));
        pw.format("<caption>%s</caption>", formattedDate);
        pw.println("<HTML>");
        pw.println("<meta charset='UTF-8'>");
        for (String team : teams) {
            System.out.println(team);
            String url = "https://www.baseball-reference.com/teams/" + team + "/2018.shtml";
            Document doc = Jsoup.connect(url).get();
            Elements tables = doc.select("table");
            int name_idx, pos_idx, g_idx, avg_idx, obp_idx, slg_idx, h_idx, ab_idx, r_idx, hr_idx,
                    rbi_idx, sb_idx, bbb_idx, tb_idx, pa_idx, hbp_idx, gs_idx, era_idx, ip_idx,
                    bb_idx, k_idx, sv_idx, erap_idx, fip_idx;
            int team_games = GetTeamGames(url);
            int t_idx = java.util.Arrays.asList(fTeams).indexOf(team) + 1;
            ArrayList<Player> batters;
            ArrayList<Pitcher> pitchers;
            for (int i = 0; i < tables.size(); i++) {
                if (tables.get(i).id().equals("team_batting")) {
                    Element b_table = tables.get(i);
                    Elements b_rows = b_table.select("tr");

                    Elements headers = b_rows.get(0).select("th");
                    name_idx = findIdx("Name", headers);
                    pos_idx = findIdx("Pos", headers);
                    g_idx = findIdx("G", headers);
                    avg_idx = findIdx("BA", headers);
                    obp_idx = findIdx("OBP", headers);
                    slg_idx = findIdx("SLG", headers);
                    h_idx = findIdx("H", headers);
                    ab_idx = findIdx("AB", headers);
                    r_idx = findIdx("R", headers);
                    hr_idx = findIdx("HR", headers);
                    rbi_idx = findIdx("RBI", headers);
                    sb_idx = findIdx("SB", headers);
                    bbb_idx = findIdx("BB", headers);
                    tb_idx = findIdx("TB", headers);
                    pa_idx = findIdx("PA", headers);
                    hbp_idx = findIdx("HBP", headers);

                    String bf_href1 = "https://www.fangraphs.com/leaders.aspx?pos=all&stats=bat&lg=all&qual=0&type=8&season=2018&" +
                            "month=0&season1=2018&ind=0&team=" + t_idx + "&rost=0&age=0&filter=&players=0";
                    String bf_href2 = "https://www.fangraphs.com/leaders.aspx?pos=all&stats=bat&lg=all&qual=0&type=8&season=2018&" +
                            "month=0&season1=2018&ind=0&team=" + t_idx + "&rost=0&age=0&filter=&players=0&page=2_30";
                    Document bat_teamDoc1 = Jsoup.connect(bf_href1).get();
                    Document bat_teamDoc2 = Jsoup.connect(bf_href2).get();
                    int numOfPagesF = getNumOfPagesF(t_idx);
                    int numOfPagesB = getNumOfPagesB(t_idx);
                    if (Arrays.asList(NLteams).contains(team)) {
                        batters = batterStats(8, numOfPagesF, numOfPagesB, b_rows, g_idx, h_idx, ab_idx, r_idx, hr_idx, rbi_idx, sb_idx,
                                bbb_idx, tb_idx, pa_idx, hbp_idx, name_idx, avg_idx, obp_idx, slg_idx, team, pos_idx,
                                t_idx, bat_teamDoc1, bat_teamDoc2, fTeams);
                        mlbTeams.add(writeBatterTable(batters, pw, team, url, "nl"));
                        for (Player batter : batters) {

//                            double lavg = batter.getLastAvg();
//                            double lobp = batter.getLastObp();
//                            double lslg = batter.getLastSlg();
//                            double lops = batter.getLastOps();
//                            if (lavg == lobp && lobp == lslg && lslg == lops && lops == 0.0) {
//                                rookies.put(batter, batter.getWar());
//                                nlRookies.put(batter, batter.getWar());
//                            }
                            batter = updateBatter(batter);/*
                            if (!(players.containsKey(batter))) {
                                nlPlayers.put(batter, batter.getWar());
                                players.put(batter, batter.getWar());
                                fWars.put(batter, batter.getFwar());
                                nlfWars.put(batter, batter.getFwar());
                                homerLeaders.put(batter, batter.getHr());
                                nlHomerLeaders.put(batter, batter.getHr());
                                diffInWar.put(batter, batter.getFwar() - batter.getWar());
                            }
                            else if (players.containsKey(batter) && !(nlPlayers.containsKey(batter))) {
                                nlPlayers.put(batter, batter.getWar());
                                nlfWars.put(batter, batter.getFwar());
                                nlHomerLeaders.put(batter, batter.getHr());
                            }*/
                            nlPlayers.put(batter, batter.getWar());
                            players.put(batter, batter.getWar());
                            fWars.put(batter, batter.getFwar());
                            nlfWars.put(batter, batter.getFwar());
                            homerLeaders.put(batter, batter.getHr());
                            nlHomerLeaders.put(batter, batter.getHr());
                            diffInWar.put(batter, batter.getFwar() - batter.getWar());
                            if (batter.getAwards().contains("AS")) {
                                nlAllStars.put(batter, batter.getWar());
                            }
                            if ((batter.getPa() / (double) team_games) >= 3.1) {
                                btqualified.put(batter, batter.getAvg());
                                nlbtqualified.put(batter, batter.getAvg());
                                opsLeaders.put(batter, batter.getOps());
                                nlopsLeaders.put(batter, batter.getOps());
                            }
                            if (batter.getPos().equals("C")) {
                                catchers.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("1B")) {
                                firstB.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("2B")) {
                                secondB.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("3B")) {
                                thirdB.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("SS")) {
                                ss.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("LF")) {
                                lf.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("CF")) {
                                cf.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("RF")) {
                                rf.put(batter,  batter.getDrs());
                            }
                        }
                    }
                    else if (Arrays.asList(ALteams).contains(team)) {
                        batters = batterStats(9, numOfPagesF, numOfPagesB, b_rows,
                                g_idx, h_idx, ab_idx, r_idx, hr_idx, rbi_idx, sb_idx, bbb_idx,
                                tb_idx, pa_idx, hbp_idx, name_idx, avg_idx, obp_idx, slg_idx, team,
                                pos_idx, t_idx, bat_teamDoc1, bat_teamDoc2, fTeams);
                        mlbTeams.add(writeBatterTable(batters, pw, team, url, "al"));
                        for (Player batter : batters) {
//                            double lavg = batter.getLastAvg();
//                            double lobp = batter.getLastObp();
//                            double lslg = batter.getLastSlg();
//                            double lops = batter.getLastOps();
//                            if (lavg == lobp && lobp == lslg && lslg == lops && lops == 0.0) {
//                                rookies.put(batter, batter.getWar());
//                                alRookies.put(batter, batter.getWar());
//                            }
                            batter = updateBatter(batter);
                            alPlayers.put(batter, batter.getWar());
                            players.put(batter, batter.getWar());
                            fWars.put(batter, batter.getFwar());
                            alfWars.put(batter, batter.getFwar());
                            homerLeaders.put(batter, batter.getHr());
                            alHomerLeaders.put(batter, batter.getHr());
                            diffInWar.put(batter, batter.getFwar() - batter.getWar());
                            if (batter.getAwards().contains("AS")) {
                                alAllStars.put(batter, batter.getWar());
                            }
                            if ((batter.getPa() / (double) team_games) >= 3.1) {
                                btqualified.put(batter, batter.getAvg());
                                albtqualified.put(batter, batter.getAvg());
                                opsLeaders.put(batter, batter.getOps());
                                alopsLeaders.put(batter, batter.getOps());
                            }
                            if (batter.getName().contains("Xander Bogaerts") || batter.getName().contains("Didi Gregorius")) {
                                didiVxander.add(batter);
                            }
                            if (batter.getPos().equals("C")) {
                                catchers.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("1B")) {
                                firstB.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("2B")) {
                                secondB.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("3B")) {
                                thirdB.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("SS")) {
                                ss.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("LF")) {
                                lf.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("CF")) {
                                cf.put(batter,  batter.getDrs());
                            }
                            else if (batter.getPos().equals("RF")) {
                                rf.put(batter,  batter.getDrs());
                            }
                        }
                    }

                } else if (tables.get(i).id().equals("team_pitching")) {
                    Element p_table = tables.get(i);
                    Elements p_rows = p_table.select("tr");

                    Elements headers = p_rows.get(0).select("th");
                    name_idx = findIdx("Name", headers);
                    g_idx = findIdx("G", headers);
                    gs_idx = findIdx("GS", headers);
                    pos_idx = findIdx("Pos", headers);
                    era_idx = findIdx("ERA", headers);
                    ip_idx = findIdx("IP", headers);
                    bb_idx = findIdx("BB", headers);
                    k_idx = findIdx("SO", headers);
                    sv_idx = findIdx("SV", headers);
                    erap_idx = findIdx("ERA+", headers);
                    fip_idx = findIdx("FIP", headers);
                    int line_to_skip = getLineToSkip(p_rows);


                    int numOfPagesP = getNumOfPagesP(t_idx);
                    pitchers = pitcherStats(p_rows, name_idx, g_idx, gs_idx, era_idx, ip_idx, bb_idx, k_idx, sv_idx, line_to_skip,
                            team, pos_idx, erap_idx, fip_idx, fTeams, numOfPagesP,t_idx);

                    pw.println("<TABLE BORDER><TR style=\"background-color: lightgray\"><TH>Pos<TH>Name<TH>bWAR<TH>" +
                            "fWAR<TH>G<TH>GS<TH>IP<TH>ERA<TH>BB<TH>SO<TH>SV<TH>SO/W<TH>SO9<TH>FIP<TH>ERA+<TH>Awards</TR>");
                    for (Pitcher pitcher : pitchers) {
                        String href = pitcher.getHref();
                        String name = pitcher.getName();
                        String pos = pitcher.getPos();
                        int g = pitcher.getG();
                        int gs = pitcher.getGS();
                        double war = pitcher.getWar();
                        double fwar = pitcher.getFwar();
                        double ip = pitcher.getIP();
                        double era = pitcher.getERA();
                        double fip = pitcher.getFIP();
                        int erap = pitcher.getERAPlus();
                        int er = ((int) ((era * ip) / 9));
                        int bb = pitcher.getBB();
                        int k = pitcher.getK();
                        int saves = pitcher.getSV();
                        double ktoBB = pitcher.KtoBB();
                        double kPer9 = pitcher.Kper9();
                        String awards = pitcher.getAwards();
                        pw.format("<TR ALIGN=RIGHT><TD><strong>%s</strong><TD><a href=%s>%s</a><TD>%.1f<TD>%.1f<TD>%d<TD>%d<TD>%.1f<TD>" +
                                        "%.2f<TD>%d<TD>%d<TD>%d<TD>%.2f<TD>%.2f<TD>%.2f<TD>%d<TD>%s", pos, href, name, war, fwar, g, gs, ip, era, bb, k, saves,
                                ktoBB, kPer9, fip, erap, awards);
                        name = pitcher.getOriginalName();
                        if (Arrays.asList(NLteams).contains(team)) {
                            pitcher = updatePitcher(pitcher);

                            nlPlayers.put(pitcher, pitcher.getWar());
                            players.put(pitcher, pitcher.getWar());
                            fWars.put(pitcher, pitcher.getFwar());
                            nlfWars.put(pitcher, pitcher.getFwar());
                            diffInWar.put(pitcher, pitcher.getFwar() - pitcher.getWar());
                            if (pitcher.getAwards().contains("AS")) {
                                nlAllStars.put(pitcher, war);
                            }
                        }
                        if (Arrays.asList(ALteams).contains(team)) {
                            pitcher = updatePitcher(pitcher);

                            alPlayers.put(pitcher, pitcher.getWar());
                            players.put(pitcher, pitcher.getWar());
                            fWars.put(pitcher, pitcher.getFwar());
                            alfWars.put(pitcher, pitcher.getFwar());
                            diffInWar.put(pitcher, pitcher.getFwar() - pitcher.getWar());
                            if (pitcher.getAwards().contains("AS")) {
                                alAllStars.put(pitcher, war);
                            }
                        }
                    }

                    pw.println("</TABLE>");
                    pw.println("<caption>*-Pitches Left-Handed\n</caption>");
                    pw.println("</div>");



                }
            }
        }
        pw.println("<div>");
        pw.println("<h3><b>Team Batting Stats</b></h3>");
        pw.println("<table class=\"sortable\">");
        pw.println("<thead><TR><th class=\"header\">Team</th><th class=\"header\">bWAR</th><th class=\"header\">fWAR</th><th class=\"header\">H/AB" +
                "</th><th class=\"header\">R</th><th class=\"header\">HR</th><th class=\"header\">RBI</th>" +
                "<th class=\"header\">SB</th><th class=\"header\">BA</th><th class=\"header\">OBP</th>" +
                "<th class=\"header\">SLG</th><th class=\"header\">OPS</th></TR></thead>");
        pw.println("<tbody>");
        for (BattingTeam team : mlbTeams) {
            pw.format("<TR ALIGN=RIGHT><TD><a href=%s>%s</a></TD><TD>%.1f</TD><TD>%.1f</TD><TD>%d/%d</TD><TD>%d</TD><TD>%d</TD><TD>%d</TD><TD>" +
                    "%d</TD><TD>%.3f</TD><TD>%.3f</TD><TD>%.3f</TD><TD>%.3f</TD></TR>", team.getHref(), team.getName(),
                    team.getbWar(), team.getfWar(), team.getH(), team.getAb(), team.getR(), team.getHr(), team.getRbi(),
                    team.getSb(), team.getAvg(), team.getObp(), team.getSlg(), team.getOps());
        }
        pw.println("</tbody>");
        pw.println("</TABLE>");
        pw.println("</div>");

// --------------------------------------------------------------------------------------------------------
// ------------------------------------------ LEADERBOARDS ------------------------------------------------
// --------------------------------------------------------------------------------------------------------



        players = sortHashMapByValues(players);
        alPlayers = sortHashMapByValues(alPlayers);
        nlPlayers = sortHashMapByValues(nlPlayers);

        List keys = new ArrayList(alPlayers.keySet());
        List vals = new ArrayList(alPlayers.values());
        pw.println("<div>");
        pw.println("<caption><b>AL bWAR Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>bWAR</TR>");
        for (int i = 1; i < 16; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double bwar = player.getWar();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i, href, name, team,
                    bwar);
            if (i == 15) {
                int h = 1;
                while (bwar == (double) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double war2 = player2.getWar();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + h, href2, name2, team2,
                            war2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(nlPlayers.keySet());
        vals = new ArrayList(nlPlayers.values());
        pw.println("<div>");
        pw.println("<caption><b>NL WAR Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>WAR</TR>");
        for (int i = 1; i < 16; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double bwar = player.getWar();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i, href, name, team,
                    bwar);
            if (i == 15) {
                int h = 1;
                while (bwar == (double) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double war2 = player2.getWar();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + h, href2, name2, team2,
                            war2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(players.keySet());
        vals = new ArrayList(players.values());
        pw.println("<div>");
        pw.println("<caption><b>MLB WAR Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>WAR</TR>");
        for (int i = 1; i < 21; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double bwar = player.getWar();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i, href, name, team,
                    bwar);
            if (i == 20) {
                int h = 1;
                while (bwar == (double) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double war2 = player2.getWar();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + h, href2, name2, team2,
                            war2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        pw.println("</TABLE>");
        pw.println("</div>");
        keys = new ArrayList(players.keySet());
        vals = new ArrayList(players.values());
        pw.println("<div>");
        pw.println("<caption><b>MLB Worst WAR</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>WAR</TR>");
        for (int i = 0; i < 20; i ++) {
            People player = (People)keys.get(i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double war = player.getWar();
            System.out.println((i + 1) + " " + name + " " + team + " " + vals.get(i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + 1, href, name, team,
                    war);
            if (i == 19) {
                int h = 1;
                while (war == (double) vals.get(i + h)) {
                    People player2 = (People) keys.get(i + h);
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double war2 = player2.getWar();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(i + h));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + h, href2, name2, team2,
                            war2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        homerLeaders = sortHashMapByInts(homerLeaders);
        alHomerLeaders = sortHashMapByInts(alHomerLeaders);
        nlHomerLeaders = sortHashMapByInts(nlHomerLeaders);

        keys = new ArrayList(alHomerLeaders.keySet());
        vals = new ArrayList(alHomerLeaders.values());
        pw.println("<div>");
        pw.println("<caption><b>AL HR Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>HR</TR>");
        for (int i = 1; i < 16; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            int homers = (int)vals.get(vals.size() - i);
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%d", i, href, name, team,
                    homers);
            if (i == 15) {
                int h = 1;
                while (homers == (int) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    int homers2 = (int) vals.get(vals.size() - (i + h));
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%d", i + h, href2, name2, team2,
                            homers2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");
        keys = new ArrayList(nlHomerLeaders.keySet());
        vals = new ArrayList(nlHomerLeaders.values());
        pw.println("<div>");
        pw.println("<caption><b>NL HR Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>HR</TR>");
        for (int i = 1; i < 16; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            int homers = (int)vals.get(vals.size() - i);
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%d", i, href, name, team,
                    homers);
            if (i == 15) {
                int h = 1;
                while (homers == (int) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    int homers2 = (int) vals.get(vals.size() - (i + h));
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%d", i + h, href2, name2, team2,
                            homers2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");
        keys = new ArrayList(homerLeaders.keySet());
        vals = new ArrayList(homerLeaders.values());
        pw.println("<div>");
        pw.println("<caption><b>MLB HR Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>HR</TR>");
        for (int i = 1; i < 21; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            int homers = (int)vals.get(vals.size() - i);
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%d", i, href, name, team,
                    homers);
            if (i == 20) {
                int h = 1;
                while (homers == (int) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    int homers2 = (int) vals.get(vals.size() - (i + h));
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%d", i + h, href2, name2, team2,
                            homers2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        pw.println("<h3><b>All Stars</b></h3>");

        keys = new ArrayList(alAllStars.keySet());
        String[] positions = {"C", "1B", "2B", "3B", "SS", "F", "DH", "SP", "RP", "CL"};
        pw.println("<div>");
        pw.println("<caption><b>AL All Stars</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Pos<TH>Name<TH>Team</TR>");
        for (String pos : positions) {
            for (Object key : keys) {
                People p = (People) key;
                if (pos.equals("CL") && p.getPos().equals(pos)) {
                    pw.format("<TR ALIGN=RIGHT><TD>RP<TD><a href=%s>%s</a><TD>%s", p.getHref(), p.getName(), p.getTeam());
                    System.out.println("RP" + p.getName());
                }
                else if (p.getPos().equals(pos)) {
                    pw.format("<TR ALIGN=RIGHT><TD>%s<TD><a href=%s>%s</a><TD>%s", p.getPos(), p.getHref(), p.getName(),
                            p.getTeam());
                }
                else if (p.getPos().contains(pos) && pos.equals("F")) {
                    pw.format("<TR ALIGN=RIGHT><TD>OF<TD><a href=%s>%s</a><TD>%s", p.getHref(), p.getName(), p.getTeam());
                }
            }
        }

        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(nlAllStars.keySet());
        pw.println("<div>");
        pw.println("<caption><b>NL All Stars</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Pos<TH>Name<TH>Team</TR>");
        for (String pos : positions) {
            for (Object key : keys) {
                People p = (People) key;
                if (pos.equals("CL") && p.getPos().equals(pos)) {
                    pw.format("<TR ALIGN=RIGHT><TD>RP<TD><a href=%s>%s</a><TD>%s", p.getHref(), p.getName(), p.getTeam());
                }
                else if (p.getPos().equals(pos)) {
                    pw.format("<TR ALIGN=RIGHT><TD>%s<TD><a href=%s>%s</a><TD>%s", p.getPos(), p.getHref(), p.getName(),
                            p.getTeam());
                }
                else if (p.getPos().contains(pos) && pos.equals("F")) {
                    pw.format("<TR ALIGN=RIGHT><TD>OF<TD><a href=%s>%s</a><TD>%s", p.getHref(), p.getName(), p.getTeam());
                }
            }
        }

        pw.println("</TABLE>");
        pw.println("</div>");

        diffInWar = sortHashMapByValues(diffInWar);

        keys = new ArrayList(diffInWar.keySet());
        vals = new ArrayList(diffInWar.values());
        pw.println("<div>");
        pw.println("<caption><b>Players with Biggest Boost in fWAR</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>bWAR<TH>fWAR<TH>Difference</TR>");
        for (int i = 1; i < 16; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double fwar = player.getFwar();
            double war = player.getWar();
            double diff = (double)vals.get(vals.size() - i);
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f<TD>%.1f<TD>%.1f", i, href, name, team,
                    war, fwar, diff);
            if (i == 15) {
                int h = 1;
                while (diff == (double) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double fwar2 = player2.getFwar();
                    double war2 = player2.getWar();
                    double diff2 = (double) vals.get(vals.size() - (i + h));
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f<TD>%.1f<TD>%.1f", i + h, href2,
                            name2, team2, war2, fwar2, diff2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(diffInWar.keySet());
        vals = new ArrayList(diffInWar.values());
        pw.println("<div>");
        pw.println("<caption><b>Players with Biggest Decrease in fWAR</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>bWAR<TH>fWAR<TH>Difference</TR>");
        for (int i = 0; i < 15; i ++) {
            People player = (People)keys.get(i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double fwar = player.getFwar();
            double war = player.getWar();
            double diff = (double)vals.get(i);
            System.out.println(i + " " + name + " " + team + " " + vals.get(i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f<TD>%.1f<TD>%.1f", i+1, href, name, team,
                    war, fwar, diff);
            if (i == 14) {
                int h = 1;
                while (diff == (double) vals.get(i + h)) {
                    People player2 = (People) keys.get(i + h);
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double fwar2 = player2.getFwar();
                    double war2 = player2.getWar();
                    double diff2 = (double) vals.get(i + h);
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(i + h));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f<TD>%.1f<TD>%.1f", i + h, href2,
                            name2, team2, war2, fwar2, diff2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        HashMap<Player, Double> qualified = sortHashMapByPlayers(btqualified);
        HashMap<Player, Double> alqualified = sortHashMapByPlayers(albtqualified);
        HashMap<Player, Double> nlqualified = sortHashMapByPlayers(nlbtqualified);

        keys = new ArrayList(alqualified.keySet());
        vals = new ArrayList(alqualified.values());
        pw.println("<div>");
        pw.println("<caption><b>AL BA Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>BA</TR>");
        for (int i = 1; i < 16; i ++) {
            Player player = (Player) keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double avg = player.getAvg();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i, href, name, team,
                    vals.get(vals.size() - i));
            if (i == 15) {
                int h = 1;
                while (avg == (double) vals.get(vals.size() - (i + h))) {
                    Player player2 = (Player) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double avg2 = player2.getAvg();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i + h, href2, name2, team2,
                            avg2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(nlqualified.keySet());
        vals = new ArrayList(nlqualified.values());
        pw.println("<div>");
        pw.println("<caption><b>NL BA Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>BA</TR>");
        for (int i = 1; i < 16; i ++) {
            Player player = (Player)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double avg = player.getAvg();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i, href, name, team,
                    avg);
            if (i == 15) {
                int h = 1;
                while (avg == (double) vals.get(vals.size() - (i + h))) {
                    Player player2 = (Player) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double avg2 = player2.getAvg();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i + h, href2, name2, team2,
                            avg2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(qualified.keySet());
        vals = new ArrayList(qualified.values());
        pw.println("<div>");
        pw.println("<caption><b>MLB BA Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>BA</TR>");
        for (int i = 1; i < 21; i ++) {
            Player player = (Player)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double avg = player.getAvg();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i, href, name, team,
                    avg);
            if (i == 20) {
                int h = 1;
                while (avg == (double) vals.get(vals.size() - (i + h))) {
                    Player player2 = (Player) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double avg2 = player2.getAvg();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i + h, href2, name2, team2,
                            avg2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        alopsLeaders = sortHashMapByPlayers(alopsLeaders);
        nlopsLeaders = sortHashMapByPlayers(nlopsLeaders);
        opsLeaders = sortHashMapByPlayers(opsLeaders);

        keys = new ArrayList(alopsLeaders.keySet());
        vals = new ArrayList(alopsLeaders.values());
        pw.println("<div>");
        pw.println("<caption><b>AL OPS Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>OPS</TR>");
        for (int i = 1; i < 16; i ++) {
            Player player = (Player)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double ops = player.getOps();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i, href, name, team,
                    ops);
            if (i == 15) {
                int h = 1;
                while (ops == (double) vals.get(vals.size() - (i + h))) {
                    Player player2 = (Player) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double ops2 = player2.getOps();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i + h, href2, name2, team2,
                            ops2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(nlopsLeaders.keySet());
        vals = new ArrayList(nlopsLeaders.values());
        pw.println("<div>");
        pw.println("<caption><b>NL OPS Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>OPS</TR>");
        for (int i = 1; i < 16; i ++) {
            Player player = (Player)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double ops = player.getOps();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i, href, name, team,
                    ops);
            if (i == 15) {
                int h = 1;
                while (ops == (double) vals.get(vals.size() - (i + h))) {
                    Player player2 = (Player) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double ops2 = player2.getOps();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i + h, href2, name2, team2,
                            ops2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(opsLeaders.keySet());
        vals = new ArrayList(opsLeaders.values());
        pw.println("<div>");
        pw.println("<caption><b>MLB OPS Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>OPS</TR>");
        for (int i = 1; i < 21; i ++) {
            Player player = (Player)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double ops = player.getOps();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i, href, name, team,
                    ops);
            if (i == 20) {
                int h = 1;
                while (ops == (double) vals.get(vals.size() - (i + h))) {
                    Player player2 = (Player) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double ops2 = player2.getOps();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i + h, href2, name2, team2,
                            ops2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        pw.println("</TABLE>");
        pw.println("</div>");
        keys = new ArrayList(qualified.keySet());
        vals = new ArrayList(qualified.values());
        pw.println("<div>");
        pw.println("<caption><b>MLB Worst BA</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>BA</TR>");
        for (int i = 0; i < 20; i ++) {
            Player player = (Player)keys.get(i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double avg = player.getAvg();
            System.out.println((i + 1) + " " + name + " " + team + " " + vals.get(i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i + 1, href, name, team,
                    avg);
            if (i == 19) {
                int h = 1;
                while (avg == (double) vals.get(i + h)) {
                    Player player2 = (Player) keys.get(i + h);
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double avg2 = player2.getAvg();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(i + h));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.3f", i + h, href2, name2, team2,
                            avg2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        players = sortHashMapByValues(fWars);
        alPlayers = sortHashMapByValues(alfWars);
        nlPlayers = sortHashMapByValues(nlfWars);

        keys = new ArrayList(alPlayers.keySet());
        vals = new ArrayList(alPlayers.values());
        pw.println("<div>");
        pw.println("<caption><b>AL fWAR Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>fWAR</TR>");
        for (int i = 1; i < 16; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double war = player.getFwar();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i, href, name, team,
                    vals.get(vals.size() - i));
            if (i == 15) {
                int h = 1;
                while (war == (double) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double war2 = player2.getFwar();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + h, href2, name2, team2,
                            war2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(nlPlayers.keySet());
        vals = new ArrayList(nlPlayers.values());
        pw.println("<div>");
        pw.println("<caption><b>NL fWAR Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>fWAR</TR>");
        for (int i = 1; i < 16; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double war = player.getFwar();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i, href, name, team,
                    war);
            if (i == 15) {
                int h = 1;
                while (war == (double) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double war2 = player2.getFwar();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + h, href2, name2, team2,
                            war2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        keys = new ArrayList(players.keySet());
        vals = new ArrayList(players.values());
        pw.println("<div>");
        pw.println("<caption><b>MLB fWAR Leaders</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>fWAR</TR>");
        for (int i = 1; i < 21; i ++) {
            People player = (People)keys.get(keys.size() - i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double war = player.getFwar();
            System.out.println(i + " " + name + " " + team + " " + vals.get(vals.size() - i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i, href, name, team,
                    war);
            if (i == 20) {
                int h = 1;
                while (war == (double) vals.get(vals.size() - (i + h))) {
                    People player2 = (People) keys.get(keys.size() - (i + h));
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double war2 = player2.getFwar();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(vals.size() - (i + h)));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + h, href2, name2, team2,
                            war2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        pw.println("<div>");
        pw.println("<caption><b>MLB Worst fWARs</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>Name<TH>Team<TH>fWAR</TR>");
        for (int i = 0; i < 20; i ++) {
            People player = (People)keys.get(i);
            String name = player.getName();
            String team = player.getTeam();
            String href = player.getHref();
            double war = player.getFwar();
            System.out.println(i + " " + name + " " + team + " " + vals.get(i));
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + 1, href, name, team,
                    war);
            if (i == 19) {
                int h = 1;
                while (war == (double) vals.get(i + h)) {
                    People player2 = (People) keys.get(i + h);
                    String name2 = player2.getName();
                    String team2 = player2.getTeam();
                    String href2 = player2.getHref();
                    double war2 = player2.getFwar();
                    System.out.println((i + h) + " " + name2 + " " + team2 + " " + vals.get(i + h));
                    pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a><TD>%s<TD>%.1f", i + h, href2, name2, team2,
                            war2);
                    h++;
                }
            }
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        ArrayList<Player> c_keys = new ArrayList(sortHashMapByInts(catchers).keySet());
        ArrayList<Player> firstB_keys = new ArrayList(sortHashMapByInts(firstB).keySet());
        ArrayList<Player> secondB_keys = new ArrayList(sortHashMapByInts(secondB).keySet());
        ArrayList<Player> thirdB_keys = new ArrayList(sortHashMapByInts(thirdB).keySet());
        ArrayList<Player> ss_keys = new ArrayList(sortHashMapByInts(ss).keySet());
        ArrayList<Player> lf_keys = new ArrayList(sortHashMapByInts(lf).keySet());
        ArrayList<Player> cf_keys = new ArrayList(sortHashMapByInts(cf).keySet());
        ArrayList<Player> rf_keys = new ArrayList(sortHashMapByInts(rf).keySet());
        pw.println("<div>");
        pw.println("<caption><b>DRS Leaders at Each Position</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>C<TH>1B<TH>2B<TH>SS<TH>3B<TH>LF<TH>CF<TH>RF</TR>");
        for (int i = 1; i < 6; i ++) {
            Player catcher = c_keys.get(c_keys.size() - i);
            Player first = firstB_keys.get(firstB_keys.size() - i);
            Player second = secondB_keys.get(secondB_keys.size() - i);
            Player ss_p = ss_keys.get(ss_keys.size() - i);
            Player third = thirdB_keys.get(thirdB_keys.size() - i);
            Player lf_p = lf_keys.get(lf_keys.size() - i);
            Player cf_p = cf_keys.get(cf_keys.size() - i);
            Player rf_p = rf_keys.get(rf_keys.size() - i);
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)<TD>" +
                            "<a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)<TD>" +
                            "<a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)", i,
                    catcher.getHref(), catcher.getName(), catcher.getDrs(), first.getHref(), first.getName(), first.getDrs(),
                    second.getHref(), second.getName(), second.getDrs(), ss_p.getHref(), ss_p.getName(), ss_p.getDrs(),
                    third.getHref(), third.getName(), third.getDrs(), lf_p.getHref(), lf_p.getName(), lf_p.getDrs(),
                    cf_p.getHref(), cf_p.getName(), cf_p.getDrs(), rf_p.getHref(), rf_p.getName(), rf_p.getDrs());
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        pw.println("<div>");
        pw.println("<caption><b>Worst DRS at Each Position</b></caption>");
        pw.println("<TABLE BORDER><TR><TH>Rank<TH>C<TH>1B<TH>2B<TH>SS<TH>3B<TH>LF<TH>CF<TH>RF</TR>");
        for (int i = 0; i < 5; i ++) {
            Player catcher = c_keys.get(i);
            Player first = firstB_keys.get(i);
            Player second = secondB_keys.get(i);
            Player ss_p = ss_keys.get(i);
            Player third = thirdB_keys.get(i);
            Player lf_p = lf_keys.get(i);
            Player cf_p = cf_keys.get(i);
            Player rf_p = rf_keys.get(i);
            pw.format("<TR ALIGN=RIGHT><TD>%d<TD><a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)<TD>" +
                            "<a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)<TD>" +
                            "<a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)<TD><a href=%s>%s</a> (%d)", i+1,
                    catcher.getHref(), catcher.getName(), catcher.getDrs(), first.getHref(), first.getName(), first.getDrs(),
                    second.getHref(), second.getName(), second.getDrs(), ss_p.getHref(), ss_p.getName(), ss_p.getDrs(),
                    third.getHref(), third.getName(), third.getDrs(), lf_p.getHref(), lf_p.getName(), lf_p.getDrs(),
                    cf_p.getHref(), cf_p.getName(), cf_p.getDrs(), rf_p.getHref(), rf_p.getName(), rf_p.getDrs());
        }
        pw.println("</TABLE>");
        pw.println("</div>");

        Player player1 = didiVxander.get(0);
        Player player2 = didiVxander.get(1);
        pw.println("<div>");
        pw.println("<caption><b>Xander Bogarts vs. Didi Gregorius</b></caption>");
        pw.println("<TABLE BORDER><TR><TH><TH>" + player1.getName() + "<TH>" + player2.getName() + "</TR>");
        pw.format("<TR ALIGN=RIGHT><TD><strong>bWAR</strong><TD>%.1f<TD>%.1f", player1.getWar(), player2.getWar());
        pw.format("<TR ALIGN=RIGHT><TD><strong>fWAR</strong><TD>%.1f<TD>%.1f", player1.getFwar(), player2.getFwar());
        pw.format("<TR ALIGN=RIGHT><TD><strong>G</strong><TD>%d<TD>%d", player1.getG(), player2.getG());
        pw.format("<TR ALIGN=RIGHT><TD><strong>H</strong><TD>%d<TD>%d", player1.getH(), player2.getH());
        pw.format("<TR ALIGN=RIGHT><TD><strong>R</strong><TD>%d<TD>%d", player1.getR(), player2.getR());
        pw.format("<TR ALIGN=RIGHT><TD><strong>HR</strong><TD>%d<TD>%d", player1.getHr(), player2.getHr());
        pw.format("<TR ALIGN=RIGHT><TD><strong>RBI</strong><TD>%d<TD>%d", player1.getRbi(), player2.getRbi());
        pw.format("<TR ALIGN=RIGHT><TD><strong>SB</strong><TD>%d<TD>%d", player1.getSb(), player2.getSb());
        pw.format("<TR ALIGN=RIGHT><TD><strong>AVG</strong><TD>%.3f<TD>%.3f", player1.getAvg(), player2.getAvg());
        pw.format("<TR ALIGN=RIGHT><TD><strong>OBP</strong><TD>%.3f<TD>%.3f", player1.getObp(), player2.getObp());
        pw.format("<TR ALIGN=RIGHT><TD><strong>SLG</strong><TD>%.3f<TD>%.3f", player1.getSlg(), player2.getSlg());
        pw.format("<TR ALIGN=RIGHT><TD><strong>OPS</strong><TD>%.3f<TD>%.3f", player1.getOps(), player2.getOps());
        pw.format("<TR ALIGN=RIGHT><TD><strong>DRS</strong><TD>%d<TD>%d", player1.getDrs(), player2.getDrs());

        pw.println("</TABLE>");
        pw.println("</div>");
        pw.println("</HTML>");
        pw.close();
    }
}