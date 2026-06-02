package com.hapifyme.api.tests;

import com.hapifyme.api.models.*;
import com.hapifyme.api.utils.ApiPoller;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class FullUserLifecycleTest {
    private static final Logger logger =
            LogManager.getLogger(FullUserLifecycleTest.class);

    @Test
    public void testFullUserLifecycle() {
        String baseUri = "https://apps.qualiadept.eu/hapifyme/api";

        // --- ETAPA 1: REGISTER (Obținem API Key și User ID) ---
        logger.info("===== ETAPA 1: Inresgistrare USER =====");

        String uniqueEmail = "chain_" + System.currentTimeMillis() + "@hapifyme.com";
        String password = "Pass1234@";
        String firstName = "Chain";
        String lastName = "User";

        RegisterRequest registerBody = new RegisterRequest(firstName, lastName, uniqueEmail, password);

        RegisterResponse registerResponse = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(registerBody)
                .log().body()
                .when()
                .post("/user/register.php")
                .then()
                .statusCode(201) // sau 200 depinde de API
                .extract().as(RegisterResponse.class);

        String extractedApiKey = registerResponse.getApiKey();
        int extractedUserId = Integer.parseInt(registerResponse.getUserId());

        logger.info("User creat cu ID: {}", registerResponse.getUserId());
        String extractedUsername = registerResponse.getUsername();

        // Pregătire date (emailul utilizatorului înregistrat)
        String userEmail = uniqueEmail;
        String apiKey = extractedApiKey;

        //Așteptăm statusul "success" folosind ApiPoller pe endpoint-ul valid
        String statusUrl = "https://apps.qualiadept.eu/hapifyme/api/user/retrieve_token.php?username_or_email=" + userEmail;
        ApiPoller.pollForStatus(statusUrl, "success", apiKey);

        logger.info("Register finalizat. Username: {}", extractedUsername);
        logger.debug("API Key: {}", extractedApiKey);
        logger.info("Final ETAPA 1");
        logger.info("====================  ");
        logger.info("");

        logger.info("===== ETAPA 2: confirmare email =====  ");
        logger.info("Activare Cont");
        logger.info("");

        // --- ETAPA 2: Confirm email (folosim token-ul utilizatorului) ---
        String extractedToken = registerResponse.getToken();

        given()
                .baseUri(baseUri)
                .queryParam("token", extractedToken)
                .contentType(ContentType.JSON)
                .when()
                .get("/user/confirm_email.php")
                .then()
                .log().body()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message", containsString("Email confirmed successfully. You can now log in"));
        logger.info("Email confirmat cu succes");
        logger.info("Final ETAPA 2");
        logger.info("====================  ");
        logger.info("");

        // Etapa 3. Login ( extrage Bearer Token)
        logger.info("===== ETAPA 3: login ===== ");
        logger.info("Obtinerea Bearer Token");
        logger.info("");

        LoginRequest loginBody = new LoginRequest(extractedUsername, password); // SAU folosim username din registerResponse

        LoginResponse loginResponse = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post("/user/login.php")
                .then()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponse.class);

        String bearerToken = loginResponse.getToken();
        logger.info("Bearer Token: {}", bearerToken);
        logger.info("Final ETAPA 3");
        logger.info("====================  ");
        logger.info("");

        //4.Get profile (Verificare profil: datele de la autentificarecorespund cu cele din profil)
        logger.info("===== ETAPA 4: get profile =====");
        logger.info("Verificare Profil");
        logger.info("");

        ProfileResponse profileResponse = given()
                .baseUri(baseUri)
                .header("Authorization", extractedApiKey) // Folosim API Key
                .queryParam("user_id", extractedUserId) // Folosim Bearer Token
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .get("/user/get_profile.php")
                .then()
                .statusCode(200)
                .extract().as(ProfileResponse.class);
        logger.info("Profile response: {}", profileResponse);

        // Verificare profil: datele de la autentificare(email, nume)  corespund cu cele din profil

        if (!profileResponse.getUser().getEmail().equals(uniqueEmail) ||
                !profileResponse.getUser().getFirstName().equals(firstName) ||
                !profileResponse.getUser().getLastName().equals(lastName)) {
            throw new RuntimeException("Email mismatch");
        } else {
            logger.info("Email User inregistrat: ", uniqueEmail);
            logger.info("Nume User inregistrat: ", firstName + " " + lastName);
            logger.info(" ");
            logger.info("Email user din profil: ",profileResponse.getUser().getEmail());
            logger.info("Nume user din profil: ",profileResponse.getUser().getFirstName() + " " + profileResponse.getUser().getLastName() );
        }
        logger.info("4. Done. Nume si email din profil sunt identice cu cele de la inregistrare.");
        logger.info("Final ETAPA 4");
        logger.info("");

        //5. Update profile
        logger.info("===== ETAPA 5: update profile =====");
        logger.info("Modificare Profil");
        logger.info("====================");

        UpdateProfileRequest updateBody = new UpdateProfileRequest(extractedUserId, "UpdatedName",
                profileResponse.getUser().getLastName(), profileResponse.getUser().getEmail(),
                profileResponse.getUser().getProfilePic());

        given()
                .baseUri(baseUri)
                .header("Authorization", extractedApiKey) // Folosim API Key
                .contentType(ContentType.JSON)
                .log().body()
                .body(updateBody)
                .when()
                .put("/user/update_profile.php")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message", containsString("updated"));
        logger.info("5. Done. Profile updated.");
        logger.info("Final ETAPA 5");
        logger.info("====================");

        // 6: DELETE PROFILE (Cleanup - Folosind Bearer Token) ---
        logger.info("===== ETAPA 6: delete profile =====");
        logger.info("Stergere Profil");
        logger.info("");

        given()
                .baseUri(baseUri)
                .header("Authorization", "Bearer " + bearerToken) // Folosim Bearer Token
                .contentType(ContentType.JSON)
                .when()
                .delete("/user/delete_profile.php")
                .then()
                .statusCode(200)
                .log().status()
                .body("status", equalTo("success"))
                .body("message", containsString("deleted"));

        logger.info("Delete Done. User cleaned up");
        logger.info("====================");

        //Negative Check: Încearcă să citești profilul din nou (GET) și validează că primești 404 sau 401 (utilizatorul nu mai există).
        Response extractedResponse = given()
                .baseUri(baseUri)
                .header("Authorization", extractedApiKey) // Folosim API Key
                .queryParam("user_id", extractedUserId) // Folosim Bearer Token
                .contentType(ContentType.JSON)
                .when()
                .get("/user/get_profile.php")
                .then()
                .log().status()
                .statusCode(200)
                .body("status", equalTo("error"))
                .body("message", containsString("User not found."))
                .extract().response();
        logger.info("Status: ", extractedResponse.statusCode() );
        logger.info("Response: ",extractedResponse.getBody().asString() );
        logger.info("Negative Check Done. User not found.");
        logger.info("Final ETAPA 6");
        logger.info("====================");
    }
}


