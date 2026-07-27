package ffbattlesystem;

/**
 * Represents a temporary ailment or condition applied to a character.
 * Handles damage over time (e.g., Poison) and turn-skipping effects (e.g., Paralysis).
 */
public class StatusEffect {
    private String name;
    private int damage;        // Damage taken per turn by the afflicted character
    private int time;          // Remaining turns until the effect wears off
    private boolean losesTurn; // If true, the character is incapacitated

    public StatusEffect(String name, int damage, int time, boolean losesTurn) {
        this.name = name;
        this.damage = damage;
        this.time = time;
        this.losesTurn = losesTurn;
    }

    /**
     * Copy Constructor (Prototype Pattern).
     * Creates a safe clone of the effect to prevent reference sharing between characters.
     */
    public StatusEffect(StatusEffect other) {
        this.name = other.name;
        this.damage = other.damage;
        this.time = other.time;
        this.losesTurn = other.losesTurn;
    }

    /**
     * Reduces the remaining duration of the effect by one turn.
     */
    public void decreaseTime() { 
        this.time--; 
    }
    
    // --- GETTERS ---
    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getTime() { return time; }
    public boolean isLosesTurn() { return losesTurn; }
}