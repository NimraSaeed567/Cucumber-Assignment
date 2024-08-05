package com.tau.steps;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.tau.pages.CartPage;
import com.tau.pages.CheckoutPage;
import com.tau.pages.HomePage;
import com.tau.pages.MoisturizersPage;
import com.tau.pages.SunscreensPage;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class steps {
    WebDriver driver;
    private static final Logger logger = Logger.getLogger(steps.class.getName());
    HomePage homePage;
    MoisturizersPage moisturizersPage;
    SunscreensPage sunscreensPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    private static ExtentReports extent;
    private static ExtentTest test;

    @Before
    public void before_setup() throws MalformedURLException {
        WebDriverManager.chromedriver().setup();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(new URL("http://172.16.2.83:4444/wd/hub"), capabilities);

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("Report.html");
        sparkReporter.config().setTheme(Theme.STANDARD); // Optional: Set report theme
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        test = extent.createTest("Weather Shopper Test", "Test to verify shopping on Weather Shopper site based on temperature");
    }

    @Given("I am on the Weather Shopper homepage")
    public void i_am_on_the_weather_shopper_homepage() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://weathershopper.pythonanywhere.com/");
        logger.info("Opened the Weather Shopper homepage.");
        test.info("Opened the Weather Shopper homepage.");

        homePage = new HomePage(driver);
        moisturizersPage = new MoisturizersPage(driver);
        sunscreensPage = new SunscreensPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @When("I check the current temperature")
    public void i_check_the_current_temperature() {
        String temperature = homePage.getTemperature();
        System.out.println("Current temperature: " + temperature);
        test.info("Current temperature: " + temperature);
    }

    @Then("I choose the appropriate product based on the temperature")
    public void i_choose_the_appropriate_product_based_on_the_temperature() {
        String temperature = homePage.getTemperature();
        int temp = Integer.parseInt(temperature.replaceAll("[^0-9]", ""));
        if (temp < 19) {
            homePage.clickOnMoisturizers();
            moisturizersPage.addCheapestMoisturizersToCart();
            test.info("Chosen moisturizers based on temperature.");
        } else if (temp > 34) {
            homePage.clickOnSunscreens();
            sunscreensPage.addCheapestSunscreensToCart();
            test.info("Chosen sunscreens based on temperature.");
        }
    }

    @Then("I add the product to the cart")
    public void i_add_the_product_to_the_cart() {
        // Products are added to the cart in the previous methods
        test.info("Products added to the cart.");
    }

    @Then("I proceed to checkout")
    public void i_proceed_to_checkout() {
        cartPage.openCart();
        if (!cartPage.isCartEmpty()) {
            checkoutPage.proceedToPayment();
            test.info("Proceeded to payment.");
        } else {
            System.out.println("Cart is empty. Cannot proceed to checkout.");
            test.warning("Cart is empty. Cannot proceed to checkout.");
        }
    }

    @Then("I complete the purchase")
    public void i_complete_the_purchase() {
        try {
            checkoutPage.completePurchase("your.email@example.com", "4242424242424242", "12/25", "123", "12345");
            test.pass("Purchase completed successfully.");
        } catch (Exception e) {
            logger.severe("Failed to complete the purchase: " + e.getMessage());
            test.fail("Failed to complete the purchase: " + e.getMessage());
        } finally {
            driver.quit(); // Ensures the browser is closed after the test run
            extent.flush(); // Generate the report
        }
    }
}
