/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

import java.util.Scanner;

/**
 * Handles all console input and output. Acts as the View in the MVC pattern. It
 * is the ONLY class authorized to use System.out and Scanner.
 *
 * @author Dani
 */
public class TerminalUI {

    private Scanner scanner;

    public TerminalUI() {
        // Initialize the scanner once for the entire application lifecycle
        this.scanner = new Scanner(System.in);
    }

    // --- OUTPUT METHODS ---
    /**
     * Wrapper method to print generic messages to the console.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays the battle HUD showing current HP and MP for both combatants.
     */
    public void showBattleStatus(Character player, Character enemy) {
        System.out.println("\n=== CURRENT STATUS ===");
        System.out.println(player.getName() + " -> HP: " + player.getCurrentHp() + " | MP: " + player.getCurrentMp());
        System.out.println(enemy.getName() + " -> HP: " + enemy.getCurrentHp() + " | MP: " + enemy.getCurrentMp());
        System.out.println("======================\n");
    }

    // --- INPUT METHODS ---
    /**
     * Displays the main combat menu and captures the player's decision.
     *
     * @return The selected action as an integer.
     */
    public int askCharacterSelection(String promptMessage) {
        System.out.println("\n=== " + promptMessage + " ===");
        System.out.println("1. Cloud Strife (FFVII)");
        System.out.println("2. Barret Wallace (FFVII)");
        System.out.println("3. Tidus (FFX)");
        System.out.println("4. Yuna (FFX)");
        System.out.println("5. Wakka (FFX)");
        System.out.println("6. Sephiroth (Boss)");
        System.out.print("Choose a fighter (1-6): ");
        
        return scanner.nextInt();
    }
    
    public int askMainMenu(Character player) {
        System.out.println("It's " + player.getName() + "'s turn. What would you like to do?");
        System.out.println("1. Attack");
        System.out.println("2. Magic / Skills");
        System.out.println("3. Inventory");
        System.out.print("Choose your action: ");

        return scanner.nextInt();
    }

    /**
     * Displays the character's skill list dynamically.
     *
     * @return The zero-based index of the selected skill.
     */
    public int askSkillMenu(Character player) {
        System.out.println("\n--- SPELLBOOK ---");

        for (int i = 0; i < player.getSkills().size(); i++) {
            Skill s = player.getSkills().get(i);
            System.out.println((i + 1) + ". " + s.getName() + " (MP: " + s.getMpCost() + ")");
        }

        System.out.print("Choose a spell to cast: ");
        int choice = scanner.nextInt();

        return choice - 1;
    }

    /**
     * Displays the character's inventory dynamically.
     *
     * @return The zero-based index of the selected item.
     */
    public int askItemMenu(Character player) {
        System.out.println("\n--- INVENTORY ---");

        for (int i = 0; i < player.getItems().size(); i++) {
            Item item = player.getItems().get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (Quantity: " + item.getQuantity() + ")");
        }

        System.out.print("Choose an item to use: ");
        int itemChoice = scanner.nextInt();

        return itemChoice - 1;
    }

    // --- RESOURCE MANAGEMENT ---
    /**
     * Safely closes the scanner to prevent memory leaks.
     */
    public void close() {
        this.scanner.close();
    }
}
