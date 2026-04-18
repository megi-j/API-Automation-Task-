package ge.tbc.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.data.Constants;


public class TreasuryRatesPage {
    private final Page page;
    public final Locator usdGelTitle;
    public final Locator usdGelRows ;

    public TreasuryRatesPage(Page page){
        this.page = page;
        usdGelTitle=page.locator("span.business-treasury-product-table__title",new Page.LocatorOptions().setHasText("USD"));
        usdGelRows = usdGelTitle.locator("div.ag-center-cols-container div[role='row']");
    }

    public void navigate(){
        page.navigate(Constants.TREASURY_PRODUCT_URL);
    }
}
