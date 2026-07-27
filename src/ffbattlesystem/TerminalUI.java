package ffbattlesystem;

import java.util.Scanner;

/**
 * Handles all command-line inputs and formatted ASCII outputs.
 * Separates the graphical representation from the business logic (View layer).
 */
public class TerminalUI {

    private Scanner scanner;

    public TerminalUI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Helper to safely introduce artificial pauses for better readability.
     */
    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
        pause(600);
    }

    public int askCharacterSelection(String promptTitle) {
        System.out.println("\n--- " + promptTitle + " ---");
        System.out.println("[1] Cloud      [2] Barret      [3] Tidus       [4] Yuna");
        System.out.println("[5] Wakka      [6] Sephiroth   [7] Jenova      [8] Rufus");
        System.out.println("[9] Seymour    [10] Yunalesca  [11] Sinh       [12] Aeris");
        System.out.print(">> Select ID: ");
        return scanner.nextInt();
    }

    /**
     * Renders the main combat HUD, displaying HP, MP, and the dynamic Limit Break gauge.
     */
    public void showBattleStatus(Character player, Character enemy) {
        System.out.println("\n=======================================================");
        System.out.println(String.format(" %-25s | %-25s", player.getName(), enemy.getName()));
        System.out.println(String.format(" HP: %4d/%4d              | HP: %4d/%4d", player.getCurrentHp(), player.getMaxHp(), enemy.getCurrentHp(), enemy.getMaxHp()));
        System.out.println(String.format(" MP: %4d/%4d              | MP: %4d/%4d", player.getCurrentMp(), player.getMaxMp(), enemy.getCurrentMp(), enemy.getMaxMp()));
        
        // --- LIMIT GAUGE DRAWING ---
        String pLimit = getLimitBar(player);
        String eLimit = getLimitBar(enemy);
        System.out.println(String.format(" %-25s | %-25s", pLimit, eLimit));
        
        System.out.println("=======================================================\n");
    }

    /**
     * Generates a visual ASCII progress bar for the Limit Break [####------]
     */
    private String getLimitBar(Character c) {
        // If the character doesn't have a limit (e.g., standard Aeons), draw nothing
        if (c.getLimitSkill() == null && c.getFf7Summon() == null) return ""; 
        
        int bars = c.getLimitGauge() / 10;
        StringBuilder sb = new StringBuilder("LMT: [");
        for (int i = 0; i < 10; i++) {
            sb.append(i < bars ? "#" : "-");
        }
        sb.append("] ").append(c.getLimitGauge()).append("%");
        
        if (c.getLimitGauge() >= 100) sb.append(" MAX!");
        return sb.toString();
    }

    /**
     * Displays the dynamic main menu. Options 5, 6, and 7 appear contextually.
     */
    public int askMainMenu(Character player) {
        System.out.println("\n  +---------------------------------+");
        System.out.println("  | What will " + String.format("%-21s", player.getName() + " do?") + " |");
        System.out.println("  +---------------------------------+");
        System.out.println("  | [1] Attack                      |");
        System.out.println("  | [2] Skills (Physical)           |");
        System.out.println("  | [3] Magic                       |");
        System.out.println("  | [4] Use Item                    |");
        
        // FFX Summons: Only appears if the character has Aeons in their list
        if (!player.getSummons().isEmpty()) {
            System.out.println("  | [5] Summon Aeon                 |");
        }
        
        // LIMIT BREAK: Only appears when gauge is at 100% and limit exists
        if (player.getLimitSkill() != null && player.getLimitGauge() >= 100) {
            System.out.println("  | [6] LIMIT: " + String.format("%-20s", player.getLimitSkill().getName()) + " |");
        }
        
        // FF7 SUMMON MATERIA: Only appears when gauge is at 100% and materia exists
        if (player.getFf7Summon() != null && player.getLimitGauge() >= 100) {
            System.out.println("  | [7] SUMMON: " + String.format("%-19s", player.getFf7Summon().getName()) + " |");
        }
        
        System.out.println("  +---------------------------------+");
        System.out.print("  >> Action: ");
        return scanner.nextInt();
    }

    public int askMagicCategoryMenu() {
        System.out.println("\n  --- MAGIC CATEGORIES ---");
        System.out.println("  [1] Black Magic");
        System.out.println("  [2] White Magic");
        System.out.println("  [0] Back");
        System.out.print("\n  >> Select category: ");
        return scanner.nextInt();
    }

    /**
     * Filters and displays skills matching the requested SkillType.
     */
    public int askFilteredSkillMenu(Character player, SkillType filterType, String menuTitle) {
        System.out.println("\n  --- " + menuTitle + " ---");
        boolean hasSkills = false;
        
        for (int i = 0; i < player.getSkills().size(); i++) {
            Skill s = player.getSkills().get(i);
            if (s.getType() == filterType) {
                System.out.println(String.format("  [%2d] %-15s (MP: %d)", i, s.getName(), s.getMpCost()));
                hasSkills = true;
            }
        }
        
        if (!hasSkills) {
            System.out.println("  (No abilities available in this category)");
        }
        System.out.println("  -----------------------------------");
        System.out.println("  [-1] Back to Main Menu");
        System.out.print("\n  >> Select skill: ");
        return scanner.nextInt();
    }

    public int askItemMenu(Character player) {
        System.out.println("\n  --- INVENTORY ---");
        for (int i = 0; i < player.getItems().size(); i++) {
            Item item = player.getItems().get(i);
            System.out.println(String.format("  [%2d] %-15s (x%d)", i, item.getName(), item.getQuantity()));
        }
        System.out.println("  -----------------------------------");
        System.out.println("  [-1] Back to Main Menu");
        System.out.print("\n  >> Select item: ");
        return scanner.nextInt();
    }

    /**
     * Menu for FFX Aeon substitution.
     */
    public int askSummonMenu(Character player) {
        System.out.println("\n  --- GRAND SUMMON ---");
        for (int i = 0; i < player.getSummons().size(); i++) {
            Character aeon = player.getSummons().get(i);
            
            // Check if the Aeon has been defeated in a previous turn
            String status = aeon.isAlive() ? String.format("HP: %4d/%4d", aeon.getCurrentHp(), aeon.getMaxHp()) : "* FALLEN *";
            System.out.println(String.format("  [%2d] %-12s (%s)", i, aeon.getName(), status));
        }
        System.out.println("  -----------------------------------");
        System.out.println("  [-1] Back to Main Menu");
        System.out.print("\n  >> Select Aeon to summon: ");
        return scanner.nextInt();
    }

    public void close() {
        scanner.close();
    }
}