Maintaining a Watchlist

Meta:
@issue TRAD-9

Narrative:
In order keep track of items I am interested in buying
As a buyer
I want to place items on a watchlist

Scenario: Add an item to my watchlist
Given I am logged on as JOE_BLOGS
And I view an item
When I save it to my watchlist
Then it should appear in my watchlist

Scenario: Add an item to my watchlist
Given I am not logged on
And I view an item
Then I should not be able to add it to my watchlist