package ffbattlesystem;

/**
 * Factory class responsible for assembling characters, skills, and items.
 * Centralizes data creation (Factory Design Pattern) to keep the core logic
 * clean.
 */
public class GameFactory {

    // ==========================================
    //           REUSABLE BASE ITEMS
    // ==========================================
    private Item getPotion() {
        return new Item("Potion", 50, 3, true);
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

    // ==========================================
    //           REUSABLE STATUS EFFECTS
    // ==========================================
    private StatusEffect getPoisonEffect() {
        return new StatusEffect("Poison", 15, 3, false);
    }

    private StatusEffect getSleepEffect() {
        return new StatusEffect("Sleep", 0, 2, true);
    }

    private StatusEffect getParalysisEffect() {
        return new StatusEffect("Paralysis", 0, 1, true);
    }

    // ==========================================
    //           CHARACTER ROUTER
    // ==========================================
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

    // ==========================================
    //           COMMON SKILLS
    // ==========================================
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
                return new Skill("Cure", 40, 10, SkillType.WHITE_MAGIC);
            case "esna":
                return new Skill("Esna", 0, 15, SkillType.WHITE_MAGIC);
            default:
                return new Skill("Attack", 10, 0, SkillType.PHYSICAL);
        }
    }

    // ==========================================
    //           AEONS (YUNA'S SUMMONS)
    // ==========================================
    private Character createValefor() {
        Character valefor = new Character("Valefor", 250, 80, 45, 45, 25, 30, 55, 20);
        valefor.learnSkill(new Skill("Sonic Wings", 40, 10, SkillType.PHYSICAL));
        valefor.learnSkill(new Skill("Aero", 45, 15, SkillType.BLACK_MAGIC));
        // --- OVERDRIVE ---
        valefor.setLimitSkill(new Skill("Energy Ray", 100, 0, SkillType.BLACK_MAGIC));
        return valefor;
    }

    private Character createIfrit() {
        Character ifrit = new Character("Ifrit", 400, 50, 65, 30, 50, 25, 20, 10);
        ifrit.learnSkill(new Skill("Meteor Strike", 60, 15, SkillType.PHYSICAL));
        ifrit.learnSkill(new Skill("Fira", 50, 15, SkillType.BLACK_MAGIC));
        // --- OVERDRIVE ---
        ifrit.setLimitSkill(new Skill("Hellfire", 140, 0, SkillType.BLACK_MAGIC));
        return ifrit;
    }

    private Character createShiva() {
        Character shiva = new Character("Shiva", 200, 120, 35, 60, 20, 55, 60, 25);
        shiva.learnSkill(new Skill("Heavenly Strike", 40, 15, SkillType.PHYSICAL, getSleepEffect()));
        shiva.learnSkill(new Skill("Blizzara", 50, 15, SkillType.BLACK_MAGIC));
        // --- OVERDRIVE ---
        shiva.setLimitSkill(new Skill("Diamond Dust", 130, 0, SkillType.BLACK_MAGIC));
        return shiva;
    }

    private Character createBahamut() {
        Character bahamut = new Character("Bahamut", 600, 150, 80, 80, 60, 60, 45, 15);
        bahamut.learnSkill(new Skill("Impulse", 70, 20, SkillType.PHYSICAL));
        bahamut.learnSkill(new Skill("Flare", 80, 30, SkillType.BLACK_MAGIC));
        // --- OVERDRIVE ---
        bahamut.setLimitSkill(new Skill("Mega Flare", 200, 0, SkillType.BLACK_MAGIC));
        return bahamut;
    }

    // ==========================================
    //           FINAL FANTASY X ROSTER
    // ==========================================
    public Character createTidus() {
        Character tidus = new Character("Tidus", 120, 40, 40, 15, 18, 15, 45, 25);
        tidus.learnSkill(new Skill("Quick Hit", 25, 10, SkillType.PHYSICAL));
        tidus.learnSkill(new Skill("Spiral Cut", 50, 15, SkillType.PHYSICAL));
        tidus.addItem(getPotion());
        tidus.addItem(getEther());
        tidus.addItem(getAntidote());
        // --- OVERDRIVE ---
        tidus.setLimitSkill(new Skill("Blitz Ace", 110, 0, SkillType.PHYSICAL));
        return tidus;
    }

    public Character createYuna() {
        Character yuna = new Character("Yuna", 90, 150, 5, 50, 12, 45, 25, 15);
        yuna.learnSkill(getCommonSkill("Cure"));
        yuna.learnSkill(getCommonSkill("Esna"));
        yuna.learnSkill(getCommonSkill("Fire"));
        yuna.learnSkill(getCommonSkill("Blizzard"));
        yuna.addItem(getPotion());
        yuna.addItem(getEther());
        yuna.addItem(getRemedy());

        yuna.addSummon(createValefor());
        yuna.addSummon(createIfrit());
        yuna.addSummon(createShiva());
        yuna.addSummon(createBahamut());

        // --- OVERDRIVE ---
        yuna.setLimitSkill(new Skill("Holy Burst", 130, 0, SkillType.WHITE_MAGIC));
        return yuna;
    }

    public Character createWakka() {
        Character wakka = new Character("Wakka", 130, 30, 42, 10, 20, 15, 30, 30);
        wakka.learnSkill(new Skill("Dark Attack", 45, 10, SkillType.PHYSICAL));
        wakka.learnSkill(new Skill("Sleep Attack", 35, 15, SkillType.PHYSICAL, getSleepEffect()));
        wakka.addItem(getPotion());
        wakka.addItem(getEther());
        wakka.addItem(getAlarmClock());
        // --- OVERDRIVE ---
        wakka.setLimitSkill(new Skill("Attack Reels", 105, 0, SkillType.PHYSICAL));
        return wakka;
    }

    public Character createSeymour() {
        Character seymour = new Character("Seymour", 270, 150, 20, 60, 20, 45, 45, 10);
        seymour.learnSkill(new Skill("Blizzara", 70, 20, SkillType.BLACK_MAGIC));
        seymour.learnSkill(new Skill("Death Touch", 60, 30, SkillType.BLACK_MAGIC, getPoisonEffect()));
        seymour.addItem(getPotion());
        seymour.addItem(getRemedy());
        // --- OVERDRIVE ---
        seymour.setLimitSkill(new Skill("Requiem", 135, 0, SkillType.BLACK_MAGIC));
        return seymour;
    }

    public Character createYunalesca() {
        Character yunalesca = new Character("Yunalesca", 320, 180, 25, 65, 25, 50, 35, 15);
        yunalesca.learnSkill(new Skill("Hellbiter", 65, 25, SkillType.BLACK_MAGIC, getPoisonEffect()));
        yunalesca.learnSkill(new Skill("Osmose", 40, 10, SkillType.BLACK_MAGIC));
        yunalesca.addItem(getPotion());
        // --- OVERDRIVE ---
        yunalesca.setLimitSkill(new Skill("Mega Death", 160, 0, SkillType.BLACK_MAGIC));
        return yunalesca;
    }

    public Character createSinh() {
        Character sin = new Character("Sinh", 500, 200, 65, 60, 40, 40, 20, 5);
        sin.learnSkill(new Skill("Gravity Wave", 80, 30, SkillType.BLACK_MAGIC));
        sin.learnSkill(new Skill("Terror Roar", 60, 35, SkillType.PHYSICAL, getParalysisEffect()));
        sin.addItem(getPotion());
        // --- OVERDRIVE ---
        sin.setLimitSkill(new Skill("Giga-Graviton", 220, 0, SkillType.BLACK_MAGIC));
        return sin;
    }

    // ==========================================
    //           FINAL FANTASY VII ROSTER
    // ==========================================
    public Character createCloud() {
        Character cloud = new Character("Cloud", 150, 50, 45, 25, 22, 18, 35, 20);
        cloud.learnSkill(new Skill("Cross Slash", 40, 20, SkillType.PHYSICAL, getParalysisEffect()));
        cloud.learnSkill(new Skill("Braver", 60, 25, SkillType.PHYSICAL));
        cloud.learnSkill(getCommonSkill("Cure"));
        cloud.learnSkill(getCommonSkill("Esna"));
        cloud.learnSkill(getCommonSkill("Fire"));
        cloud.addItem(getPotion());
        cloud.addItem(getEther());
        cloud.addItem(getRemedy());

        // --- LIMIT BREAK ---
        cloud.setLimitSkill(new Skill("Omnislash", 150, 0, SkillType.PHYSICAL));
        // --- MATERIA SUMMON ---
        cloud.setFf7Summon(new Skill("Bahamut ZERO", 180, 0, SkillType.BLACK_MAGIC));
        return cloud;
    }

    public Character createBarret() {
        Character barret = new Character("Barret", 220, 20, 55, 5, 35, 10, 15, 10);
        barret.learnSkill(new Skill("Big Shot", 45, 10, SkillType.PHYSICAL));
        barret.learnSkill(new Skill("Mindblow", 30, 15, SkillType.PHYSICAL));
        barret.addItem(getPotion());
        barret.addItem(getAlarmClock());

        // --- LIMIT BREAK ---
        barret.setLimitSkill(new Skill("Catastrophe", 130, 0, SkillType.PHYSICAL));
        // --- MATERIA SUMMON ---
        barret.setFf7Summon(new Skill("Alexander", 140, 0, SkillType.BLACK_MAGIC));
        return barret;
    }

    public Character createAeris() {
        Character aeris = new Character("Aeris", 130, 200, 10, 55, 15, 55, 40, 25);
        aeris.learnSkill(new Skill("Healing Wind", 40, 12, SkillType.WHITE_MAGIC));
        aeris.learnSkill(getCommonSkill("Cure"));
        aeris.learnSkill(getCommonSkill("Esna"));
        aeris.learnSkill(getCommonSkill("Fire"));
        aeris.learnSkill(getCommonSkill("Thunder"));
        aeris.addItem(getPotion());
        aeris.addItem(getEther());
        aeris.addItem(getRemedy());

        // --- LIMIT BREAK ---
        aeris.setLimitSkill(new Skill("Great Gospel", 160, 0, SkillType.WHITE_MAGIC));
        // --- MATERIA SUMMON ---
        aeris.setFf7Summon(new Skill("Phoenix", 120, 0, SkillType.WHITE_MAGIC));
        return aeris;
    }

    public Character createSephiroth() {
        Character sephiroth = new Character("Sephiroth", 300, 100, 60, 55, 25, 30, 50, 20);
        sephiroth.learnSkill(new Skill("Octaslash", 75, 20, SkillType.PHYSICAL));
        sephiroth.learnSkill(new Skill("Bio", 30, 15, SkillType.BLACK_MAGIC, getPoisonEffect()));
        sephiroth.addItem(getPotion());
        sephiroth.addItem(getRemedy());

        // --- LIMIT BREAK ---
        sephiroth.setLimitSkill(new Skill("Supernova", 180, 0, SkillType.BLACK_MAGIC));
        return sephiroth;
    }

    public Character createJenova() {
        Character jenova = new Character("Jenova", 280, 120, 35, 60, 20, 40, 40, 10);
        jenova.learnSkill(new Skill("Laser", 70, 20, SkillType.BLACK_MAGIC));
        jenova.learnSkill(new Skill("Hypnotic Gas", 20, 25, SkillType.BLACK_MAGIC, getSleepEffect()));
        jenova.learnSkill(new Skill("Bio", 40, 15, SkillType.BLACK_MAGIC, getPoisonEffect()));
        jenova.addItem(getPotion());

        // --- LIMIT BREAK ---
        jenova.setLimitSkill(new Skill("Destruction Light", 140, 0, SkillType.BLACK_MAGIC));
        return jenova;
    }

    public Character createRufus() {
        Character rufus = new Character("Rufus Shinra", 220, 80, 50, 15, 20, 20, 60, 15);
        rufus.learnSkill(new Skill("Dark Nation Assault", 50, 25, SkillType.PHYSICAL, getParalysisEffect()));
        rufus.learnSkill(new Skill("Shotgun Blast", 75, 20, SkillType.PHYSICAL));
        rufus.addItem(getPotion());
        rufus.addItem(getAntidote());

        // --- LIMIT BREAK ---
        rufus.setLimitSkill(new Skill("Mako Cannon Strike", 125, 0, SkillType.PHYSICAL));
        return rufus;
    }
}
