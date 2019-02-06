public class BattingTeam {
    String name;
    String href;
    double bwar;
    double fwar;
    int hits;
    int ab;
    int runs;
    int hr;
    int rbi;
    int sb;
    double avg;
    double obp;
    double slg;
    double ops;

    BattingTeam(String name, String href, double bwar, double fwar, int hits, int ab, int runs, int hr, int rbi, int sb, double avg,
                double obp, double slg, double ops) {
        this.name = name;
        this.href = href;
        this.bwar = bwar;
        this.fwar = fwar;
        this.hits = hits;
        this.ab = ab;
        this.runs = runs;
        this.hr = hr;
        this.rbi = rbi;
        this.sb = sb;
        this.avg = avg;
        this.obp = obp;
        this.slg = slg;
        this.ops = ops;
    }

    public String getHref() {return this.href;}
    public String getName() {return this.name;}
    public double getbWar() {return this.bwar;}
    public double getfWar() {return this.fwar;}
    public int getH() {return this.hits;}
    public int getAb() {return this.ab;}
    public int getR() {return this.runs;}
    public int getHr() {return this.hr;}
    public int getRbi() {return this.rbi;}
    public int getSb() {return this.sb;}
    public double getAvg() {return this.avg;}
    public double getObp() {return this.obp;}
    public double getSlg() {return this.slg;}
    public double getOps() {return this.ops;}
}
