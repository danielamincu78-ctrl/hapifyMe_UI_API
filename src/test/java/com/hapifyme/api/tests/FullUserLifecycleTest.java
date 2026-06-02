package com.hapifyme.api.tests;

import com.hapifyme.api.models.*;
import com.hapifyme.api.utils.ApiPoller;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class FullUserLifecycleTest {
    @Test
    public void testFullUserLifecycle() {
        String baseUri = "https://apps.qualiadept.eu/hapifyme/api";

        // --- ETAPA 1: REGISTER (Obținem API Key și User ID) ---
        System.out.println("Incepe ETAPA 1: Create (Înregistrare) ");

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
        System.out.println("User creat cu ID: " + registerResponse.getUserId());
        String extractedUsername = registerResponse.getUsername();

        // Pregătire date (emailul utilizatorului înregistrat)
        String userEmail = uniqueEmail;
        String apiKey = extractedApiKey;

        //Așteptăm statusul "success" folosind ApiPoller pe endpoint-ul valid
        String statusUrl = "https://apps.qualiadept.eu/hapifyme/api/user/retrieve_token.php?username_or_email=" + userEmail;
        ApiPoller.pollForStatus(statusUrl, "success", apiKey);

        System.out.println("Register Done. API Key: " + extractedApiKey + " . Username is: " + extractedUsername);
        System.out.println("Final ETAPA 1");
        System.out.println(" ");

        System.out.println("Incepe ETAPA 2: confirmare email");
        System.out.println("●\tConfirm (Activare Cont)");

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
        System.out.println("2. Email confirmed successfully.");
        System.out.println("Final ETAPA 2");
        System.out.println(" ");

        // Etapa 3. Login ( extrage Bearer Token)
        System.out.println("Incepe ETAPA 3: login");
        System.out.println("●\tLogin (Obținerea Bearer Token)");
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
        System.out.println("3. Login Done. Bearer Token: " + bearerToken);
        System.out.println("Final ETAPA 3");
        System.out.println(" ");

        //4.Get profile (Verificare profil: datele de la autentificarecorespund cu cele din profil)
        System.out.println("Incepe ETAPA 4: get profile");
        System.out.println("●\tRead & Validate (Verificare Profil)");
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
        System.out.println("Profile response: " + profileResponse);

        // Verificare profil: datele de la autentificare(email, nume)  corespund cu cele din profil

        if (!profileResponse.getUser().getEmail().equals(uniqueEmail) ||
                !profileResponse.getUser().getFirstName().equals(firstName) ||
                !profileResponse.getUser().getLastName().equals(lastName)) {
            throw new RuntimeException("Email mismatch");
        } else {
            System.out.println("Email User inregistrat: " + uniqueEmail);
            System.out.println("Nume User inregistrat: " + firstName + " " + lastName);
            System.out.println("Email user din profil: " + profileResponse.getUser().getEmail());
            System.out.println("Nume user din profil: " + profileResponse.getUser().getFirstName() + " " + profileResponse.getUser().getLastName());
        }

        System.out.println("4. Done. Nume si email din profil sunt identice cu cele de la inregistrare.");
        System.out.println("Final ETAPA 4");
        System.out.println(" ");

        //5. Update profile
        System.out.println("Incepe ETAPA 5: update profile");
        System.out.println("●\tUpdate Profile (Modificare Profil)");
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
        System.out.println("5. Done. Profile updated.");
        System.out.println("Final ETAPA 5");
        System.out.println(" ");

        // 6: DELETE PROFILE (Cleanup - Folosind Bearer Token) ---
        System.out.println("Incepe ETAPA 6: delete profile");
        System.out.println("●\tDelete Profile (Stergere Profil)");
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

        System.out.println("6. Delete Done. User cleaned up.");
        System.out.println(" ");

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
        System.out.println("Status: " + extractedResponse.statusCode());
        System.out.println("Response: " + extractedResponse.getBody().asString());
        System.out.println("7. Negative Check Done. User not found.");
        System.out.println("Final ETAPA 7");
        System.out.println(" ");

    }
}


