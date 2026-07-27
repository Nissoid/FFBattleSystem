package ffbattlesystem;

/**
 * Represents a combat ability, magic spell, or ultimate attack.
 */
public class Skill {
    private String name;
    private int damage;                 // Base power for damage or healing calculation
    private int mpCost;                 // Magic Points required to cast
    private SkillType type;             // Defines the scaling formula
    private StatusEffect effectToApply; // Optional ailment inflicted upon hit

    // Constructor for standard skills without status effects
    public Skill(String name, int damage, int mpCost, SkillType type) {
        this.name = name;
        this.damage = damage;
        this.mpCost = mpCost;
        this.type = type;
        this.effectToApply = null;
    }

    // Constructor for skills that inflict a status effect (e.g., Bio, Sleep Attack)
    public Skill(String name, int damage, int mpCost, SkillType type, StatusEffect effectToApply) {
        this.name = name;
        this.damage = damage;
        this.mpCost = mpCost;
        this.type = type;
        this.effectToApply = effectToApply;
    }

    // --- GETTERS ---
    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getMpCost() { return mpCost; }
    public SkillType getType() { return type; }
    public StatusEffect getEffectToApply() { return effectToApply; }
}