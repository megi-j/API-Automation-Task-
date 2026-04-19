package ge.tbc.steps.api;

import ge.tbc.data.Constants;
import ge.tbc.models.ForwardRate;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import ge.tbc.models.TreasuryRatesResponse;
import ge.tbc.models.TreasuryRateCurrency;
import org.testng.Assert;

import static org.hamcrest.Matchers.*;

public class TreasuryRatesApiSteps {
    private Response response;
    private TreasuryRatesResponse treasuryRatesResponse;

    public TreasuryRatesApiSteps getTreasuryRatesInfo(){
        response = RestAssured.given()
                .baseUri(Constants.TREASURY_RATE_URL)
                .accept(ContentType.JSON)
                .when()
                .get("/api/v1/forwardRates/getForwardRates?locale=ka-GE");
        return this;
    }

    //ამ მეთოდში ორი მნიშვნელოვანი რამ ხდება: json გადაიქვა java pojo-ებად,ასევე ამოწმებს,
    //რომ bid და ask forward rate‑ები ლოგიკურად სწორია და ნულზე მეტია
    public TreasuryRatesApiSteps deserializeTreasuryRates() {
        treasuryRatesResponse  =
                response.as(TreasuryRatesResponse.class);

        List<ForwardRate> usdRates =
                treasuryRatesResponse.getRates().stream()//აბრუნებს ყველა ვალუტის list‑ს (USD,EUR,GBP..)და გადააქცევს list stream-ად რომ შევძლო filter
                        .filter(r -> r.getIso().equals("USD")) //დამიტოვე მხოლოდ ის currency, რომლის: iso = "USD"
                        .findFirst()//იღებს პირველ USD ობიექტს
                        .orElseThrow() //თუ USD საერთოდ არ არსებობს:ტესტი მყისიერად ჩაიჭრება
                        .getForwardRates();//USD currency‑დან იღებ: მის ყველა forward rate‑ს

        usdRates.forEach(rate -> {
            Assert.assertTrue(rate.getBidForwardRate() > 0,
                    "Bid rate must be > 0");
            Assert.assertTrue(rate.getAskForwardRate() > 0,
                    "Ask rate must be > 0");
        });
        return this;
    }
    //ეს მეთოდი ამოწმებს რომ აუცილებელი ველები არსებობს და ცარიელი არ არის
    public TreasuryRatesApiSteps assertField(){
        response.then()
                .assertThat()
                .statusCode(200)
                .body("rates.forwardRates.iso1", everyItem(not(emptyOrNullString())))
                .body("rates.forwardRates.iso2", everyItem(not(emptyOrNullString())))
                .body("rates.forwardRates.period", everyItem(not(emptyOrNullString())));
        return this;
    }
    //ჯერ ვფილტრავ rates კოლექციას USD ვალუტაზე (iso = "USD"), შემდეგ ამ ვალუტის forwardRates სიიდან ვიღებ პირველ forward rate‑ს, რადგან ყველა forward rate ერთსა და იმავე currency pair‑ს ეხება.
    //ამ forward rate‑დან ვიღებ iso1 და iso2 მნიშვნელობებს და ვაწყობ currency pair‑ს USD/GEL ფორმატში, რომელსაც შემდეგ UI‑ზე გამოტანილ სათაურს ვადარებ.
    public String getApiCurrencyPair() {
        ForwardRate firstUsdRate =
                treasuryRatesResponse.getRates().stream()
                        .filter(r -> r.getIso().equals("USD"))
                        .findFirst()
                        .orElseThrow(() ->
                                new AssertionError("USD currency not found"))
                        .getForwardRates()
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new AssertionError("No forward rates for USD"));

        return firstUsdRate.getIso1() + "/" + firstUsdRate.getIso2();
    }
    //ეს მეთოდი არსებობს იმისთვის, რომ API‑დან მიღებული USD forward rate‑ები გადავცეთ UI comparison‑ს
    public List<ForwardRate> getUsdRates() {
        return treasuryRatesResponse.getRates().stream()
                .filter(r -> r.getIso().equals("USD"))
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError("USD rates not found in API response"))
                .getForwardRates();
    }
}
