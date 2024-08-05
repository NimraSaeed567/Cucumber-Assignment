package com.tau.models;

import org.openqa.selenium.WebElement;

public class Product {
    private int price;
    private WebElement button;

    public Product(int price, WebElement button) {
        this.price = price;
        this.button = button;
    }

    public int getPrice() {
        return price;
    }

    public WebElement getButton() {
        return button;
    }
}
