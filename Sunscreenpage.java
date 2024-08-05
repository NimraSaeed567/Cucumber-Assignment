package com.tau.pages;

import com.tau.models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SunscreensPage {
    private WebDriver driver;

    public SunscreensPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locator
    private By productContainer = By.xpath("//div[contains(@class, 'text-center col-4')]");

    public void addCheapestSunscreensToCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productContainer));
        Product cheapestSPF50 = null;
        Product cheapestSPF30 = null;

        for (WebElement product : products) {
            try {
                String productName = product.findElement(By.xpath(".//p[contains(@class, 'font-weight-bold top-space-10')]")).getText();
                int price = Integer.parseInt(product.findElement(By.xpath(".//p[contains(text(), 'Price: Rs.')]")).getText().replaceAll("[^0-9]", ""));
                WebElement button = product.findElement(By.tagName("button"));

                if (productName.toLowerCase().contains("spf-50")) {
                    if (cheapestSPF50 == null || price < cheapestSPF50.getPrice()) {
                        cheapestSPF50 = new Product(price, button);
                    }
                }
                if (productName.toLowerCase().contains("spf-30")) {
                    if (cheapestSPF30 == null || price < cheapestSPF30.getPrice()) {
                        cheapestSPF30 = new Product(price, button);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error processing product: " + e.getMessage());
            }
        }

        try {
            if (cheapestSPF50 != null) {
                System.out.println("Cheapest SPF-50 sunscreen found at price: " + cheapestSPF50.getPrice());
                new Actions(driver).moveToElement(cheapestSPF50.getButton()).click().perform();
                System.out.println("Added SPF-50 sunscreen to cart.");
            } else {
                System.out.println("No SPF-50 sunscreen found.");
            }
            if (cheapestSPF30 != null) {
                System.out.println("Cheapest SPF-30 sunscreen found at price: " + cheapestSPF30.getPrice());
                new Actions(driver).moveToElement(cheapestSPF30.getButton()).click().perform();
                System.out.println("Added SPF-30 sunscreen to cart.");
            } else {
                System.out.println("No SPF-30 sunscreen found.");
            }
        } catch (Exception e) {
            System.out.println("Error adding products to cart: " + e.getMessage());
        }
    }
}
