package ru.netology.bank.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement header = $("[data-test-id=dashboard]");

    public DashboardPage() {
        header.shouldHave(text("Личный кабинет")).shouldBe(Condition.visible);
    }
}
