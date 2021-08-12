# Personal Project: Combatant Creator

## User Audience and Application Functionality

Combatant Creator is a simple character designing game for players who like to personalize their playable avatar
before taking them into battle! Ideally the character would face off against opponents after designing their character, 
but due to time constraints I chose to focus on just the customization phase.
Combatant Creator allows the user to make their own character by selecting from a variety of different items to
equip which prepares them for battle! Users are able to create their character by choosing ***one*** item from each of
the following categories:

- Weapon
- Spell
- Armour

Each item has its own **name, description, and stat changes**, and each item type has its own **special features**.
Each category is *separate* from each other, and the user can freely navigate back and forth between them.
There is also a section that *summarizes* what the user's character has currently equipped, which also shows **all of 
the character's stats**. From there they can also *finalize* their character, at which they can name their character 
before finishing and completing the game.

## Inspiration

I've always been a huge fan of video games, and I hope to go into video game programming in the future, so 
when I first started thinking of ideas for this project I immediately wanted to do something related to video games.
I figured if I designed something related to games, it would be more applicable if I presented it to a future employer
from a game company! As I mentioned above, I had originally envisioned an application that would include both the
creation of a character as well as the battle between them and an opponent, but I have more interest in the 
customization of the character so I decided to focus on that. 
I love the concept of personalizing in game characters because they feel more unique to me as a player, and this is a 
feature that is present in every game I've played, from choosing cosmetics and weapons in Team Fortress 2 
to picking my favourite character skin in League of Legends, thus I would be very interested in creating a
character designing application myself!

## User Stories

- As a user, I want to be able to add an item to my character
- As a user, I want to be able to choose category-specific features for my character's items
- As a user, I want to be able to switch my currently equipped item to another item of the same category
- As a user, I want to be able to see what my character has equipped so far and its total stats
- As a user, I want to be able to select an item to learn more about it (i.e. its description and stat changes)
- As a user, I want to be able to save my current character to file, including name, equipment, and stats
- As a user, I want to be able to load my saved character from file or create a new one when I start the game

## Phase 4: Task 2

I've adjusted the implementation of the Character class to be robust by changing two methods to potentially throw an
exception. The first robust method is setName(), which will throw an InvalidNameException if the passed in string is 
empty or null, and this is tested with the tests testSetNameNoExceptionThrown() and testSetNameExceptionThrown() in the
CharacterTest class. The other robust method is removeItem(), specifically its helper method tryToRemove(), which can
throw a CannotRemoveItemException if the character does not have the passed in item equipped (this exception is also 
thrown by removeItem()), and this covers both the case of having not equipped anything and having equipped a different 
item of the same category. These exceptions are tested in the CharacterTest class with the tests 
testEquipThenRemoveItemsNoExceptionThrown(), testRemoveNonEquippedItemExceptionThrown() and 
testRemoveAnotherItemExceptionThrown().

## Phase 4: Task 3

- I can make WeaponList, SpellList, and ArmourList contain a list of their respective item subtype rather than have
them all have a list of items (i.e. WeaponList would contain a List\<Weapon> instead). 
  - This way the category subclasses do not need to have an association with Item, and I may also modify Category's 
    List\<Item> field in order to accomplish this.
  
- The abstract CategoryTab class can be refactored to not have associations with Item and the category lists. Instead I
can implement methods in each specific tab subclass which will be called respectively based on what type of item is
  changing states due to the buttons. 
  - This can also make the process of identifying the passed in item more efficient because otherwise
  the compiler traverses through an ArrayList of ALL the items every time it needs to find a specific item, which will 
    be slow if the list gets very long. I could also change the List to a Set, but I think breaking down the items into 
    item types first will be faster because it will essentially remove two thirds of the entire collection before the
    searching even starts.
    - There might also be some equals and hashcode overriding for the item comparisons.
  
- I can change the JsonLoader class to not have a dependency relationship with Character by making the method take in
a character, modify that character, and return it at the end of the method.
  
- CombatantCreator does not need to have an association with Category, the Category field can be put inside the
Category class or its subclasses instead.
  
- I can also refactor the arrange methods of the Category subclasses such that the method structure is implemented in 
  Category, and the subclasses can call the method in the superclass with the appropriate parameters (this is not 
  directly related to the UML diagram but it's something I noticed when looking over my code)
  
