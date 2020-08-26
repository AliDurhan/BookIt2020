package com.bookit.utilities;

import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.*;


public class APIUtilities {

    static {
        baseURI = Environment.BASE_URI;
    }

    public static String getToken(){
        Response response = given().
                queryParam("email", Environment.LEADER_USERNAME).
                queryParam("password", Environment.LEADER_PASSWORD).
                when().
                get("/sign");
        response.then().log().ifError();//if request failed, print response information
        String token = response.jsonPath().getString("accessToken");
        System.out.println("TOKEN :: " + token);
        return token;
    }

    public static String getToken(String role){

        String email = null;
        String password = null;

        if (role.toLowerCase().contains("teacher")) {
            email = Environment.TEACHER_USERNAME;
            password = Environment.TEACHER_PASSWORD;
        } else if (role.toLowerCase().contains("lead")) {
            email = Environment.LEADER_USERNAME;
            password = Environment.LEADER_PASSWORD;
        } else {
            email = Environment.MEMBER_USERNAME;
            password = Environment.MEMBER_PASSWORD;
        }

        Response response = given()
                .queryParam("email", email)
                .queryParam("password", password)
                .when()
                .get("/sign"); //this would come from the Endpoints util class
        response.then().log().ifError();
        String token = response.jsonPath().getString("accessToken");
        System.out.println("TOKEN ::" + token);
        return token;
    }

    public static String getToken(String email, String password){
        Response response = given().
                queryParam("email", email).
                queryParam("password", password).
                when().
                get("/sign");
        response.then().log().ifError();//if request failed, print response information
        String token = response.jsonPath().getString("accessToken");
        System.out.println("TOKEN :: " + token);
        return token;
    }

    public static int getUserId (String email, String password){
        try {
            String token = getToken(email, password);
            Response response = given().auth().oauth2(token)
                    .when().get(Endpoints.GET_ME);
            response.then().log().ifError();

            int id = response.jsonPath().getInt("id");
            return id;
        } catch (Exception e){
            System.out.println("USER DOESN'T EXIST");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static Response deleteUserByID(int id){
        String token = getToken("teacher");
        Response response = given().auth().oauth2(token).when().delete(Endpoints.DELETE_STUDENT, id);
        response.then().log().ifError();
        return response;
    }

    public static Response addBatch (int batchNumber){
        String token = getToken("teacher");

        Response response = given()
                                .auth().oauth2(token)
                                .queryParam("batch-number", batchNumber)
                            .when().post(Endpoints.ADD_BATCH);
                response.then().log().ifError();
        return response;
    }

    public static Response addTeam (String teamName, String location, int batchNumber){
        String token = getToken("teacher");

        Response response = given().auth().oauth2(token)
                                .queryParam("team-name", teamName)
                                .queryParam("campus-location", location)
                                .queryParam("batch-number", batchNumber)
                            .when().post(Endpoints.ADD_TEAM);
                    response.then().log().ifError();
            return response;
    }


}
