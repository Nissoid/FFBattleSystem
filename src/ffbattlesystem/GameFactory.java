/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

public class GameFactory {

    private Item getPotion() {
        return new Item("Potion", 30, 3, true);
    }

    private Item getEther() {
        return new Item("Ether", 40, 2, false);
    }

    private Item getAntidote() {
        return new Item("Antidote", 0, 3, true);
    }

    private Item getAlarmClock() {
        return new Item("Alarm Clock", 0, 2, true);
    }

    private Item getRemedy() {
        return new Item("Remedy", 0, 1, true);
    }

    private StatusEffect getPoisonEffect() {
        return new StatusEffect("Poison", 15, 3, false);
    }

    private StatusEffect getSleepEffect() {
        return new StatusEffect("Sleep", 0, 2, true);
    }

    private StatusEffect getParalysisEffect() {
        return new StatusEffect("Paralysis", 0, 1, true);
    }

    public Character createCharacterById(int id) {
        switch (id) {
            case 1:
                return createCloud();
            case 2:
                return createBarret();
            case 3:
                return createTidus();
            case 4:
                return createYuna();
            case 5:
                return createWakka();
            case 6:
                return createSephiroth();
            case 7:
                return createJenova();
            case 8:
                return createRufus();
            case 9:
                return createSeymour();
            case 10:
                return createYunalesca();
            case 11:
                return createSinh();
            case 12:
                return createAeris();
            default:
                return createCloud();
        }
    }

    private Skill getCommonSkill(String skillName) {
        switch (skillName.toLowerCase()) {
            case "fire":
                return new Skill("Fire", 35, 12, SkillType.BLACK_MAGIC);
            case "blizzard":
                return new Skill("Blizzard", 35, 12, SkillType.BLACK_MAGIC);
            case "thunder":
                return new Skill("Thunder", 45, 18, SkillType.BLACK_MAGIC);
            case "ice":
                return new Skill("Ice", 30, 10, SkillType.BLACK_MAGIC);
            case "cure":
                return new Skill("Cure", 30, 10, SkillType.WHITE_MAGIC);
            case "esna":
                return new Skill("Esna", 0, 15, SkillType.WHITE_MAGIC);
            default:
                return new Skill("Attack", 10, 0, SkillType.PHYSICAL);
        }
    }

    public Character createTidus() {
        Character tidus = new Character("Tidus", 120, 40, 35, 15, 45);
        
        tidus.learnSkill(new Skill("Quick Hit", 30, 10, SkillType.PHYSICAL));
        tidus.learnSkill(new Skill("Spiral Cut", 50, 15, SkillType.PHYSICAL));
        tidus.learnSkill(new Skill("Jecht Shot", 75, 25, SkillType.PHYSICAL));
        
        tidus.addItem(getPotion());
        tidus.addItem(getEther());
        tidus.addItem(getAntidote());
        
        return tidus;
    }

    public Character createYuna() {
        Character yuna = new Character("Yuna", 90, 150, 10, 15, 25);
        
        yuna.learnSkill(getCommonSkill("Cure"));
        yuna.learnSkill(getCommonSkill("Esna"));
        yuna.learnSkill(getCommonSkill("Fire"));
        yuna.learnSkill(getCommonSkill("Blizzard"));
        yuna.learnSkill(new Skill("Holy", 80, 40, SkillType.WHITE_MAGIC));
        
        yuna.addItem(getPotion());
        yuna.addItem(getEther());
        yuna.addItem(getRemedy());
        
        return yuna;
    }

    public Character createWakka() {
        Character wakka = new Character("Wakka", 130, 30, 40, 20, 30);
        
        wakka.learnSkill(new Skill("Dark Attack", 45, 10, SkillType.PHYSICAL));
        wakka.learnSkill(new Skill("Sleep Attack", 35, 15, SkillType.PHYSICAL, getSleepEffect()));
        wakka.learnSkill(new Skill("Aurochs Spirit", 85, 25, SkillType.PHYSICAL));
        
        wakka.addItem(getPotion());
        wakka.addItem(getEther());
        wakka.addItem(getAlarmClock());
        
        return wakka;
    }

    public Character createSeymour() {
        Character seymour = new Character("Seymour", 270, 150, 45, 22, 45);
        
        seymour.learnSkill(new Skill("Blizzara", 70, 20, SkillType.BLACK_MAGIC));
        seymour.learnSkill(new Skill("Firaga", 100, 35, SkillType.BLACK_MAGIC));
        seymour.learnSkill(new Skill("Death Touch", 60, 30, SkillType.BLACK_MAGIC, getPoisonEffect()));
        
        seymour.addItem(getPotion());
        seymour.addItem(getRemedy());
        
        return seymour;
    }

    public Character createYunalesca() {
        Character yunalesca = new Character("Yunalesca", 320, 180, 40, 30, 35);
        
        yunalesca.learnSkill(new Skill("Hellbiter", 65, 25, SkillType.BLACK_MAGIC, getPoisonEffect()));
        yunalesca.learnSkill(new Skill("Mega Death", 130, 50, SkillType.BLACK_MAGIC));
        
        yunalesca.addItem(getPotion());
        return yunalesca;
    }

    public Character createSinh() {
        Character sinh = new Character("Sinh", 500, 200, 70, 40, 20);
        
        sinh.learnSkill(new Skill("Gravity Wave", 100, 30, SkillType.BLACK_MAGIC));
        sinh.learnSkill(new Skill("Terror Roar", 60, 35, SkillType.PHYSICAL, getParalysisEffect()));
        
        sinh.addItem(getPotion());
        
        return sinh;
    }

    public Character createCloud() {
        Character cloud = new Character("Cloud", 150, 50, 45, 20, 35);
        
        cloud.learnSkill(new Skill("Cross Slash", 40, 20, SkillType.PHYSICAL, getParalysisEffect()));
        cloud.learnSkill(new Skill("Omnislash", 120, 60, SkillType.PHYSICAL));
        
        cloud.learnSkill(getCommonSkill("Cure"));
        cloud.learnSkill(getCommonSkill("Esna"));
        cloud.learnSkill(getCommonSkill("Fire"));
        
        cloud.addItem(getPotion());
        cloud.addItem(getEther());
        cloud.addItem(getRemedy());
        
        return cloud;
    }

    public Character createSephiroth() {
        Character sephiroth = new Character("Sephiroth", 300, 100, 55, 25, 50);
        
        sephiroth.learnSkill(new Skill("Octaslash", 75, 20, SkillType.PHYSICAL));
        sephiroth.learnSkill(new Skill("Shadow Flare", 90, 40, SkillType.BLACK_MAGIC));
        sephiroth.learnSkill(new Skill("Bio", 30, 15, SkillType.BLACK_MAGIC, getPoisonEffect()));
        
        sephiroth.addItem(getPotion());
        sephiroth.addItem(getRemedy());
        
        return sephiroth;
    }

    public Character createJenova() {
        Character jenova = new Character("Jenova", 280, 120, 50, 20, 40);
        
        jenova.learnSkill(new Skill("Laser", 70, 20, SkillType.BLACK_MAGIC));
        jenova.learnSkill(new Skill("Hypnotic Gas", 20, 25, SkillType.BLACK_MAGIC, getSleepEffect()));
        jenova.learnSkill(new Skill("Bio", 40, 15, SkillType.BLACK_MAGIC, getPoisonEffect()));
        
        jenova.addItem(getPotion());
        
        return jenova;
    }

    public Character createRufus() {
        Character rufus = new Character("Rufus Shinra", 220, 80, 45, 18, 65);

        rufus.learnSkill(new Skill("Dark Nation Assault", 50, 25, SkillType.PHYSICAL, getParalysisEffect()));
        rufus.learnSkill(new Skill("Shotgun Blast", 75, 20, SkillType.PHYSICAL));

        rufus.addItem(getPotion());
        rufus.addItem(getAntidote());

        return rufus;
    }

    public Character createBarret() {
        Character barret = new Character("Barret", 200, 20, 35, 35, 15);
        
        barret.learnSkill(new Skill("Big Shot", 45, 10, SkillType.PHYSICAL));
        barret.learnSkill(new Skill("Catastrophe", 95, 35, SkillType.PHYSICAL));

        barret.addItem(getPotion());
        barret.addItem(getAlarmClock());

        return barret;
    }

    public Character createAeris() {
        Character aeris = new Character("Aeris", 130, 200, 30, 18, 40);

        aeris.learnSkill(new Skill("Healing Wind", 40, 12, SkillType.WHITE_MAGIC)); // Heal skill treated as white magic
        aeris.learnSkill(new Skill("Great Gospel", 120, 50, SkillType.WHITE_MAGIC)); // Ultimate support/heal

        aeris.learnSkill(getCommonSkill("Cure"));
        aeris.learnSkill(getCommonSkill("Esna"));

        aeris.learnSkill(getCommonSkill("Fire"));
        aeris.learnSkill(getCommonSkill("Thunder"));

        aeris.addItem(getPotion());
        aeris.addItem(getEther());
        aeris.addItem(getRemedy());

        return aeris;
    }
}
