package ge.tbc.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.data.Constants;

public class MoneyTransfersSystemsPage {
    private final Page page;
    public final Locator moneyTransferCards;
    public final Locator currencies;

    public MoneyTransfersSystemsPage(Page page){
        this.page = page;
        moneyTransferCards = page.locator("div.tbcx-pw-money-transfer-system-card"); //ყველა ბარათი
        currencies = moneyTransferCards.locator("span.tbcx-pw-card__caption");
    }
    public void navigate(){
        page.navigate(Constants.MONEY_TRANSFER_URL);
    }

}
