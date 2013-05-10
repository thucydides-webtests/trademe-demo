Searching by category

Meta:
@issue TRAD-1
@tags size:small, colour:red, type:functional

Narrative:
In order to find objects that I am looking for
As a buyer
I want to search for objects whose title or description contains certain keywords

Scenario: Looking for items containing a keyword in a given category
Given I want to find an item in a particular category
When I search for listings relating to <keyword> in the <category> category (<options>)
Then I should see a listing with '<expected_title>' in the title

Examples:
|category|keyword|expected_title| options
|Art     |cats   |Sculpture Cat | the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog
|Art     |chat   |'Chat Noir by Théophile Steinlen Vintage Art Print| the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog
|Books   |dogs   |Dogs  | the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog