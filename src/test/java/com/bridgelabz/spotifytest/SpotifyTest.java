package com.bridgelabz.spotifytest;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class SpotifyTest {
    public static String token = "";
    public String User_ID = "";
    public String playlist_id = "";
    public final String JSON = "application/json";

    @BeforeMethod
    public void get() {
        token = "Bearer BQAGN1LDNdL05Fi7iypoNHoBSA-L0kkPQ6EXv1S3cEYcmn1SlPdLX6dWMkgZNHe-srmeQV4F7FIE-Flm1ZlX6E_1hr_tWq-UEm5K_4nA-lMiXkuVDjnpC6E2pftAWEXBKnhH425OOb9aeV67vqK-uI7VR38tXNl-eAckSeV7KYa4Rgj0VeNk2EK84U_ky0as2zSkcL23i0Me1wBYVvPXFpZFVPoiWtd_N-Vn43E8-kr9UHd0MhKVzYS3B-ZYtVzaduFtPLaUlTcV7cTCpx2NhVyFMkOt4GHuzzw9r-0";
    }

    @Test(priority = 1)
    public void UserId_Get_Request() {
        Response response = given().contentType(JSON).accept(JSON).header("Authorization", token)
                .when().get("https://api.spotify.com/v1/me");
        User_ID = response.path("id"); // this step fetch user id;
        String UserName = response.path("display_name");
        System.out.println(UserName);
        response.then().assertThat().statusCode(200); // validation of status code;
        System.out.println(User_ID);
    }

    @Test(priority = 2)
    public void userProfile_Get_Request() {
        Response response = given().accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .when().get("https://api.spotify.com/v1/users/" + User_ID + '/');
        response.then().assertThat().statusCode(200);
        response.prettyPrint();

    }

    @Test(priority = 3)
    public void createPlaylist_Post_Request() {

        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .body("{\"name\": \"Party_Mix_New\",\"description\": \"New playlist description\",\"public\": true}")
                .when()
                .post("https://api.spotify.com/v1/users/" + User_ID + "/playlists");
        String name = response.path("owner.display_name");
        System.out.println("Name Of Owner: " + name);
        response.then().assertThat().statusCode(201); //Validation Of PlayList
        response.prettyPrint(); //optional
    }
}
