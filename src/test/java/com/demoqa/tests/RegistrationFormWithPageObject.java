package com.demoqa.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.demoqa.pages.RegistrationFormPage;
import com.demoqa.utils.RandomUtils;
import com.github.javafaker.Faker;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;


public class RegistrationFormWithPageObject {
    RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    String firstName;
    String lastName;
    String email;
    String phone;
    String day;
    String month;
    String year;
    String sex;
    String hobby;
    String address;
    String subject;

    Faker faker = new Faker();

    @BeforeEach
    void prepareTestData(){
        firstName = faker.name().firstName();
        lastName  = faker.name().lastName();
        email = faker.internet().emailAddress();
        phone = faker.phoneNumber().subscriberNumber(10);
        sex = faker.demographic().sex();
        hobby = RandomUtils.getRandomHobby();
        address = faker.address().fullAddress();
        subject = RandomUtils.getRandomSubject();
        day = String.valueOf(faker.number().numberBetween(1,28));
        month = RandomUtils.getRandomMonth();
        year = String.valueOf(faker.number().numberBetween(1980,2010));

    }

    @BeforeAll
    static void setUp() {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1600x800";
        Configuration.holdBrowserOpen = true;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

    }

    @Test
    void fillNameTest(){

        //  String firstName="Valeria";

        registrationFormPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(sex)
                .setNumber(phone)
                .setBirthDate(day, month, year)
                .setSubjects(subject)
                .setHobbies(hobby)
                .setPicture("img/crow.jpg")
                .setAddress(address)
                .setState("Haryana")
                .setCity("Karnal")
                .clickSubmit();

        registrationFormPage.checkResultsTableVisible()
                .checkResult("Student Name",firstName+ " "+lastName)
                .checkResult("Student Email",email)
                .checkResult("Gender",sex)
                .checkResult("Mobile",phone)
                .checkResult("Date of Birth",day + " "+ month + "," +  year)
                .checkResult("Subjects",subject)
                .checkResult("Hobbies",hobby)
                .checkResult("Picture","crow.jpg")
                .checkResult("Address",address)
                .checkResult("State and City","Haryana Karnal");

    }
}
