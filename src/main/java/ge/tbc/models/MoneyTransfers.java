package ge.tbc.models;

import java.util.List;

public class MoneyTransfers {
    private String mtSystem;
    private String name;
    private String imageUrl;
    private List<String> currencies;

    public String getMtSystem() {
        return mtSystem;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getCurrencies() {
        return currencies;
    }
}
