# Fireboy and Watergirl (Java Swing Implementation)

A custom implementation of the classic **Fireboy and Watergirl** puzzle-platformer, built entirely with the `javax.swing` library. This project recreates the mechanics of the original game with multiple levels, cooperative character control, collectible diamonds, and enemies.

<p align="center">
<img width="300" height="300" alt="image" src="https://github.com/user-attachments/assets/3de4319a-cdee-4257-9b76-2c7a3f719c4f" />
<img width="300" height="300" alt="image" src="https://github.com/user-attachments/assets/48c60b55-30d6-44e0-b06b-f4b206c15fba" />
<img width="300" height="300" alt="image" src="https://github.com/user-attachments/assets/fb0983b6-6528-4724-8dff-83982e16da9d" />
</p>

---

## ⁉️ features

* **Two playable characters**: Fireboy and Watergirl, each with their own collision rules.
* **Three playable levels** with increasing difficulty and features.
* **Classic mechanics**: doors, diamonds (red, blue, white), buttons, teleporters, and hazards like lava.
* **Monsters**: bouncing enemies that players must avoid.
* **Scoreboard system**: track names, times, win/lose status, and a grading system (A–F).
* **Menu system**:
  * **Play** : choose level
  * **Help** : how to play
  * **Legend** : explains game items
* **Thread-based game loop** for smooth animation and updates.

---

## ‼️ technical details

* **Language**: Java
* **Library**: `javax.swing`
* **Game loop**: Implemented with **threads** for smooth updates and animations.
* **Rendering**: Custom drawing with `paintComponent` for sprites and tiles.
* **Collision system**: Tile-based, with special hitboxes for players, diamonds, and monsters.
* **Data persistence**: Text file used for scoreboard (names, times, grades).

---

## 💫 how to play

1. Clone or download the repository.
2. Open the project in an IDE that supports Java [Eclipse is one which I reccomend!]
3. Compile & run `Main.java`.
4. Use the **menu** to select a level and start playing!!

---

## 🙌🏻 controls

* **Fireboy**: Arrow Keys
* **Watergirl**: WAD Keys (just like the real game)
* **Objective**: Collect diamonds, avoid monsters, and guide both characters to their doors in efficient time!

---

## scoreboard & grading

* After each game, players can enter their name. A sample 'database' has been provided. 
* Data stored includes:

```
NAME | TIME | LEVEL | WIN/LOSE | COLLECTED ALL DIAMONDS? | DATE
```

* View scoreboard by the **database window**.

---
