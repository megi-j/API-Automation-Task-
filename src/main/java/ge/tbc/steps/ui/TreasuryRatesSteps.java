package ge.tbc.steps.ui;

import com.microsoft.playwright.Page;
import ge.tbc.pages.TreasuryRatesPage;
import org.testng.Assert;

import java.util.List;

public class TreasuryRatesSteps {
    private final TreasuryRatesPage treasuryRatesPage;

    public TreasuryRatesSteps (Page page){
        this.treasuryRatesPage = new TreasuryRatesPage(page);
    }
    public TreasuryRatesSteps navigateToTreasuryPage(){
        treasuryRatesPage.navigate();
        return this;
    }

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

}
