import ge.tbc.steps.api.MoneyTransfersApiSteps;
import ge.tbc.steps.ui.MoneyTransfersSteps;
import ge.tbc.utils.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class Scenario1 extends BaseTest {

    @Test
    public void monayTransfersTest(){
    MoneyTransfersApiSteps moneyTransfersApiSteps = new MoneyTransfersApiSteps();
    List<String> expectedApiNames = moneyTransfersApiSteps.getMoneyTransfersSystem()
            .assertField()
            .deserializeTransfersSystem()
            .getTransferSystemsNames();

    List<List<String>> expectedApiCurrencies = moneyTransfersApiSteps
            .getTransferSystemsCurrencies();

    MoneyTransfersSteps moneyTransfersSteps = new MoneyTransfersSteps(page);
        moneyTransfersSteps.navigateToMoneyTransfersPage()    //გახსენი Money Transfers გვერდი
                .getMoneyTransfersSystemsNames()                     //წაიკითხე სისტემების სახელები UI-დან
                .getMoneyTransfersSystemCurrencies()          //წაიკითხე ვალუტები UI-დან
                .assertTransfersSystemsNamesMatch(expectedApiNames)    //შეადარე UI სახელები API-ს
                .assertCurrenciesMatch(expectedApiCurrencies);       //შეადარე UI ვალუტები API-ს
    }
}
