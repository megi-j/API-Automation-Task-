package ge.tbc.steps.api;

import ge.tbc.data.Constants;
import ge.tbc.models.ForwardRate;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;
import ge.tbc.models.TreasuryRatesResponse;
import ge.tbc.models.TreasuryRateCurrency;
import ge.tbc.models.ForwardRate;
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
    public TreasuryRatesApiSteps deserializeTreasuryRates() {
        TreasuryRatesResponse responsePojo =
                response.as(TreasuryRatesResponse.class);

        List<ForwardRate> usdRates =
                responsePojo.getRates().stream()
                        .filter(r -> r.getIso().equals("USD"))
                        .findFirst()
                        .orElseThrow()
                        .getForwardRates();

        usdRates.forEach(rate -> {
            Assert.assertTrue(rate.getBidForwardRate() > 0,
                    "Bid rate must be > 0");
            Assert.assertTrue(rate.getAskForwardRate() > 0,
                    "Ask rate must be > 0");
        });
        return this;
    }
    public TreasuryRatesApiSteps assertField(){
        response.then()
                .assertThat()
                .statusCode(200)
                .body("rates.forwardRates.iso1", everyItem(not(emptyOrNullString())))
                .body("rates.forwardRates.iso2", everyItem(not(emptyOrNullString())))
                .body("rates.forwardRates.period", everyItem(not(emptyOrNullString())));
        return this;
    }
    public String getApiCurrencyPair() {
        TreasuryRatesResponse responsePojo =
                response.as(TreasuryRatesResponse.class);
        TreasuryRateCurrency usdCurrency =
                responsePojo.getRates().stream()
                        .filter(r -> r.getIso().equals("USD"))
                        .findFirst()
                        .orElseThrow(() ->
                                new AssertionError("USD currency not found in API response"));
        return "USD/GEL";
    }
}
