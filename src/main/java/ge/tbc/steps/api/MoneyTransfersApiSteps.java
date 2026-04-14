package ge.tbc.steps.api;

import ge.tbc.data.Constants;
import ge.tbc.models.MoneyTransfers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;

public class MoneyTransfersApiSteps {
    private Response response;
    private List<MoneyTransfers> moneyTransfers;

    public MoneyTransfersApiSteps getMoneyTransfersSystem(){
        response = RestAssured.given()
                .baseUri(Constants.BASE_URL)
                .accept(ContentType.JSON)
                .when()
                .get("/api/v1/moneyTransfer/systems?locale=ka-GE");
        return this;
    }
    public MoneyTransfersApiSteps deserializeTransfersSystem(){
        this.moneyTransfers = Arrays.asList(response.as(MoneyTransfers[].class));
        return this;

    }
    public List<String> getTransferSystemsNames(){
        return moneyTransfers.stream()
                .map(MoneyTransfers::getName)
                .collect(Collectors.toList());

    }
    public MoneyTransfersApiSteps assertField(){
        response.then()
                .assertThat()
                .statusCode(200)
                .body("mtSystem", everyItem(not(emptyOrNullString())))
                .body("name", everyItem(not(emptyOrNullString())))
                .body("imageUrl", everyItem(not(emptyOrNullString())))
                .body("currencies", everyItem(not(emptyOrNullString())));
        return this;
    }
}
