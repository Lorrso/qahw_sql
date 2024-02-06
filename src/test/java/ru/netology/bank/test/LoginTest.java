package ru.netology.bank.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bank.data.DataHelper;
import ru.netology.bank.data.SQLHelper;
import ru.netology.bank.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown() { SQLHelper.cleanAuthCodes(); }

    @AfterAll
    static void tearDownAll() {
        SQLHelper.cleanDatabase();
    }

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void shouldLoginSuccessfully() {
        var authInfo = DataHelper.generateRandomUser();
        var verificationPage = loginPage.login(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldGetErrorNotificationWhenLoginUnregisteredUser() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.login(authInfo);
        loginPage.verifyErrorNotificationVisibility("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldGetErrorNotificationWhenLoginWithWrongVerificationCode() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.login(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisibility("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}
