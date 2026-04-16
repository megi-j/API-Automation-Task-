import ge.tbc.steps.api.TreasuryRatesApiSteps;
import ge.tbc.utils.BaseTest;
import org.testng.annotations.Test;

public class Scenario2 extends BaseTest {
    @Test
    public void treasuryRatesTest(){
        TreasuryRatesApiSteps treasuryRatesApiSteps = new TreasuryRatesApiSteps();
        treasuryRatesApiSteps.getTreasuryRatesInfo()
                .assertField()
                .deserializeTreasuryRates();
    }
}
