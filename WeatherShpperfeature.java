@tag
Feature: Weather Shopper Automation

  Scenario: Verify the temperature and buy suitable product
    Given I am on the Weather Shopper homepage
    When I check the current temperature
    Then I choose the appropriate product based on the temperature
    And I add the product to the cart
    And I proceed to checkout
    And I complete the purchase

