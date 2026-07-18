/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

/**
 * Responsible for creating and assembling game data. Centralizes the creation
 * of characters, skills, and base items (Factory Pattern).
 *
 * @author Dani
 */
public class GameFactory {

    // --- REUSABLE BASE ITEMS ---
    private Item getPotion() {
        return new Item("Potion", 30, 3, true);
    }

    private Item getEther() {
        return new Item("Ether", 40, 2, false);
    }
    
    public Character createCharacterById(int id) {
        switch (id) {
            case 1: return createCloud();
            case 2: return createBarret();
            case 3: return createTidus();
            case 4: return createYuna();
            case 5: return createWakka();
            case 6: return createSephiroth();
            default:
                // Fallback de seguridad: si el usuario pone un 9, le damos a Cloud por defecto.
                System.err.println("Invalid ID. Defaulting to Cloud.");
                return createCloud(); 
        }
    }
    
    private Skill getCommonSkill(String skillName) {
        switch (skillName.toLowerCase()) {
            case "fire":
                return new Skill("Fire", 35, 12, false);
            case "blizzard":
                return new Skill("Blizzard", 35, 12, false);
            case "thunder":
                return new Skill("Thunder", 45, 18, false);
            case "ice":
                return new Skill ("Ice", 30, 10, false);
            case "cure":
                return new Skill("Cure", 30, 10, true);
            default:
                // Fallback de seguridad por si escribes mal el nombre
                System.err.println("Skill not found in catalog: " + skillName);
                return new Skill("Attack", 10, 0, false);
        }
    }

    // --- FINAL FANTASY X ROSTER ---
    public Character createTidus() {
        // Stats: Name, MaxHP, MaxMP, Attack, Defense, Speed
        Character tidus = new Character("Tidus", 120, 40, 35, 15, 45);

        tidus.learnSkill(new Skill("Quick Hit", 30, 10, false));
        tidus.learnSkill(new Skill("Spiral Cut", 50, 15, false));
        tidus.learnSkill(new Skill("Jecht Shot", 75, 25, false));

        tidus.addItem(getPotion());
        tidus.addItem(getEther());

        return tidus;
    }

    public Character createYuna() {
        Character yuna = new Character("Yuna", 90, 150, 10, 15, 25);

        // White Magic
        yuna.learnSkill(getCommonSkill("Cure"));

        // Black Magic
        yuna.learnSkill(getCommonSkill("Fire"));
        yuna.learnSkill(getCommonSkill("Blizzard"));
        yuna.learnSkill(getCommonSkill("Thunder"));
        yuna.learnSkill(getCommonSkill("Ice"));
        
        // Ultimate
        yuna.learnSkill(new Skill("Holy", 80, 40, false));

        yuna.addItem(getPotion());
        yuna.addItem(getEther());

        return yuna;
    }

    public Character createWakka() {
        Character wakka = new Character("Wakka", 130, 30, 40, 20, 30);

        wakka.learnSkill(new Skill("Dark Attack", 45, 10, false));
        wakka.learnSkill(new Skill("Silence Attack", 45, 10, false));
        wakka.learnSkill(new Skill("Sleep Attack", 45, 10, false));
        wakka.learnSkill(new Skill("Aurochs Spirit", 85, 25, false));

        wakka.addItem(getPotion());
        wakka.addItem(getEther());

        return wakka;
    }

    // --- FINAL FANTASY VII ROSTER ---
    public Character createCloud() {
        Character cloud = new Character("Cloud", 150, 50, 45, 20, 35);

        cloud.learnSkill(new Skill("Cross Slash", 50, 20, false));
        cloud.learnSkill(new Skill("Blade Beam", 65, 30, false));
        cloud.learnSkill(new Skill("Climhazzard", 80, 40, false));
        cloud.learnSkill(new Skill("Omnislash", 120, 60, false));
        
        // White Magic
        cloud.learnSkill(getCommonSkill("Cure"));
        
        // Black Magic
        cloud.learnSkill(getCommonSkill("Fire"));
        cloud.learnSkill(getCommonSkill("Blizzard"));
        cloud.learnSkill(getCommonSkill("Thunder"));
        cloud.learnSkill(getCommonSkill("Ice"));

        cloud.addItem(getPotion());
        cloud.addItem(getEther());

        return cloud;
    }

    public Character createSephiroth() {
        Character sephiroth = new Character("Sephiroth", 300, 100, 55, 25, 50);

        sephiroth.learnSkill(new Skill("Octaslash", 75, 20, false));
        sephiroth.learnSkill(new Skill("Shadow Flare", 90, 40, false));
        sephiroth.learnSkill(new Skill("Heartless Angel", 100, 50, false));
        sephiroth.learnSkill(new Skill("Supernova", 150, 80, false));

        sephiroth.learnSkill(new Skill("Dark Flame", 55, 25, false));
        sephiroth.learnSkill(new Skill("Quake", 60, 30, false));

        sephiroth.addItem(getPotion());

        return sephiroth;
    }

    public Character createBarret() {
        Character barret = new Character("Barret", 200, 20, 35, 35, 15);

        barret.learnSkill(new Skill("Big Shot", 45, 10, false));
        barret.learnSkill(new Skill("Mindblow", 30, 15, false));
        barret.learnSkill(new Skill("Catastrophe", 95, 35, false));

        barret.addItem(getPotion());

        return barret;
    }
}
