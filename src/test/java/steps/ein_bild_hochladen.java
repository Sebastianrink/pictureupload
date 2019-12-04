package steps;

import application.SpringStepBase;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Gegebensei;
import io.cucumber.java.de.Wenn;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import java.io.File;
import static io.restassured.RestAssured.given;


public class ein_bild_hochladen extends SpringStepBase {

    private ValidatableResponse validatableResponse;
    private File testUploadFile;

    @Gegebensei("ein hochzuladendes Bild")
    public void einHochzuladendesBild() {
        //TODO
        testUploadFile = new File("/Users/sebmaster/Projects/backend/src/test/resources/pictures/test.png");
    }

    @Wenn("das Bild erfolgreich hochgeladen wurde")
    public void dasBildErfolgreichHochgeladenWurde() {
        RestAssured.baseURI ="http://localhost:8080/picture";
        given().when().post("/picture").then().assertThat().statusCode(HttpStatus.SC_OK);
        validatableResponse = given()
                .multiPart(testUploadFile)
                .formParam("form",testUploadFile.getAbsolutePath())
                .when()
                .post("/pictures")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Dann("soll das Bild abrufbar sein")
    public void sollDasBildAbrufbarSein() {
        String s = validatableResponse.extract().body().toString();
        given().when().get("5de7ee6b0dc68e44824fa5d").then().assertThat().statusCode(HttpStatus.SC_OK);
    }
}
