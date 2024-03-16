package com.phonebook.RAtests;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpdateContactTests extends TestBase {

    String id;

    @BeforeMethod
    public void precondition() {
        ContactDto dto = ContactDto.builder()
                .name("Adam")
                .lastName("Kross")
                .email("adkro@gm.com")
                .phone("1234567890")
                .address("Koblenz")
                .description("goalkeeper")
                .build();

        String message = given()
                .contentType(ContentType.JSON)
                .header(AUTH, token)
                .body(dto)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");

        String[] split = message.split(": ");
        id = split[1];
    }

    @Test
    public void updateContactSuccessStatus200Test() {
//        Response response =
        given()
                .contentType(ContentType.JSON)
                .body(ContactDto.builder()
                        .id(id)
                        .name("Adam")
                        .lastName("Kross")
                        .email("adkro@gm.com")
                        .phone("1234567890")
                        .address("Hamburg")
                        .description("goalkeeper")
                        .build())
                .header(AUTH, token)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message",containsString("Contact was updated"));
//        System.out.println(response.getBody().asString());
    }

    @Test
    public void updateContactWithInvalidEmailError400Test() {
//        Response response =
        given()
                .contentType(ContentType.JSON)
                .body(ContactDto.builder()
                        .id(id)
                        .name("Adam")
                        .lastName("Kross")
                        .email("adkrogm.com")
                        .phone("1234567890")
                        .address("Hamburg")
                        .description("goalkeeper")
                        .build())
                .header(AUTH, token)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.email",containsString("must be a well-formed email address"));
//        System.out.println(response.getBody().asString());
    }

    @Test
    public void updateContactWithInvalidPhoneError400Test() {
//        Response response =
                given()
                .contentType(ContentType.JSON)
                .body(ContactDto.builder()
                        .id(id)
                        .name("Adam")
                        .lastName("Kross")
                        .email("adkro@gm.com")
                        .phone("1234567")
                        .address("Hamburg")
                        .description("goalkeeper")
                        .build())
                .header(AUTH, token)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.phone",containsString("Phone number must contain only digits! And length min 10, max 15!"));
//        System.out.println(response.getBody().asString());
    }

    @Test
    public void updateContactWithoutAuthError401Test() {
//        MessageDto dto =
                given()
                .contentType(ContentType.JSON)
                .body(ContactDto.builder()
                        .id(id)
                        .name("Adam")
                        .lastName("Kross")
                        .email("adkro@gm.com")
                        .phone("1234567890")
                        .address("Hamburg")
                        .description("goalkeeper")
                        .build())
                .header(AUTH, "sometoken")
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message",containsString("JWT strings must contain exactly 2 period characters. Found: 0"));
//                .extract().response().as(MessageDto.class);
//        System.out.println(dto.getMessage());
    }

    @Test
    public void updateNotExistedContactError404Test() {
        given()
                .contentType(ContentType.JSON)
                .body(ContactDto.builder()
                        .id("321")
                        .name("Adam")
                        .lastName("Kross")
                        .email("adkro@gm.com")
                        .phone("1234567890")
                        .address("Hamburg")
                        .description("goalkeeper")
                        .build())
                .header(AUTH, token)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(404);
//                .assertThat().body("message",containsString("Contact with id: 321 not found in your contacts!"));
    }

    @AfterMethod
    public void postCondition() {
        given()
                .header(AUTH, token)
                .when()
                .delete("contacts/" + id);
    }
}
