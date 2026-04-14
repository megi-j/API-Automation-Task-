import ge.tbc.steps.api.MoneyTransfersApiSteps;
import ge.tbc.steps.ui.MoneyTransfersSteps;
import ge.tbc.utils.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class Scenario1 extends BaseTest {
//    MoneyTransfersSteps moneyTransfersSteps;
//    MoneyTransfersApiSteps moneyTransfersApiSteps;


//    public void setUp() {
//         moneyTransfersSteps = new MoneyTransfersSteps(page);
//        moneyTransfersApiSteps = new MoneyTransfersApiSteps();
//    }

    @Test
    public void test(){


    MoneyTransfersApiSteps moneyTransfersApiSteps = new MoneyTransfersApiSteps();
    List<String> expectedApiNames = moneyTransfersApiSteps.getMoneyTransfersSystem()
            .assertField()
            .deserializeTransfersSystem()
            .getTransferSystemsNames();


    MoneyTransfersSteps moneyTransfersSteps = new MoneyTransfersSteps(page);
    moneyTransfersSteps.navigateToMoneyTransfersPage()
            .getMoneyTransfersSystems()
            .assertTransfersSystemsNamesMatch(expectedApiNames);
    }
}
