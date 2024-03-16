package com.phonebook.RAtests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static final String token =
            "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoib2theUBnbS5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcxMTAzNDA2MCwiaWF0IjoxNzEwNDM0MDYwfQ.3HZoAuUfRS7sbdXEgWZ-U72rxZ7HCubcnISYEr7pFgE";

    public static final String AUTH = "Authorization";

    @BeforeMethod
    public void init() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }
}
