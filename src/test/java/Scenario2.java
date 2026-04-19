import ge.tbc.models.ForwardRate;
import ge.tbc.steps.api.TreasuryRatesApiSteps;
import ge.tbc.steps.ui.TreasuryRatesSteps;
import ge.tbc.utils.BaseTest;
import org.testng.annotations.Test;

import java.util.List;

//ტესტი ამოწმებს Treasury forward rates‑ის სინქრონს API‑სა და UI‑ს შორის.
// ჯერ ვალიდაციას ვუკეთებ API response‑ის სტრუქტურასა და numeric ველებს,
// შემდეგ API‑დან დინამიკურად ვიღებ USD/GEL currency pair‑სა და forward rate‑ებს და ბოლოს UI‑ზე ვამოწმებ,
// რომ სათაური და ყველა period‑ის bid/ask მნიშვნელობა ზუსტად ემთხვევა backend მონაცემებს.
public class Scenario2 extends BaseTest {
    @Test
    public void treasuryRatesTest(){
        TreasuryRatesApiSteps treasuryRatesApiSteps = new TreasuryRatesApiSteps();
        treasuryRatesApiSteps.getTreasuryRatesInfo()
                .assertField()
                .deserializeTreasuryRates();

        String apiCurrencyPair = treasuryRatesApiSteps.getApiCurrencyPair();

        List<ForwardRate> apiUsdRates = treasuryRatesApiSteps.getUsdRates();

        TreasuryRatesSteps treasuryRatesSteps = new TreasuryRatesSteps(page);
        treasuryRatesSteps.navigateToTreasuryPage()
                .assertCurrencyPairMatches(apiCurrencyPair)
                .assertRatesMatch(apiUsdRates);

    }
}
