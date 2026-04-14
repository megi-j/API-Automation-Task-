package ge.tbc.steps.ui;

import com.microsoft.playwright.Page;
import ge.tbc.pages.MoneyTransfersSystemsPage;
import org.testng.Assert;

import java.util.List;

public class MoneyTransfersSteps {
    private final MoneyTransfersSystemsPage moneyTransfersSystemsPage;
    private List<String> uiTransfersSystemsNames;

    public MoneyTransfersSteps(Page page){
        this.moneyTransfersSystemsPage = new MoneyTransfersSystemsPage(page);
    }
    public MoneyTransfersSteps navigateToMoneyTransfersPage(){
        moneyTransfersSystemsPage.navigate();
        return this;
    }
    public MoneyTransfersSteps getMoneyTransfersSystems(){
        moneyTransfersSystemsPage.moneyTransferCards.first().waitFor();

        uiTransfersSystemsNames = moneyTransfersSystemsPage.moneyTransferCards.allInnerTexts()
                .stream()
                .map(text -> text.split("\\n")[0].trim())
                .collect(java.util.stream.Collectors.toList());

        return this;
    }
    public MoneyTransfersSteps assertTransfersSystemsNamesMatch(List<String> transfersSystemsNames){
        Assert.assertEquals(uiTransfersSystemsNames, transfersSystemsNames);
        return this;

    }
}
