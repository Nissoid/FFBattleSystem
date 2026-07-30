# Final Fantasy Turn-Based Battle System (Java GUI v4.0) ⚔️

An advanced, object-oriented JRPG combat system inspired by Final Fantasy, developed in Java following clean architecture principles (MVC, Factory Pattern, Prototype Pattern, and SOLID principles), now featuring a fully interactive Java Swing desktop graphical interface.

---

## 🚀 Key Features

### Clean MVC Architecture
Total separation between business logic (**Models**), the desktop graphical interface (**SwingUI View**), and flow coordination (**FFBattleSystem Controller**).

### Interactive Java Swing GUI
Replaced the text-based CLI with a modern desktop window featuring:

- Real-time HUD dashboard
- Live HP/MP pools
- Visual Limit/Overdrive gauges
- Interactive modal selection dialogs
- Dynamic combat logs

### Dynamic ATB Combat System
Implements an **Active Time Battle (ATB)** engine where turn frequency is dictated by each combatant's Speed statistic.

### Advanced Mathematical & Combat Engine
Features a complete RPG stat system with:

- `physAttack`
- `magicAttack`
- `physDefense`
- `magicDefense`
- `speed`
- `luck`

Includes a critical hit system scaled by the character's Luck attribute.

### Reactive Limit/Overdrive & Dual Ultimate Systems
Characters build a Limit Gauge (0%–100%) while receiving damage, unlocking:

- **Final Fantasy X Grand Summons / Aeons**
- **Final Fantasy VII Limit Breaks**
- Ultimate Materia abilities

### Extended Character Roster (Factory Pattern)
Centralized character creation through `GameFactory`, featuring iconic heroes and villains from Final Fantasy VII and Final Fantasy X:

| Heroes | Bosses |
|------|------|
| Cloud | Sephiroth |
| Barret | Jenova |
| Tidus | Rufus |
| Yuna | Seymour |
| Wakka | Yunalesca |
| Aeris | Sinh |

### Advanced Status Effect Engine

Supports dynamic status ailments such as:

- Poison (Damage over Time)
- Sleep (Turn Disable)
- Paralysis (Chance to Skip Turn)

Implemented using the **Prototype Pattern** through copy constructors to safely instantiate effects without shared references.

### Hierarchical JRPG Menus

Categorized command navigation:

- ⚔️ Physical Skills
- 🔥 Black Magic
- ✨ White Magic
- 🎒 Inventory
- 🐉 Summons / Aeons
- 💥 Limit Breaks

### Status-Healing Support Items & Spells

Support system includes:

- Antidote
- Alarm Clock
- Remedy
- Esna (White Magic)

### Autonomous Enemy AI

Enemies utilize a weighted pseudo-random decision system capable of:

- Selecting optimal attacks
- Prioritizing Limit abilities
- Dynamically reacting to combat states

---

## 🧱 Applied Concepts & Design Patterns

| Concept | Description |
|--------|-------------|
| MVC Pattern | Separation between Models, Views, and Controllers. |
| Factory Pattern | Centralized creation of playable characters and bosses. |
| Prototype Pattern | Safe cloning of status effects through copy constructors. |
| SOLID Principles | Focus on maintainability and extensibility. |
| SRP | Every class has one clearly defined responsibility. |
| Encapsulation | Internal state protected through getters and setters. |
| Delegation | Mathematical calculations delegated to domain models. |
| Composition | Characters own collections of Skills, Items, and Status Effects. |

---

## 📂 Project Structure

```plaintext
src/
└── ffbattlesystem/
    ├── FFBattleSystem.java
    ├── SwingUI.java
    ├── GameFactory.java
    ├── Character.java
    ├── Skill.java
    ├── SkillType.java
    ├── StatusEffect.java
    └── Item.java
```

### Class Responsibilities

| Class | Responsibility |
|------|------|
| `FFBattleSystem` | Controller responsible for ATB loops, turn management, and GUI interactions. |
| `SwingUI` | Java Swing graphical interface handling HUD, logs, and dialogs. |
| `GameFactory` | Creates and equips all fighters. |
| `Character` | Core entity managing stats, inventory, limits, and status effects. |
| `Skill` | Represents combat abilities and spells. |
| `SkillType` | Enum categorizing Physical, Black Magic, and White Magic. |
| `StatusEffect` | Handles temporary effects and turn modifiers. |
| `Item` | Represents consumables and quantity tracking. |

---

## 🗺️ Development Roadmap

| Phase | Description | Version |
|------|-------------|---------|
| Phase 1 | Turn-based battle loop and physical attacks | v1.0 |
| Phase 1.5 | Dynamic skills and healing logic | v1.1 |
| Phase 2 | Inventory and consumable integration | v2.0 |
| Phase 3 | MVC refactor, Factory Pattern, expanded roster | v2.0 |
| Phase 4 | Status effects, ESNA, and hierarchical menus | v3.0 |
| Phase 5 | Speed stats and Limit/Overdrive mechanics | v3.5 |
| Phase 6 | Migration from TerminalUI to Java Swing GUI | v4.0 |

---

## 🎮 How to Play

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git
```

### 2. Navigate to the project directory

```bash
cd YOUR_REPOSITORY_NAME
```

### 3. Compile the source files

```bash
javac ffbattlesystem/*.java
```

### 4. Run the application

```bash
java ffbattlesystem.FFBattleSystem
```

### 5. Begin Your Adventure!

Once launched, a Java Swing window will automatically open.

Players can:

- Select their fighter (IDs 1–12)
- Choose an opponent
- Monitor HP, MP, and Limit gauges
- Execute skills through graphical menus
- Utilize items and healing spells
- Summon Aeons and activate Limit Breaks
- Defeat legendary enemies and achieve victory

---

## 🎮 Available Commands

| Category | Description |
|---------|-------------|
| ⚔️ Physical Skills | Weapon techniques and melee attacks. |
| 🔥 Black Magic | Offensive magic abilities. |
| ✨ White Magic | Healing and support spells. |
| 🎒 Inventory | Consumable items and status cures. |
| 🐉 Grand Summons | FFX Aeons and character substitutions. |
| 💥 Limit Breaks | Powerful ultimate abilities. |

---

## 🛠️ Technologies Used

- Java 21+
- Java Swing
- Object-Oriented Programming (OOP)
- MVC Architecture
- Factory Pattern
- Prototype Pattern
- SOLID Principles
- ArrayLists & Collections Framework

---

## 🔮 Future Improvements

- Save & Load system.
- Additional playable characters.
- Multiple enemy encounters.
- Equipment system.
- Experience and leveling mechanics.
- Sound effects and background music.
- Animated combat sequences.
- Migration to JavaFX.
- Multiplayer PvP mode.

---

## 👨‍💻 Author

Developed with passion by **Dani**

> "The legacy of Final Fantasy lives on—one turn at a time."
