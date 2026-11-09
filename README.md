# Project Name

## Team Information

### Team Members
- Seoyun Ju
- Name
- Name
- Name
- Electra Chan
- Name

---

## Project Description

### Domain

[Project description]

---

## User Stories

### User Story 1: Calculate Pull Probability
As a player, I want to enter my current pull history, so that I can see my probability of pulling a 5-star within N pulls.
- As a user, I want to figure out how many pulls give me the best chance of getting a 5-star without spending too much money (minimum problem).

### User Story 2

### User Story 3

### User Story 5
As a user, I want to be able to enter my pull history easily from a link so that I don’t have to type everything in manually.

### User Story 6
As a user, I want to be able to see the standardized probabilities of getting a character and the different type of banners.

(Add additional user stories as needed.)

---

## Use Cases

### Use Case 1: Calculate Pull Probability
**Primary actor:** Player

**Preconditions:**
- The pity rules (base rate, soft pity, hard pity) for each supported game are available to the system.

**Trigger:** The player asks the system to calculate their probability of pulling a 5-star.

**Success outcome:** The player is shown their probability of getting at least one 5-star within the chosen number of pulls.

**Main success scenario:**
1. The player selects a game.
2. The player enters their current pity (the number of pulls since their last 5-star) and the number of pulls they plan to make.
3. The system checks that the input is valid.
4. The system calculates the probability of getting at least one 5-star within that number of pulls, based on the game's pity rules.
5. The system shows the probability as a percentage.

**Extensions:**
- 3a. The input is not valid (e.g., not a number, negative, or the pity is at or above the game's hard pity).
    1. The system explains which value is not valid.
    2. The player corrects the value, and the use case continues at step 3.
- 4a. The planned pulls reach the game's hard pity.
    1. The system shows that a 5-star is guaranteed (100%).
    2. The system shows how many pulls are needed to reach hard pity, and the use case ends.

### Use Case 2

### Use Case 3

### Use Case 5 : Get Pull Data and Compare
**Primary actor:** Player

**Precondition:** Requires the player to have an authenticated link from one of the games we support, with an optional log in user account to save the data.

**Trigger:** The player enters the link in a text box and clicks a button.

**Main success scenario:**
1. Accept and validate Link.
2. Use the API to get the player’s pull data in JSON format.
3. Use the pull data to generate icons of each of the pulls and display them neatly in a Java panel
4. Update the pity in the user account/ saved data.
5. Update the world comparison data and show it in a Java panel.
6. Save the pull data in the user account if logged in.
7. If not logged in, prompt the user to log in or sign up with a new account and then save the pull data.

**Extensions:**
The analyzer will be able to compare the player's luck to other players to see how unlucky they are. (ex. Bottom 37%, top 5% of players)

**Success outcome:** User is able to see their current pulls (up to 6 months ago)

### Use Case 6 : Get different pull types and probabilities for the games
**Primary actor:** Player

**Precondition:** None

**Trigger:**  The user opens the application.

**Main success scenario:**
1. User starts the application.
2. We send a GET request to the API for getting the probabilities of all the games? / for a specific game + details on the characters and pull types.
3. We receive a JSON file, parse it and load all the probabilities for all the characters and which banners are available.
4. If the GET request fails, return an error message stating that we were unable to get the pull probabilities OR we set up a backup JSON file that’s static and use that instead for the probabilities.

**Extensions:**
Get data from games other than Hoyoverse games, such as Reverse 1999, Pokemon TCG, Project Sekai etc.

**Success outcome:** User is able to see their current pulls (up to 6 months ago)

(Add additional use cases as needed.)

---

## Minimal Viable Product (MVP)

| Team Member | MVP Use Case |
|-------------|--------------|
| Seoyun | Use Case 1: Calculate Pull Probability |
| Name | Use Case |
| Name | Use Case |
| Electra | Use Case 5 and 6: Getting pull data, probabilities, and information on characters. |
 

---

## Entities

### Entity 1

### Entity 2

(Add additional entities as needed.)

---

## API Usage

**API Name:**

**Documentation:** [Link]

**How it is used in the program:**

---

## Design Diagrams

### UML Class Diagram


### Sequence Diagrams

---

## How to Run

[Instructions for running the program.]

---

## Notes

[Any additional project information.]
