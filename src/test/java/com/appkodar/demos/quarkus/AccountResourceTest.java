package com.appkodar.demos.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountResourceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    void allAccounts() {
        Response result =
                given()
                        .when().get("/accounts")
                        .then()
                        .statusCode(200)
                        .body(
                                containsString("Diana Rigg"),
                                containsString("George Baird"),
                                containsString("Mary Taylor")

                        ).extract().response();
        List<Account> accountList = result.jsonPath().get("$");
        assertThat(accountList, not(empty()));
        assertThat(accountList, hasSize(3));
    }

    @Test
    void getAccountByAccountNumber() {
    }

    @Test
    @Order(2)
    void createAccount() {
        Account account = new Account(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), "Satheesh", new BigDecimal("354.23"));
        Account returnedAccount = given()
                .when()
                .contentType(ContentType.JSON)
                .body(account)
                .post("/accounts/create")
                .then()
                .extract().as(Account.class);

        assertThat(returnedAccount, equalTo(account));
        assertThat(returnedAccount.getAccountNumber(), equalTo(account.getAccountNumber()));

        Account result =
                given()
                        .when().get("/accounts/"+account.getAccountNumber())
                        .then()
                        .statusCode(200)
                        .extract().as(Account.class);

        assertThat(result, not(nullValue()));
        assertThat(account.getAccountNumber(), equalTo(result.getAccountNumber()));
    }
}