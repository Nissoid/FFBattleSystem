# Final Fantasy Turn-Based Battle System (Java Console v3.0) ⚔️

A robust, object-oriented JRPG turn-based combat system inspired by Final Fantasy, developed in Java and built following clean architecture principles (MVC pattern, Factory design pattern, and SOLID principles).

---

## 🚀 Key Features

* **Clean MVC Architecture:** Total separation between business logic (Models), the command-line interface (View), and flow coordination (Controller).
* **Extended Character Roster (Factory Pattern):** Centralized object creation via `GameFactory` featuring iconic heroes and bosses from *Final Fantasy VII* and *Final Fantasy X* (Cloud, Barret, Tidus, Yuna, Wakka, Sephiroth, Jenova, Rufus, Seymour, Yunalesca, Sinh, and Aeris).
* **Advanced Status Effect Engine:** Dynamic management of periodic and disabling conditions like **Poison** (damage over time), **Sleep**, and **Paralysis** (turn-skipping). Uses the Prototype Pattern (copy constructors) to safely instantiate effects without reference pollution.
* **Hierarchical JRPG Menus:** Categorized command navigation allowing players to switch between Physical Abilities, Black Magic, White Magic, and Inventory items, complete with full back-navigation support (`-1`).
* **Status-Healing Support Items & Spells:** Dedicated items (*Antidote*, *Alarm Clock*, *Remedy*) and the white magic spell *Esna* to cleanse character debuffs dynamically.
* **Enhanced Terminal UI & HUD:** Side-by-side dashboard layout displaying live HP/MP pools enclosed in ASCII borders, paired with artificial pacing delays (`Thread.sleep`) to prevent text flooding.
* **Autonomous AI Enemy:** Opponents make tactical decisions using a pseudo-random weighted system between basic attacks and specialized skill sets.
* **Robust Input Validation:** Error-resistant CLI controls that safely catch invalid formats and allow smooth menu cancellation without penalizing or skipping the player's turn.

---

## 🧱 Concepts and Design Patterns Applied

* **Single Responsibility Principle (SRP):** Every class has a distinct, isolated purpose—controllers coordinate, views render, factories instantiate, and models compute.
* **Factory Pattern:** Centralizes character assembly, mapping unique IDs to fully equipped entities with specialized skill sets and items.
* **Encapsulation & Delegation:** Private attributes protected via getters and setters, delegating mathematical state updates directly to domain models.
* **Composition:** `Character` models establish robust "has-a" relationships by managing collections (`ArrayList`) of `Skill`, `Item`, and `StatusEffect` objects.
* **Prototype Pattern:** Leveraged via copy constructors in `StatusEffect` to clone debuffs safely upon application.

---

## 📂 Project Structure & Class Summary

```text
src/
└── ffbattlesystem/
    ├── FFBattleSystem.java   # Controller: Orchestrates turn loops and game state
    ├── TerminalUI.java       # View: Handles formatted ASCII HUD, pacing, and CLI inputs
    ├── GameFactory.java      # Factory: Instantiates and equips the complete roster
    ├── Character.java        # Model: Core fighter entity managing stats, inventory, and status updates
    ├── Skill.java            # Model: Represents abilities and spells linked to a SkillType
    ├── SkillType.java        # Enum: Categorizes skills into Physical, Black Magic, or White Magic
    ├── StatusEffect.java     # Model: Manages temporary battle debuffs, damage over time, and turn skips
    └── Item.java             # Model: Template for consumable items and quantity tracking
## 🗺️ Development Roadmap

- [x] **Phase 1:** Core turn-based battle loop, stats, and basic physical attack engine (**v1.0**)
- [x] **Phase 1.5:** Dynamic skill lists and basic healing logic (**v1.1**)
- [x] **Phase 2:** Inventory management and consumable items integration (**v2.0**)
- [x] **Phase 3:** Architectural refactoring (**MVC**), Factory Pattern, and expanded roster (**v2.0**)
- [x] **Phase 4:** Status effect engine (**Poison, Sleep, Paralysis**), **ESNA**, status-clearing items, and hierarchical sub-menus with back-navigation support (**v3.0**)
- [ ] **Phase 5:** Advanced combat functions (**Speed stats, Limit Breaks / Overdrive mechanics**)
- [ ] **Phase 6:** Graphical User Interface (**GUI migration from TerminalUI to JavaFX/Swing**)

---

## 🎮 How to Play & Execute

### 1️⃣ Clone this repository

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git
```

### 2️⃣ Navigate to the project directory

```bash
cd YOUR_REPOSITORY_NAME
```

### 3️⃣ Compile all Java source files

```bash
javac *.java
```

### 4️⃣ Run the simulation

```bash
java FFBattleSystem
```

### 5️⃣ Start your adventure

Follow the terminal prompts to:

- Select your fighter (**IDs 1–12**)
- Choose your opponent
- Navigate through:
  - ⚔️ Physical Abilities
  - 🔥 Black Magic
  - ✨ White Magic
  - 🎒 Inventory Items

Defeat your enemy and claim victory!

---

## 👨‍💻 Author

**Developed by Dani**
