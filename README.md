# Project Name: 

## Team Information

### Team Members
- Seoyun Ju
- Justin Ng
- Donggyu Oh
- Rebecca Tian 
- Electra Chan

---

## Domain Background 
Gacha games use a randomized reward system, in which players spend in-game currency to obtain characters, weapons, or other items. 

A pull is one attempt to receive a random reward from the available pool of items. Each possible reward has a specific probability of being obtained, and rewards are usually divided into different rarity levels. In many gacha games, pulls are organized into limited-time banners, which are specific reward pools that feature a selected group of characters or items. Each pull requires a payment using in-game currency or real money.

A banner is a temporary pool of rewards you can pull from. For example, a banner might contain several characters, but advertise one featured character with a higher probability of appearing. Different banners can have different characters, probabilities, costs, or availability periods. (delete unnecessary) Some featured rewards may have increased probabilities of being obtained while the banner is active. 

Each gacha game has its own pity system, which prevents players from going too many pulls without receiving a high-rarity reward. For example, it is common in gacha games for a player to receive a 5-star reward guaranteed within 90 pulls, while at least one 4-star reward is guaranteed within every 10 pulls. The number of pulls since the last high-rarity reward is commonly referred to as the player’s pity, and this count may reset once that reward is obtained. Players generally cannot directly purchase a specific 5-star character or item. Instead, they spend in-game currency to pull for a chance to obtain it. This in-game currency, such as Primogems, Stellar Jades, Polychromes, or other game-specific currencies, can often be obtained through gameplay or purchased using real money. As a result, a player may spend real currency, such as CAD, to obtain in-game currency, which is then used to perform pulls.

Loot boxes are a broader form of randomized reward container and may appear as occasional rewards or purchases without being centered on character collection or banner-based progression. Gacha systems and loot boxes are both randomized reward mechanisms in which players receive an item from a predefined pool of possible rewards. The primary distinction lies in how these systems are implemented. Gacha systems typically involve repeated pulls from banners that feature collectible characters, weapons, or other items, often with specified probabilities or increased rates for selected rewards. 


## Program Summary 
Our system is designed to help players analyze and better understand their gacha pull results. Users can manually enter their pull history or import it from a supported pull-history API and the targeted 5-star. The program will calculate statistics such as rarity distribution, observed versus expected pull rates, and the probability of obtaining specific rewards within a given number of pulls. It can also estimate the number of future pulls or costs required to reach certain outcomes, allowing users to compare their results with the game’s advertised probabilities and make more informed decisions.


## Program Description 




---

## User Stories

### User Story 1: Calculate Pull Probability
As a player, I want to enter my current pull history, so that I can see my probability of pulling a 5-star within N pulls.
- As a user, I want to figure out how many pulls give me the best chance of getting a 5-star without spending too much money (minimum problem).

### User Story 2 Pull Simulation
As a player, I want to simulate thousands of pull sessions from my current state (default = 0 pulls) , so that I can see the distribution of outcomes.

### User Story 3
As a player, I want to see the estimated cost of a given number of pulls in my chosen currency, so that I understand the real spending involved.

### User Story 4 
As a user, I want to compare the pity systems of Hoyoverse games side by side, so that I can see which is more favourable. 

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

### Use Case 2: Pull Simulation
**Primary actor:** Player

**Preconditions:**
- Pity rules are valid and available for each supported game.

**Trigger:** The player chooses to simulate a number pulls for a selected game or for specific drop percentages and pity

**Main success scenario:**
1. Player specifies either (Game) or (drop percentages and # for pity)
2. Player specifies a starting pity value (current pity#)
3. Player specifies how many pulls to simulate per sample 
4. The system retrieves pity rules for selected game if specified
5. The system Performs many (3000 or more) samplings given a number of pulls.
6. The system calculates the average number of 5 stars received with extra information (special character,standard character) and calculates average pity carryover (number of pulls since the last 5 star) from simulations.
7. The system calculates the standard deviation between samples and the (1st,2nd,3rd) quartiles.
8. The system displays distribution graphs generated by simulation (with the three quartiles).
9. The system displays all statistical information.

**Extensions:**
- 1a. Settings are invalid or repeated
  - Exact settings chosen will produce an error
  - If values given(other than pity#) are negative
**Success outcome:** 
  - Player is able to see the mean and median amount of 5 stars(standard/special) received in a simulation.

### Use Case 3: Calculate Pull Cost in Chosen Currency
**Primary actor:** Player

**Precondition:** System has at least one game with configured pull types, in-game currency per pull for the pull types, and real money pricing for in-game currency.

**Trigger:** The player chooses to calculate the cost of pulls.

**Main success scenario:** 
1. Player selects game, pull type, number of pulls, and currency.
2. System retrieves in-game currency cost per pull.
3. System calculates total in-game currency required.
4. System retrieves pricing information in original currency and calculates real money cost.
5. System retrieves exchange rates and converts cost into chosen currency.
6. System displays calculated cost.

**Extensions:**
 - Invalid input: system explains error, asks for another input.
 - Chosen currency is the same as original currency: system skips exchange rate retrieval and conversion
 - Failed exchange rate retrieval: system informs user, system skips conversion, system displays result in original currency
 - User cancels: use case ends

**Success outcome:** Player sees calculated cost of selected game, pull type, and number of pulls in chosen currency.


### Use Case 4 : Compare Across Games 
**Name:** Compare across games 

**Primary actor:** Player

**Precondition:** App must have access to both games’ pity systems 

**Trigger:** Player selects 2 games and 2 respective banners to compare and clicks “compare” button. 

**Success Outcome:** Player is able to see side-by-side comparison of 2 games.  

**Main success scenario**
1. Player selects 2 games and clicks compare button 
2. The player enters the amount of pulls they want to compare in the text field (Across Hoyoverse games they cost the same) 
3. Player enters current pity count (pulls since last 5-star)  for each game (if applicable, otherwise default is 0) 
4. The system retrieves the pity rules for both selected games.
5. The system calculates the probability of obtaining a 5-star reward within the specified number of pulls for each game. (Refer to User Story 1) 
6. The system displays the results side by side, allowing the player to compare the two pity system in the same window 

**Extensions**
2a. Invalid pull count (only accept positive integers) : Player entered an invalid pull count triggers error message
3a. Invalid pity count (only accept positive integers 0-90) : Player entered an invalid pull count triggers error message


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
| Justin | Use Case 2: Pull Simulation |
| Donggyu | Use Case 3: Calculate Pull Cost in Chosen Currency |
| Rebecca | Use Case 4: Compare Across Games |
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
