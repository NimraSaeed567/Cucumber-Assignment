package com.tau.pages;

import com.tau.models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MoisturizersPage {
    private WebDriver driver;

    public MoisturizersPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locator
    private By productContainer = By.xpath("//div[contains(@class, 'text-center col-4')]");

    public void addCheapestMoisturizersToCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productContainer));
        Product cheapestAloe = null;
        Product cheapestAlmond = null;
        
        System.out.println("List of Moisturizers found:");
        
        for (WebElement product : products) {
            try {
                String productName = product.findElement(By.xpath(".//p[contains(@class, 'font-weight-bold top-space-10')]")).getText();
                int price = Integer.parseInt(product.findElement(By.xpath(".//p[contains(text(), 'Price: Rs.')]")).getText().replaceAll("[^0-9]", ""));
                WebElement button = product.findElement(By.tagName("button"));

                if (productName.toLowerCase().contains("aloe")) {
                    if (cheapestAloe == null || price < cheapestAloe.getPrice()) {
                        cheapestAloe = new Product(price, button);
                    }
                }
                if (productName.toLowerCase().contains("almond")) {
                    if (cheapestAlmond == null || price < cheapestAlmond.getPrice()) {
                        cheapestAlmond = new Product(price, button);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error processing product: " + e.getMessage());
            }
        }

        try {
            if (cheapestAloe != null) {
                System.out.println("Cheapest Aloe moisturizer found at price: " + cheapestAloe.getPrice());
                new Actions(driver).moveToElement(cheapestAloe.getButton()).click().perform();
                System.out.println("Added Aloe moisturizer to cart.");
            } else {
                System.out.println("No Aloe moisturizer found.");
            }
            if (cheapestAlmond != null) {
                System.out.println("Cheapest Almond moisturizer found at price: " + cheapestAlmond.getPrice());
                new Actions(driver).moveToElement(cheapestAlmond.getButton()).click().perform();
                System.out.println("Added Almond moisturizer to cart.");
            } else {
                System.out.println("No Almond moisturizer found.");
            }
        } catch (Exception e) {
            System.out.println("Error adding products to cart: " + e.getMessage());
        }
    }
}
