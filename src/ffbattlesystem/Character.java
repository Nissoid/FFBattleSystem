package ffbattlesystem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Core domain model representing any combatant (Heroes, Bosses, and Aeons).
 * Manages stats, inventory, ATB speed, limit breaks, and FF7 Materia summons.
 */
public class Character {

    private String name;
    private int maxHp;
    private int currentHp;
    private int maxMp;
    private int currentMp;

    // --- COMBAT STATISTICS ---
    private int physAttack;   // Base physical damage output
    private int magicAttack;  // Base magical power (damage and healing)
    private int physDefense;  // Resistance against physical attacks
    private int magicDefense; // Resistance against magical attacks
    private int speed;        // Determines the frequency of turns (ATB system)
    private int luck;         // Percentage chance to land a critical hit

    // --- LIMIT BREAK & FF7 SUMMONS ---
    private int limitGauge;   // Overdrive/Limit bar (0 to 100)
    private Skill limitSkill; // The ultimate attack unlocked at 100% Limit
    private Skill ff7Summon;  // The ultimate magic summon unlocked at 100% Limit

    // --- COLLECTIONS ---
    private ArrayList<Item> items;
    private ArrayList<Skill> skills;
    private ArrayList<StatusEffect> activeStatuses;
    private ArrayList<Character> summons; // FFX Aeons (Substitute combatants)

    private Random random;

    public Character(String name, int maxHp, int maxMp, int physAttack, int magicAttack, int physDefense, int magicDefense, int speed, int luck) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.maxMp = maxMp;
        this.currentMp = maxMp;

        this.physAttack = physAttack;
        this.magicAttack = magicAttack;
        this.physDefense = physDefense;
        this.magicDefense = magicDefense;
        this.speed = speed;
        this.luck = luck;

        this.limitGauge = 0;
        this.limitSkill = null;
        this.ff7Summon = null;

        this.items = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.activeStatuses = new ArrayList<>();
        this.summons = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Performs a standard physical attack. Calculates critical hits based on
     * the Luck stat.
     */
    public String attack(Character target) {
        int damageDealt = this.physAttack - target.getPhysDefense();
        if (damageDealt < 1) {
            damageDealt = 1;
        }

        String log = this.name + " attacks " + target.getName() + "!\n";

        // Critical Hit Calculation (RNG roll against Luck stat)
        boolean isCrit = random.nextInt(100) < this.luck;
        if (isCrit) {
            damageDealt = (int) (damageDealt * 1.5); // 50% damage bonus
            log += ">> CRITICAL HIT! <<\n";
        }

        log += target.takeDamage(damageDealt);
        return log;
    }

    /**
     * Processes incoming damage and updates the Limit Break gauge.
     */
    public String takeDamage(int damage) {
        this.currentHp -= damage;
        if (this.currentHp < 0) {
            this.currentHp = 0;
        }

        // Fill the Limit Gauge based on the percentage of max HP lost
        if ((this.limitSkill != null || this.ff7Summon != null) && this.currentHp > 0) {
            int limitGain = (int) (((double) damage / this.maxHp) * 200);
            this.limitGauge += limitGain;
            if (this.limitGauge > 100) {
                this.limitGauge = 100;
            }
        }

        return this.name + " takes " + damage + " damage!";
    }

    /**
     * Restores Hit Points without exceeding the maximum capacity.
     */
    public String healing(int healAmount) {
        this.currentHp += healAmount;
        if (this.currentHp > this.maxHp) {
            this.currentHp = this.maxHp;
        }
        return this.name + " recovers " + healAmount + " HP!";
    }

    /**
     * Executes a skill, applying the correct scaling formula based on its type.
     */
    public String useSkill(Character target, int skillIndex) {
        Skill selectedSkill = this.skills.get(skillIndex);

        if (this.currentMp < selectedSkill.getMpCost()) {
            return "Not enough MP to cast this spell!";
        }

        this.currentMp -= selectedSkill.getMpCost();
        String actionLog = this.name + " uses " + selectedSkill.getName() + "!\n";

        // Combat Engine: Dynamic scaling based on SkillType
        if (selectedSkill.getType() == SkillType.WHITE_MAGIC) {
            int healAmount = this.magicAttack + selectedSkill.getDamage();
            actionLog += this.healing(healAmount);

        } else if (selectedSkill.getType() == SkillType.BLACK_MAGIC) {
            int magicDmg = (this.magicAttack + selectedSkill.getDamage()) - target.getMagicDefense();
            if (magicDmg < 1) {
                magicDmg = 1;
            }
            actionLog += target.takeDamage(magicDmg);

        } else if (selectedSkill.getType() == SkillType.PHYSICAL) {
            int physDmg = (this.physAttack + selectedSkill.getDamage()) - target.getPhysDefense();
            if (physDmg < 1) {
                physDmg = 1;
            }

            if (random.nextInt(100) < this.luck) {
                physDmg = (int) (physDmg * 1.5);
                actionLog += ">> CRITICAL HIT! <<\n";
            }
            actionLog += target.takeDamage(physDmg);
        }

        // Apply status effect if the skill carries one
        if (selectedSkill.getEffectToApply() != null) {
            StatusEffect cloneEffect = new StatusEffect(selectedSkill.getEffectToApply());
            target.addStatusEffect(cloneEffect);
            actionLog += "\n>> " + target.getName() + " has been afflicted with [" + cloneEffect.getName() + "]! <<";
        }

        return actionLog;
    }

    /**
     * Executes the character's physical Limit Break (Ultimate Attack) and
     * resets the gauge.
     */
    public String useLimitBreak(Character target) {
        if (this.limitGauge < 100 || this.limitSkill == null) {
            return "Limit Break is not ready yet!";
        }

        this.limitGauge = 0;
        String actionLog = "!!! " + this.name + " unleashes LIMIT BREAK: " + this.limitSkill.getName() + " !!!\n";

        if (this.limitSkill.getType() == SkillType.WHITE_MAGIC) {
            int healAmount = this.magicAttack + this.limitSkill.getDamage();
            actionLog += this.healing(healAmount);
        } else if (this.limitSkill.getType() == SkillType.BLACK_MAGIC) {
            int magicDmg = (this.magicAttack + this.limitSkill.getDamage()) - target.getMagicDefense();
            if (magicDmg < 1) {
                magicDmg = 1;
            }
            actionLog += target.takeDamage(magicDmg);
        } else {
            int physDmg = (this.physAttack + this.limitSkill.getDamage()) - target.getPhysDefense();
            if (physDmg < 1) {
                physDmg = 1;
            }
            if (random.nextInt(100) < this.luck) {
                physDmg = (int) (physDmg * 1.5);
                actionLog += ">> CRITICAL HIT! <<\n";
            }
            actionLog += target.takeDamage(physDmg);
        }

        return actionLog;
    }

    /**
     * Executes the character's magical Summon Materia (FF7 style) and resets
     * the gauge.
     */
    public String useSummon(Character target) {
        if (this.limitGauge < 100 || this.ff7Summon == null) {
            return "Summon is not ready yet!";
        }

        this.limitGauge = 0;
        String actionLog = "★★★ " + this.name + " calls forth " + this.ff7Summon.getName() + " ★★★\n";

        if (this.ff7Summon.getType() == SkillType.WHITE_MAGIC) {
            int healAmount = this.magicAttack + this.ff7Summon.getDamage();
            actionLog += this.healing(healAmount);
        } else {
            int magicDmg = (this.magicAttack + this.ff7Summon.getDamage()) - target.getMagicDefense();
            if (magicDmg < 1) {
                magicDmg = 1;
            }
            actionLog += target.takeDamage(magicDmg);
        }

        return actionLog;
    }

    /**
     * Consumes an item from the inventory to restore stats or cure ailments.
     */
    public String useItem(int itemIndex) {
        Item selectedItem = this.items.get(itemIndex);
        if (selectedItem.getQuantity() <= 0) {
            return "You don't have any " + selectedItem.getName() + " left!";
        }

        selectedItem.decreaseQuantity();
        String log = this.name + " uses a " + selectedItem.getName() + "!\n";

        // Status effect cleansing based on item name
        if (selectedItem.getName().equals("Antidote")) {
            clearStatus("Poison");
        } else if (selectedItem.getName().equals("Alarm Clock")) {
            clearStatus("Sleep");
        } else if (selectedItem.getName().equals("Remedy")) {
            this.activeStatuses.clear();
        }

        if (selectedItem.isRestoresHp() && selectedItem.getRestoreAmount() > 0) {
            log += this.healing(selectedItem.getRestoreAmount());
        } else if (!selectedItem.isRestoresHp()) {
            this.currentMp += selectedItem.getRestoreAmount();
            if (this.currentMp > this.maxMp) {
                this.currentMp = this.maxMp;
            }
            log += this.name + " recovers " + selectedItem.getRestoreAmount() + " MP!";
        }
        return log;
    }

    /**
     * Helper method to remove a specific ailment by name.
     */
    private void clearStatus(String statusName) {
        this.activeStatuses.removeIf(effect -> effect.getName().equals(statusName));
    }

    /**
     * Evaluates active status effects at the start of the turn (e.g., Poison
     * damage).
     */
    public String processStatuses() {
        String log = "";
        for (int i = this.activeStatuses.size() - 1; i >= 0; i--) {
            StatusEffect effect = this.activeStatuses.get(i);
            if (effect.getDamage() > 0) {
                this.currentHp -= effect.getDamage();
                if (this.currentHp < 0) {
                    this.currentHp = 0;
                }
                log += this.name + " suffers " + effect.getDamage() + " damage from [" + effect.getName() + "]! (" + (effect.getTime() - 1) + " turns remaining)\n";
            }
            effect.decreaseTime();
            if (effect.getTime() <= 0) {
                log += this.name + " has completely recovered from [" + effect.getName() + "]!\n";
                this.activeStatuses.remove(i);
            }
        }
        return log;
    }

    /**
     * Checks if the character is paralyzed, asleep, or otherwise incapacitated.
     */
    public boolean canAct() {
        for (StatusEffect effect : this.activeStatuses) {
            if (effect.isLosesTurn()) {
                return false;
            }
        }
        return true;
    }

    // --- UTILITY METHODS ---
    public void addStatusEffect(StatusEffect newEffect) {
        this.activeStatuses.add(newEffect);
    }

    public boolean isAlive() {
        return this.currentHp > 0;
    }

    public void learnSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void addSummon(Character summon) {
        this.summons.add(summon);
    }

    // --- STANDARD GETTERS ---
    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public int getPhysAttack() {
        return physAttack;
    }

    public int getMagicAttack() {
        return magicAttack;
    }

    public int getPhysDefense() {
        return physDefense;
    }

    public int getMagicDefense() {
        return magicDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLuck() {
        return luck;
    }

    public int getLimitGauge() {
        return limitGauge;
    }

    public Skill getLimitSkill() {
        return limitSkill;
    }

    public Skill getFf7Summon() {
        return ff7Summon;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public ArrayList<Character> getSummons() {
        return summons;
    }

    // --- STANDARD SETTERS ---
    public void setLimitGauge(int limitGauge) {
        this.limitGauge = limitGauge;
    }

    public void setLimitSkill(Skill limitSkill) {
        this.limitSkill = limitSkill;
    }

    public void setFf7Summon(Skill ff7Summon) {
        this.ff7Summon = ff7Summon;
    }
}
