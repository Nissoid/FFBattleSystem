package ffbattlesystem;

import java.util.Scanner;

public class TerminalUI {
    
    private Scanner scanner;

    public TerminalUI() {
        this.scanner = new Scanner(System.in);
    }

    public void showMessage(String message) {
        System.out.println(message);
        try {
            Thread.sleep(500); // Artificial delay for pacing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void showBattleStatus(Character player, Character enemy) {
        System.out.println("\n");
        System.out.println("O===============================================================O");
        System.out.println(String.format("| %-29s | %-29s |", "HERO: " + player.getName(), "ENEMY: " + enemy.getName()));
        System.out.println("|-------------------------------+-------------------------------|");
        System.out.println(String.format("| HP: %3d / %-3d                 | HP: %3d / %-3d                 |", 
                player.getCurrentHp(), player.getMaxHp(), enemy.getCurrentHp(), enemy.getMaxHp()));
        System.out.println(String.format("| MP: %3d / %-3d                 | MP: %3d / %-3d                 |", 
                player.getCurrentMp(), player.getMaxMp(), enemy.getCurrentMp(), enemy.getMaxMp()));
        System.out.println("O===============================================================O");
    }

    public int askCharacterSelection(String promptMessage) {
        System.out.println("\n\n  +---------------------------------------+");
        System.out.println("  |     " + promptMessage + "     |");
        System.out.println("  +---------------------------------------+");
        System.out.println("  | 1. Cloud Strife    |  7. Jenova       |");
        System.out.println("  | 2. Barret Wallace  |  8. Rufus        |");
        System.out.println("  | 3. Tidus           |  9. Seymour      |");
        System.out.println("  | 4. Yuna            | 10. Yunalesca    |");
        System.out.println("  | 5. Wakka           | 11. Sinh         |");
        System.out.println("  | 6. Sephiroth       | 12. Aeris        |");
        System.out.println("  +---------------------------------------+");
        System.out.print("\n  >> Choose a fighter (1-12): ");
        return scanner.nextInt();
    }

    public int askMainMenu(Character player) {
        System.out.println("\n  +---------------------------------+");
        System.out.println("  | What will " + String.format("%-21s", player.getName() + " do?") + " |");
        System.out.println("  +---------------------------------+");
        System.out.println("  | [1] Attack                      |");
        System.out.println("  | [2] Skills (Physical)           |");
        System.out.println("  | [3] Magic                       |");
        System.out.println("  | [4] Use Item                    |");
        System.out.println("  +---------------------------------+");
        System.out.print("  >> Action (1-4): ");
        return scanner.nextInt();
    }

    public int askMagicCategoryMenu() {
        System.out.println("\n  +---------------------------------+");
        System.out.println("  | SELECT MAGIC CATEGORY           |");
        System.out.println("  +---------------------------------+");
        System.out.println("  | [1] Black Magic (Offensive)     |");
        System.out.println("  | [2] White Magic (Support)       |");
        System.out.println("  | [0] Back to Main Menu           |");
        System.out.println("  +---------------------------------+");
        System.out.print("  >> Category (0-2): ");
        return scanner.nextInt();
    }

    /**
     * Filters and displays only the skills matching the requested category.
     */
    public int askFilteredSkillMenu(Character player, SkillType typeToShow, String menuTitle) {
        System.out.println("\n  --- " + menuTitle + " ---");
        boolean found = false;
        
        for (int i = 0; i < player.getSkills().size(); i++) {
            Skill s = player.getSkills().get(i);
            if (s.getType() == typeToShow) {
                System.out.println(String.format("  [%2d] %-18s (MP: %3d)", i, s.getName(), s.getMpCost()));
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("  |  (No abilities learned yet)    |");
        }
        
        // Añadimos explícitamente la opción de volver atrás
        System.out.println("  -----------------------------------");
        System.out.println("  [-1] Back to Categories");
        System.out.print("\n  >> Select ID to cast: ");
        return scanner.nextInt();
    }

    public int askItemMenu(Character player) {
        System.out.println("\n  --- BACKPACK ---");
        for (int i = 0; i < player.getItems().size(); i++) {
            Item item = player.getItems().get(i);
            System.out.println(String.format("  [%2d] %-15s (x%d)", i, item.getName(), item.getQuantity()));
        }
        System.out.print("\n  >> Select item ID (or -1 to cancel): ");
        return scanner.nextInt();
    }

    public void close() {
        scanner.close();
    }
}