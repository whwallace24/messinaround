class Pitcher extends People {
    double ERA;
    double IP;
    int GS;
    int BB;
    int K;
    int SV;
    double FIP;
    int ERAPlus;
    String originalName;

    Pitcher(String href, String name, String team, String pos, int games, int gs, double war, double fwar, double ERA, double IP, int BB, int K, int SV,
            String awards, double FIP, int ERAPlus) {
        super(href, name, team, pos, games, war, fwar, awards);
        this.GS = gs;
        this.ERA = ERA;
        this.IP = IP;
        this.BB = BB;
        this.K = K;
        this.SV = SV;
        this.FIP = FIP;
        this.ERAPlus = ERAPlus;
        this.originalName = name;
    }

    Pitcher(String href, String name, String team, String pos, int games, int gs, double war, double fwar, double ERA, double IP, int BB, int K, int SV,
            String awards, double FIP, int ERAPlus, String originalName) {
        super(href, name, team, pos, games, war, fwar, awards);
        this.GS = gs;
        this.ERA = ERA;
        this.IP = IP;
        this.BB = BB;
        this.K = K;
        this.SV = SV;
        this.FIP = FIP;
        this.ERAPlus = ERAPlus;
        this.originalName = originalName;
    }

    Pitcher(String href, String name, String team, String pos, double war, double fwar,
            String awards) {
        super(href, name, team, pos, 0, war, fwar, awards);
        this.GS = 0;
        this.ERA = 0.0;
        this.IP = 0.0;
        this.BB = 0;
        this.K = 0;
        this.SV = 0;
        this.FIP = 0;
        this.ERAPlus = 0;
        this.originalName = "";
    }

    public double getERA() {return this.ERA;}
    public double getIP() {return this.IP;}
    public int getGS() {return this.GS;}
    public int getBB() {return this.BB;}
    public int getK() {return this.K;}
    public int getSV() {return this.SV;}
    public double getFIP() {return this.FIP;}
    public String getOriginalName() {return this.originalName;}
    public int getERAPlus() {return this.ERAPlus;}

    public double KtoBB() {
        double K = this.K;
        double BB = this.BB;
        return K/BB;
    }

    public double Kper9() {
        String IP = Double.toString(this.IP);
        String[] inningsSplit = IP.split("\\.");
        int outs = Integer.parseInt(inningsSplit[1]);
        double fraction = outs/3;
        double innings = Integer.parseInt(inningsSplit[0]) + fraction;
        double kToIP = this.K/innings;
        return kToIP * 9;
    }
}
