package ge.tbc.steps.ui;

import com.microsoft.playwright.Page;
import ge.tbc.models.ForwardRate;
import ge.tbc.pages.TreasuryRatesPage;
import org.testng.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TreasuryRatesSteps {
    private final TreasuryRatesPage treasuryRatesPage;

    public TreasuryRatesSteps (Page page){
        this.treasuryRatesPage = new TreasuryRatesPage(page);
    }
    public TreasuryRatesSteps navigateToTreasuryPage(){
        treasuryRatesPage.navigate();
        return this;
    }
//ეს მეთოდი ამოწმებს, რომ UI‑ზე ნაჩვენები currency pair ემთხვევა API response‑დან მიღებულ currency pair‑ს.
    public TreasuryRatesSteps assertCurrencyPairMatches(String apiCurrencyPair) {
        String uiCurrencyPair = treasuryRatesPage.usdGelTitle
                        .innerText()
                        .replaceAll("\\s+", "");
        Assert.assertEquals(
                uiCurrencyPair,
                apiCurrencyPair,
                "UI currency pair does not match API currency pair"
        );
        return this;
    }
//მეთოდი კითხულობს USD/GEL ცხრილის ყველა row‑ს UI‑დან, ახდენს ტექსტის ნორმალიზაციას და პარსავს თითოეულ row‑ს period,
// bid და ask მნიშვნელობებად. საბოლოოდ ეს მონაცემები ინახება Map‑ში, სადაც period არის key, ხოლო bid და ask — value,
// რაც საშუალებას მაძლევს API‑დან მიღებულ forward rate‑ებს სტაბილურად და index‑ისგან დამოუკიდებლად შევადარო UI‑ს.
    public Map<String, double[]> getUiRatesByPeriod() {
        Map<String, double[]> uiRates = new HashMap<>();
        List<String> rows = treasuryRatesPage.usdGelRows.allInnerTexts();
        for (String row : rows) {
            String normalized = row.replaceAll("\\s+", " ").trim();
            // ვპოულობთ ყველა რიცხვს (rates)
            String[] parts = normalized.split(" ");
            if (parts.length < 3) {
                continue;
            }
            // bid და ask ყოველთვის ბოლო ორი რიცხვია
            double bid = Double.parseDouble(parts[parts.length - 2]);
            double ask = Double.parseDouble(parts[parts.length - 1]);

            // period არის ყველაფერი bid‑მდე
            String period = String.join(
                    " ",
                    Arrays.copyOfRange(parts, 0, parts.length - 2)
            ).replaceAll("\\s+", " ").trim();

            uiRates.put(period, new double[]{bid, ask});
        }
        return uiRates;
    }
    //ეს მეთოდი ერთدროულად ამოწმებს:
    // UI‑ზე ნაჩვენები ყველა period არსებობს API‑ში
    // bid მნიშვნელობები ემთხვევა
    // ask მნიშვნელობები ემთხვევა
    // UI და API ერთსა და იმავე ფინანსურ მონაცემს აჩვენებს
    public TreasuryRatesSteps assertRatesMatch(List<ForwardRate> apiRates) {
        Map<String, double[]> uiRatesByPeriod = getUiRatesByPeriod();
        for (Map.Entry<String, double[]> uiEntry : uiRatesByPeriod.entrySet()) {
            String uiPeriod = uiEntry.getKey();
            double[] uiRates = uiEntry.getValue();

            ForwardRate apiRate = apiRates.stream()
                    .filter(r -> r.getPeriod()
                            .replaceAll("\\s+", " ")
                            .trim()
                            .equals(uiPeriod))
                    .findFirst()
                    .orElseThrow(() ->
                            new AssertionError("API does not contain period shown on UI: " + uiPeriod));

            Assert.assertEquals(
                    uiRates[0],
                    apiRate.getBidForwardRate(),
                    0.01,
                    "Bid rate mismatch for period: " + uiPeriod
            );

            Assert.assertEquals(
                    uiRates[1],
                    apiRate.getAskForwardRate(),
                    0.01,
                    "Ask rate mismatch for period: " + uiPeriod
            );
        }
        return this;
    }
}
