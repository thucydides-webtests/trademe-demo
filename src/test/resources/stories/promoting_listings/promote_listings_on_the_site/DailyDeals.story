Display daily deals on the home page

Narrative:
In order to bring my listing to the attention of buyers
As a seller
I want to be able to display my listing on the home page

Meta:
@tags colour:blue

Scenario: Displaying Daily Deals
Meta:
@issue DEMO-1
When I view the daily deals
Then I should see 3 daily deals

Scenario: Daily Deals should be unique
Meta:
@issue DEMO-2
When I view the daily deals
Then I should see no duplicated deals

Scenario: Daily Deals should be updated each day
Meta:
@issue DEMO-3
When I view the daily deals
Then I should see no daily deals that were displayed yesterday

