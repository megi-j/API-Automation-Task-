package ge.tbc.models;

import java.util.List;

public class TreasuryRatesResponse {
    private List<TreasuryRateCurrency> rates;
    private String updateDate;

    public List<TreasuryRateCurrency> getRates() {
        return rates;
    }

}
