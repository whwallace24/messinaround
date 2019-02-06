public class People {
    String href;
    String name;
    String team;
    String pos;
    int games;
    double war;
    double fwar;
    String awards;

    People(String href, String name, String team, String pos, int games, double war, double fwar, String awards) {
        this.href = href;
        this.name = name;
        this.team = team;
        this.pos = pos;
        this.games = games;
        this.war = war;
        this.fwar = fwar;
        this.awards = awards;
    }

    public String getHref() {return this.href;}
    public String getName() {return this.name;}
    public String getTeam() {return this.team;}
    public String getPos() {return this.pos;}
    public int getG() {return this.games;}
    public double getWar() {return this.war;}
    public double getFwar() {return this.fwar;}
    public String getAwards() {return awards;}
}
