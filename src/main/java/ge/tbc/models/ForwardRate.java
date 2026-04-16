package ge.tbc.models;

public class ForwardRate {
    private String iso1;
    private String iso2;
    private String period;
    private double bidForwardRate;
    private double askForwardRate;

    public String getIso1() {
        return iso1;
    }

    public String getIso2() {
        return iso2;
    }

    public String getPeriod() {
        return period;
    }

    public double getBidForwardRate() {
        return bidForwardRate;
    }

    public double getAskForwardRate() {
        return askForwardRate;
    }
}
