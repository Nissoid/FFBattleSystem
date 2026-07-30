Final Fantasy Turn-Based Battle System (Java GUI v4.0) ⚔️
An advanced, object-oriented JRPG combat system inspired by Final Fantasy, developed in Java following clean architecture principles (MVC pattern, Factory design pattern, and SOLID principles), now featuring a fully interactive Java Swing desktop graphical interface.

🚀 Key Features
Clean MVC Architecture: Total separation between business logic (Models), the desktop graphical interface (SwingUI View), and flow coordination (FFBattleSystem Controller).

Interactive Java Swing GUI: Replaced text-based CLI with a modern desktop window featuring real-time HUD dashboard, live HP/MP pools, visual limit gauges, and modal selection dialogs.

Dynamic ATB Combat System: Active Time Battle (ATB) engine where turn frequency is dictated by each combatant's speed statistic.

Advanced Mathematical & Combat Engine: Separates physAttack, magicAttack, physDefense, and magicDefense to create distinct RPG roles, paired with an RNG critical hit system scaled against the luck attribute.

Reactive Limit/Overdrive & Dual Ultimate Systems: A charging bar (0% to 100%) that fills dynamically when taking damage, unlocking either FFX Grand Summons (character substitution) or FF7 Limit Breaks/Materia.

Extended Character Roster (Factory Pattern): Centralized object creation via GameFactory featuring iconic heroes and bosses from Final Fantasy VII and Final Fantasy X (Cloud, Barret, Tidus, Yuna, Wakka, Sephiroth, Jenova, Rufus, Seymour, Yunalesca, Sinh, and Aeris).

Advanced Status Effect Engine: Dynamic management of periodic and disabling conditions like Poison (damage over time), Sleep, and Paralysis (turn-skipping). Uses the Prototype Pattern (copy constructors) to safely instantiate effects without reference pollution.

Hierarchical JRPG Menus: Categorized command navigation allowing players to switch between Physical Abilities, Black Magic, White Magic, Inventory items, Summons, and Limits.

Status-Healing Support Items & Spells: Dedicated items (Antidote, Alarm Clock, Remedy) and the white magic spell Esna to cleanse character debuffs dynamically.

Autonomous AI Enemy: Opponents make tactical decisions using a pseudo-random weighted system, prioritizing ultimate attacks when their limit gauge is full.

🧱 Applied Concepts & Design Patterns
Single Responsibility Principle (SRP): Each class serves a distinct, isolated purpose: controllers coordinate, views render graphical elements, factories instantiate, and models compute math.

Factory Pattern: Centralizes character assembly, mapping unique IDs to fully equipped entities with specialized skill sets and inventories.

Encapsulation & Delegation: Private attributes are protected via getters/setters, delegating mathematical state updates directly to domain models.

Composition: Character models establish robust "has-a" relationships via collection management (ArrayList) of Skill, Item, and StatusEffect objects.

Prototype Pattern: Leveraged via copy constructors in StatusEffect to securely clone debuffs upon application.

📂 Project Structure & Class Summary
Plaintext
src/
└── ffbattlesystem/
    ├── FFBattleSystem.java   # Controller: Orchestrates ATB loops, turn states, and GUI events
    ├── SwingUI.java          # View: Desktop graphical interface managing HUD, logs, and dialogs
    ├── GameFactory.java      # Factory: Instantiates and equips the complete roster
    ├── Character.java        # Model: Core fighter entity managing stats, inventory, and status updates
    ├── Skill.java            # Model: Represents abilities and spells linked to a SkillType
    ├── SkillType.java        # Enum: Categorizes skills into Physical, Black Magic, or White Magic
    ├── StatusEffect.java     # Model: Manages temporary battle debuffs, damage over time, and turn skips
    └── Item.java             # Model: Template for consumable items and quantity tracking
🗺️ Development Roadmap
Phase 1: Main turn-based battle loop, stats, and basic physical attack engine (v1.0)

Phase 1.5: Dynamic skill lists and basic healing logic (v1.1)

Phase 2: Inventory management and consumable item integration (v2.0)

Phase 3: Architectural refactoring (MVC), Factory pattern, and expanded roster (v2.0)

Phase 4: Status effect engine (Poison, Sleep, Paralysis), ESNA, status-clearing items, and hierarchical submenus (v3.0)

Phase 5: Advanced combat features (speed stats, Limit Breaks/Overdrive mechanics) (v3.5)

Phase 6: Graphical User Interface (Migration from CLI TerminalUI to Java Swing GUI) (v4.0)

🎮 How to Play & Run
1️⃣ Clone this repository
Bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git
2️⃣ Navigate to the project directory
Bash
cd YOUR_REPOSITORY_NAME
3️⃣ Compile all Java source files
Bash
javac ffbattlesystem/*.java
4️⃣ Run the simulation
Bash
java ffbattlesystem.FFBattleSystem
5️⃣ Begin Your Adventure
A desktop graphical window will launch automatically. Follow the interactive dialog prompts to:

Select your fighter (IDs 1–12)

Choose your opponent

Navigate through graphical action buttons and menus:

⚔️ Physical Skills

🔥 Black Magic

✨ White Magic

🎒 Inventory Items

🐉 Grand Summons / Aeons

💥 Limit Breaks

Defeat your enemy and claim victory!

👨‍💻 Author
Developed by Dani
