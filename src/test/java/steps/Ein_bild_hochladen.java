package steps;

import application.SpringStepBase;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Gegebensei;
import io.cucumber.java.de.Wenn;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;


public class Ein_bild_hochladen extends SpringStepBase {

    private ValidatableResponse validatableResponse;
    private File picture;

    @Gegebensei("ein hochzuladendes Bild")
    public void einHochzuladendesBild() throws FileNotFoundException {
        picture = ResourceUtils.getFile("classpath:pictures/test.png");
    }

    //TODO: Datenbank nach den Tests wieder löschen
    //TODO: Daten als Testdaten kennzeichnen
    //GGF nochmal nach der Version suchen bei der ein Komplexes Object zum upload verwendet wurde, kein
    // Multipart
    @Wenn("das Bild erfolgreich hochgeladen wurde")
    public void dasBildErfolgreichHochgeladenWurde() {
        RestAssured.baseURI = "http://localhost:8080/picture";
        File test = new File("/Users/sebmaster/Downloads/test.json");

        validatableResponse = given()
                .multiPart(picture)
                .multiPart(new MultiPartSpecBuilder(test).fileName("test.jpg")
                        .controlName("photo")
                        .mimeType("application/json")
                        .build())
                .contentType("multipart/form-data")
                .formParam("file", picture.getAbsolutePath())
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Dann("soll das Bild abrufbar sein")
    public void sollDasBildAbrufbarSein() throws IOException {
        String s = validatableResponse.extract().header("location").toString();
        ValidatableResponse validatableResponse = given().when().get(s).then().assertThat()
                .statusCode(HttpStatus.SC_OK);
        byte[] bytes = validatableResponse.extract().body().asByteArray();
        assertTrue(Arrays.equals(bytes, Files.readAllBytes(picture.toPath())));
    }
}
