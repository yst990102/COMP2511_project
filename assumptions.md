# Assumptions:

## Basic Rules:


### 1. Default attributes of Character are listed below:

- Default HP: 300
- Default Attack: 5
- Default Defence: 0
- Default XP: 0
- Default Gold: $100


### 2. Default attributes of different types of enemy are listed below:

================= Common Enemy ==================

Slug
- HP: 15
- Attack: 5
- Speed: 5
- battle radius: 1
- support radius: 1

Zombie
- HP: 20
- Attack: 10
- Speed: 2
- battle radius: 2
- support radius: 2
- chance of critical bite: 20%

Vampire
- HP: 25
- Attack: 20
- Speed: 3
- battle radius: 2
- support radius: 3
- chance of critical bite: 20%
- additional number of attacks: (1,3)
- additional damage of a critical bite: 10

================= BOSS ==================

Doggie
- HP: 75
- Attack: 20
- Speed: 5
- battle radius: 1
- support radius: 1
- Stun percentage : 20%
- Stun Round : 2

ElanMuske
- HP: 150
- Attack: 35
- Speed: 5
- battle radius: 1
- support radius: 1
- treatment_amount: 10


#### 3. There will be 5 slugs spawned randomly on path tiles each turn.


#### 4. The Doggie will be spawned after 20 cycles


#### 5. Elan Muske will be spawned after 40 cycles if the playe also has reached 10000 XP


#### 6. Two Gold of $100 will appear on the ground randomly every time the Character completes a cycle of the path.


#### 7. One health potion will appear on the ground randomly every 2 cycles of the path completed by the Character.


#### 8. The goal (How to win the game)
Four basic goals
- Complete 30 cycles
- Amass 88888 Gold
- Obtain 99999 XP
- Kill all bosses

Combination
- Complete 30 cycles AND (amasing 88888 Gold OR obtaining 99999 XP OR killing all bosses)


### 9. Default attributes of a soldier are listed below:
Soldier (produced from Barracks)
- HP: 50
- Attack: 5



## Items Rules:


#### 1. The attack, defense of each equipment are listed below:


Sword
- Add Attack: 5

Stake
- Add Attack: 3
- Add Attack When Attacking Vampires: 8

Staff
- Add Attack: 2
- chance of inflicting a trance: 20%
- duration of trance: 10s

Armour
- Add Defence: 5

Shield
- Add Defence: 2

Helmet
- Add Defence: 2
- Decrease Attack (Character): 2
- Decrease Attack (enemy): 2

Anduril
- Add Attack: 8
- Tripe Attack When Attacking Boss

TreeStump
- Add Defence: 8
- Add Defence When Defending Boss: 12


#### 2. A health potion will heal 50 HP.


#### 3. The health potion can be used by clicking it.


#### 4. When HP is full, health will not be restored when Character consumes the health potion.



## Building Rules:


#### 1. functions of each building

Vampire Castle
- produces 1 vampire every 5 cycles

Zombie Pit
- produces 2 zombies every cycle

Tower
- shooting radius: 2
- Attack: 5

Village
- regain health: 20

Barracks
- produces 1 allied soldier

Trap
- Attack: 10

Campfire
- battle radius: 3



## Fighting Rules:


#### 1. 5% of a random card; A range of 0 to 3 equipment; 0 or 1 health potion; Gold and XP will depend on the type of enemy.

- Slug: $50, XP 100
- Zombie: $100, XP 200
- Vampire: $200, XP 300
- Doggie: $1000, XP 1000, (1, 3) DoggieCoins
- Elan Muske: $1500, XP 1500


#### 2. When a battle starts, the Character will attack first in each turn by default.


#### 3. Every time the Character wins a battle,  there is a 5% chance of obtaining a rare item.



## Selling Rules:


#### 1. The price of each item is fixed when selling or purchasing.

- Sword: $150

- Stake: $150

- Staff: $200

- Armour: $250

- Shield: $150

- Helmet: $200

- Health potion: $200

- The One Ring: $500 (sale only)

- Anduril: $500 (sale only)

- TreeStump: $500 (sale only)

- DoggieCoin: $50-$100 when Muske Dead;  
              $200-$300 when Muske Alive


#### 2. When the old card is replaced by the new one, the Character receives $100 Gold, 100 XP.


#### 3. When the old item is replaced by the new one, the Character receives $100 Gold and 100 XP.

