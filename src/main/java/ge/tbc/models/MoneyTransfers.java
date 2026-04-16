package ge.tbc.models;

import java.util.List;
//POJO კლასი არის მოდელი, რომელიც აღწერს API response-ს
public class MoneyTransfers {
    private String name;
    private List<String> currencies;


    public String getName() {
        return name;
    }

    public List<String> getCurrencies() {
        return currencies;
    }
}
