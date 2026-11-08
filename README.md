# Project Name

## Team Information

### Team Members
- Seoyun Ju
- Name
- Name
- Name
- Name
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

(Add additional use cases as needed.)

---

## Minimal Viable Product (MVP)

| Team Member | MVP Use Case |
|-------------|--------------|
| Seoyun | Use Case 1: Calculate Pull Probability |
| Name | Use Case |
| Name | Use Case |

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
