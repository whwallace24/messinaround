class Player extends People {
    int hits;
    int ab;
    int runs;
    int hr;
    int rbi;
    int sb;
    int bb;
    int tb;
    int pa;
    int hbp;
    double avg;
    double obp;
    double slg;
    double ops;
    int drs;
    String originalName;
//    double avg_2017;
//    double obp_2017;
//    double slg_2017;
//    double ops_2017;

    Player(String href, String name, String team, String pos, int games, double war, double fwar, int hits, int ab, int runs, int hr, int rbi, int sb, int bb, int tb,
            int pa, int hbp, double avg, double obp, double slg, String awards, int drs) { //double avg_2017, double obp_2017, double slg_2017) {
        super(href, name, team, pos, games, war, fwar, awards);
        this.hits = hits;
        this.ab = ab;
        this.runs = runs;
        this.hr = hr;
        this.rbi = rbi;
        this.sb = sb;
        this.bb = bb;
        this.tb = tb;
        this.pa = pa;
        this.hbp = hbp;
        this.avg = avg;
        this.obp = obp;
        this.slg = slg;
        this.ops = obp + slg;
        this.drs = drs;
        this.originalName = name;
//        this.avg_2017 = avg_2017;
//        this.obp_2017 = obp_2017;
//        this.slg_2017 = slg_2017;
//        this.ops_2017 = obp_2017 + slg_2017;
    }
    Player(String href, String name, String team, String pos, int games, double war, double fwar, int hits, int ab, int runs, int hr, int rbi, int sb, int bb, int tb,
           int pa, int hbp, double avg, double obp, double slg, String awards, int drs, String originalName) { //double avg_2017, double obp_2017, double slg_2017) {
        super(href, name, team, pos, games, war, fwar, awards);
        this.hits = hits;
        this.ab = ab;
        this.runs = runs;
        this.hr = hr;
        this.rbi = rbi;
        this.sb = sb;
        this.bb = bb;
        this.tb = tb;
        this.pa = pa;
        this.hbp = hbp;
        this.avg = avg;
        this.obp = obp;
        this.slg = slg;
        this.ops = obp + slg;
        this.drs = drs;
        this.originalName = originalName;
//        this.avg_2017 = avg_2017;
//        this.obp_2017 = obp_2017;
//        this.slg_2017 = slg_2017;
//        this.ops_2017 = obp_2017 + slg_2017;
    }


    public int getH() {return this.hits;}
    public int getAb() {return this.ab;}
    public int getR() {return this.runs;}
    public int getHr() {return this.hr;}
    public int getRbi() {return this.rbi;}
    public int getSb() {return this.sb;}
    public int getBb() {return this.bb;}
    public int getTb() {return this.tb;}
    public int getPa() {return this.pa;}
    public int getHbp() {return this.hbp;}
    public double getAvg() {return this.avg;}
    public double getObp() {return this.obp;}
    public double getSlg() {return this.slg;}
    public double getOps() {return this.ops;}
    public int getDrs() {return this.drs;}
    public String getOriginalName() {return this.originalName;}
//    public double getLastAvg() {return this.avg_2017;}
//    public double getLastObp() {return this.obp_2017;}
//    public double getLastSlg() {return this.slg_2017;}
//    public double getLastOps() {return this.ops_2017;}
}
