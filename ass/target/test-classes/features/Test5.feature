Feature: Check icons
  Scenario Outline: Icon Check
    Given I am an administrator of the website and I upload an alert of type <alert-type>
    Given I am a user of marketalertum
    When I view a list of alerts
    Then I should see 1 alerts
    And the icon displayed should be "<icon-file-name>"
    Examples:
      |alert-type | icon-file-name                                             |
      |1          | https://www.marketalertum.com/images/icon-car.png          |
      |2          | https://www.marketalertum.com/images/icon-boat.png         |
      |3          | https://www.marketalertum.com/images/icon-property-rent.jpg|
      |4          | https://www.marketalertum.com/images/icon-property-sale.jpg|
      |5          | https://www.marketalertum.com/images/icon-toys.png         |
      |6          | https://www.marketalertum.com/images/icon-electronics.png  |
