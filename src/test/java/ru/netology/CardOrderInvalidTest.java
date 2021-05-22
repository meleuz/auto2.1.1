package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.type;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderInvalidTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldGetErrorWhenNameIsEmpty() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=phone] input").setValue("+77777777777");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfNameInLatin() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Ivanov Ivan");
        form.$("[data-test-id=phone] input").setValue("+77777777777");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно." +
                        " Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldGetErrorIfNameInNumerals() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("11111111");
        form.$("[data-test-id=phone] input").setValue("+77777777777");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно." +
                        " Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldGetErrorWhenPhoneIsEmpty() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов-Петров Иван");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorWhenPhoneWithoutPlus() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов-Петров Иван");
        form.$("[data-test-id=phone] input").setValue("877777777777");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678. "));
    }

    @Test
    void shouldGetErrorWhenPhoneWithRandomNumbers() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов-Петров Иван");
        form.$("[data-test-id=phone] input").setValue("11111");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678. "));
    }

    @Test
    void shouldGetErrorWhenPhoneWithLetters() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов-Петров Иван");
        form.$("[data-test-id=phone] input").setValue("asdfg");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678. "));
    }

    @Test
    void shouldGetErrorWhenNotClickCheckbox() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов-Петров Иван");
        form.$("[data-test-id=phone] input").setValue("+77777777777");
        form.$("button").click();
//        $(".input_invalid").$("input")
        $("[data-test-id=agreement].input_invalid input")
                .shouldHave(type("checkbox"));
    }
}
