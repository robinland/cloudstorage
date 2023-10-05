package com.udacity.jwdnd.course1.cloudstorage.useraction;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserActionTest {
    @LocalServerPort
    public int port;

    public static WebDriver driver;

    public String baseURL;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        System.setProperty("webdriver.http.factory", "jdk-http-client");


    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:" + port;
    }


    @Test
    public void testHomePageNotAccessible() {
        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = null;
        try {
            homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        } catch (TimeoutException e) {
            // Expected
        }
        assertNull(homeMarker);

        WebDriverWait wait2 = new WebDriverWait(driver, 3);
        WebElement marker2 = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        assertNotNull(marker2);
    }
    @Test
    public void testSignupPageAccessible() {
        driver.get("http://localhost:" + port + "/signup");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = null;
        homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        assertNotNull(homeMarker);
    }

    @Test
    public void testUserSignupLoginAndSubmitMessage() {
        String username = "testuser";
        String password = "testpassword";

        driver.get(baseURL + "/signup");

        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.signup("First Name", "Last Name", username, password);

        driver.get(baseURL + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

    }

}
