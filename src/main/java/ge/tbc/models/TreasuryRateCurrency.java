package ge.tbc.models;

import java.util.List;

public class TreasuryRateCurrency {
    private String iso;
    private List<ForwardRate> forwardRates;

    public String getIso() {
        return iso;
    }
    public List<ForwardRate> getForwardRates() {
        return forwardRates;
    }
}
