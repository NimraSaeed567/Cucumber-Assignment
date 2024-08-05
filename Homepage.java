
	package com.tau.pages;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;


	public class HomePage {
	    WebDriver driver;

	    public HomePage(WebDriver driver) {
	        this.driver = driver;
	    }

	    // Locators
	    By temperatureText = By.id("temperature");
	    By buyMoisturizersButton = By.xpath("//button[text()='Buy moisturizers']");
	    By buySunscreensButton = By.xpath("//button[text()='Buy sunscreens']");

	    // Methods
	    public String getTemperature() {
	        return driver.findElement(temperatureText).getText();
	    }

	    public void clickOnMoisturizers() {
	        driver.findElement(buyMoisturizersButton).click();
	    }

	    public void clickOnSunscreens() {
	        driver.findElement(buySunscreensButton).click();
	    }
	}

