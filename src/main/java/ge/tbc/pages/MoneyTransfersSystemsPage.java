package ge.tbc.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.data.Constants;

public class MoneyTransfersSystemsPage {
    private final Page page;
    public final Locator moneyTransferCards;

    public MoneyTransfersSystemsPage(Page page){
        this.page = page;
        moneyTransferCards = page.locator("div.tbcx-pw-money-transfer-system-card");

    }
    public void navigate(){
        page.navigate(Constants.BLOG_URL);
    }

}
