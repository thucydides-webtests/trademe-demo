Searching by keyword

Meta:
@issue TRAD-2

Narrative:
In order to find objects that I am looking for
As a buyer
I want to search for objects whose title or description contains certain keywords

Scenario: Looking for items containing the word 'cat'
Given I want to purchase a cat
When I search for listings relating to 'cat'
Then I should only see listings with 'cat' in the title

Scenario: Looking for items containing the word 'dog'
Given I want to purchase a dog
When I search for listings relating to 'dog'
Then I should only see listings with 'dog' in the title
