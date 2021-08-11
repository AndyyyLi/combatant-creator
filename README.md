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