package ge.tbc.models;

import java.util.List;
//API response იერარქიულია, ამიტომ POJO კლასები დავყავი იმავე სტრუქტურის მიხედვით.
public class TreasuryRatesResponse {
    private List<TreasuryRateCurrency> rates;
    private String updateDate;

    public List<TreasuryRateCurrency> getRates() {
        return rates;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
