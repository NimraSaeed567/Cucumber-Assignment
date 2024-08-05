package com.tau.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;
import java.util.List;

public class CartPage {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By cartButton = By.xpath("/html/body/nav/ul/button");
    private By cartItemsContainer = By.xpath("/html/body/nav/ul/button");
    private By cartItemNames = By.cssSelector(".cart-item-name"); // Adjust selector as per actual HTML structure

    // Methods
    public void openCart() {
        WebElement button = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.elementToBeClickable(cartButton));
        button.click();
        System.out.println("Clicked on cart button and opened the cart."); // Logging statement
    }

    public List<WebElement> getItemsInCart() {
        try {
            new WebDriverWait(driver, 60)
                    .until(ExpectedConditions.visibilityOfElementLocated(cartItemsContainer));
            List<WebElement> items = driver.findElements(cartItemNames);
            System.out.println("Retrieved items in the cart. Number of items found: " + items.size()); // Logging statement
            return items;
        } catch (TimeoutException e) {
            System.out.println("Timeout while waiting for cart items to become visible. Error: " + e.getMessage());
            return Collections.emptyList(); // Return an empty list if the items are not found
        }
    }

    public boolean isCartEmpty() {
        boolean isEmpty = getItemsInCart().isEmpty();
        System.out.println("Checked if cart is empty. Result: " + (isEmpty ? "Cart is empty." : "Cart has items.")); // Logging statement
        return isEmpty;
    }

    public boolean verifyItemsInCart(List<String> expectedItemNames) {
        List<WebElement> itemsInCart = getItemsInCart();
        for (String expectedName : expectedItemNames) {
            boolean found = itemsInCart.stream()
                    .anyMatch(item -> item.getText().equals(expectedName));
            if (!found) {
                System.out.println("Item not found in cart: " + expectedName);
                return false;
            }
        }
        System.out.println("All expected items are present in the cart."); // Logging statement
        return true;
    }
}
