package com.tau.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
    }

    // Locators
    private By payWithCardButton = By.className("stripe-button-el");
    private By emailField = By.id("email");
    private By cardNumberField = By.id("card_number");
    private By expiryDateField = By.id("cc-exp");
    private By cvcField = By.id("cc-csc");
    private By zipField = By.id("billing-zip");
    private By iconTick = By.className("iconTick");
    private By stripeFrame = By.cssSelector("iframe[name='stripe_checkout_app']");

    // Methods
    public void proceedToPayment() {
        try {
            WebElement payButton = wait.until(ExpectedConditions.elementToBeClickable(payWithCardButton));
            payButton.click();
            System.out.println("Clicked on 'Pay with Card' button.");

            // Wait for the frame to be available and switch to it
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(stripeFrame));
            System.out.println("Switched to Stripe payment frame.");
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for 'Pay with Card' button or frame. Error: " + e.getMessage());
        }
    }

    public void completePurchase(String email, String cardNumber, String expiryDate, String cvc, String zip) {
        WebDriverWait wait = new WebDriverWait(driver, 60); // Increased timeout duration to 60 seconds

        try {
            // Switch to the payment frame
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(stripeFrame));
            System.out.println("Switched to Stripe payment frame.");

            // Wait for and fill out the payment form
            WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            emailElement.sendKeys(email);
            System.out.println("Entered email.");

            WebElement cardNumberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberField));
            for (char digit : cardNumber.toCharArray()) {
                cardNumberElement.sendKeys(String.valueOf(digit));
                Thread.sleep(100); // Add a small delay
            }
            System.out.println("Entered card number.");

            WebElement expiryDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(expiryDateField));
            for (char digit : expiryDate.toCharArray()) {
                expiryDateElement.sendKeys(String.valueOf(digit));
                Thread.sleep(100); // Add a small delay
            }
            System.out.println("Entered expiry date.");

            WebElement cvcElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cvcField));
            for (char digit : cvc.toCharArray()) {
                cvcElement.sendKeys(String.valueOf(digit));
                Thread.sleep(100); // Add a small delay
            }
            System.out.println("Entered CVC.");

            WebElement zipElement = wait.until(ExpectedConditions.visibilityOfElementLocated(zipField));
            for (char digit : zip.toCharArray()) {
                zipElement.sendKeys(String.valueOf(digit));
                Thread.sleep(100); // Add a small delay
            }
            System.out.println("Entered zip code.");

            WebElement tickElement = wait.until(ExpectedConditions.elementToBeClickable(iconTick));
            tickElement.click();
            System.out.println("Clicked on the confirmation icon.");

            // Switch back to the default content
            driver.switchTo().defaultContent();
            System.out.println("Switched back to the default content.");
            System.out.println("Purchase completed.");
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element. Error: " + e.getMessage());
            // Optionally, take a screenshot for debugging
            // TakesScreenshot screenshot = ((TakesScreenshot) driver);
            // File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            // File destFile = new File("screenshot.png");
            // FileUtils.copyFile(srcFile, destFile);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


}
