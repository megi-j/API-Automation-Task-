package ge.tbc.steps.ui;

import com.microsoft.playwright.Page;
import ge.tbc.pages.MoneyTransfersSystemsPage;
import org.testng.Assert;

import java.util.List;

public class MoneyTransfersSteps {
    private final MoneyTransfersSystemsPage moneyTransfersSystemsPage;

    private List<String> uiTransfersSystemsNames;
    private List<List<String>> uiTransfersSystemsCurrencies;

    public MoneyTransfersSteps(Page page){
        this.moneyTransfersSystemsPage = new MoneyTransfersSystemsPage(page);
    }
    public MoneyTransfersSteps navigateToMoneyTransfersPage(){
        moneyTransfersSystemsPage.navigate();
        return this;
    }
    public MoneyTransfersSteps getMoneyTransfersSystemsNames(){
        moneyTransfersSystemsPage.moneyTransferCards.first().waitFor();

        uiTransfersSystemsNames = moneyTransfersSystemsPage.moneyTransferCards.allInnerTexts()
                .stream()
                .map(text -> text.split("\\n")[0].trim()) //ბარათიდან მხოლოდ name-ის ამოღება
                .collect(java.util.stream.Collectors.toList());

        return this;
    }
    public MoneyTransfersSteps getMoneyTransfersSystemCurrencies(){
        uiTransfersSystemsCurrencies = moneyTransfersSystemsPage.moneyTransferCards
                .locator(moneyTransfersSystemsPage.currencies)
                .allInnerTexts()
                .stream()
                .map(text -> text
                        .replace("currency -", "")   // ვშლით prefix-ს
                        .trim()
                )
                .map(text -> java.util.Arrays.stream(text.split("/")) // split by "/"
                        .map(String::trim)
                        .map(String::toUpperCase)
                        .collect(java.util.stream.Collectors.toList())
                )
                .collect(java.util.stream.Collectors.toList());
        return this;
    }
    public MoneyTransfersSteps assertTransfersSystemsNamesMatch(List<String> transfersSystemsNames){
        Assert.assertTrue(uiTransfersSystemsNames.containsAll(transfersSystemsNames)); //UI=API
        return this;

    }
    public MoneyTransfersSteps assertCurrenciesMatch(List<List<String>> apiCurrencies){

        for (int i = 0; i < uiTransfersSystemsCurrencies.size(); i++) {
            List<String> uiCurrencies = uiTransfersSystemsCurrencies.get(i)
                    .stream()
                    .map(String::toUpperCase)
                    .toList();

            List<String> apiCurr = apiCurrencies.get(i)
                    .stream()
                    .map(String::toUpperCase)
                    .toList();

            Assert.assertTrue(uiCurrencies.containsAll(apiCurr),
                    "Currencies mismatch at index: " + i);
        }
        return this;
    }
}
