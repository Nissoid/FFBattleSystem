/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ffbattlesystem;

import java.util.Random;

/**
 * The main engine of our turn-based RPG battle simulator. Acts as the
 * Controller in the MVC pattern.
 *
 * @author Dani
 */
public class FFBattleSystem {

    public static void main(String[] args) {

        // --- 1. ENGINE INITIALIZATION ---
        GameFactory factory = new GameFactory();
        TerminalUI ui = new TerminalUI();
        Random random = new Random();

        // Instantiate combatants using the Factory
        Character player = factory.createCloud();
        Character enemy = factory.createSephiroth();

        ui.showMessage("BATTLE START!");
        ui.showMessage("===============================\n");

        // --- 2. MAIN GAME LOOP ---
        // Loop continues until one of the characters' HP reaches zero
        while (player.isAlive() && enemy.isAlive()) {

            ui.showBattleStatus(player, enemy);

            // --- PLAYER TURN ---
            boolean validTurn = false;

            while (!validTurn) {
                int playerChoice = ui.askMainMenu(player);

                switch (playerChoice) {
                    case 1 -> {
                        // Standard physical attack
                        String result = player.attack(enemy);
                        ui.showMessage(result);
                        validTurn = true;
                    }
                    case 2 -> {
                        // Magic and Skills execution
                        int skillIndex = ui.askSkillMenu(player);

                        if (skillIndex >= 0 && skillIndex < player.getSkills().size()) {
                            String result = player.useSkill(enemy, skillIndex);
                            ui.showMessage(result);
                            validTurn = true;
                        } else {
                            ui.showMessage("Invalid spell choice. Taking you back to the main menu...\n");
                        }
                    }
                    case 3 -> {
                        // Inventory usage
                        int itemIndex = ui.askItemMenu(player);

                        if (itemIndex >= 0 && itemIndex < player.getItems().size()) {
                            String result = player.useItem(itemIndex);
                            ui.showMessage(result);
                            validTurn = true;
                        } else {
                            ui.showMessage("Invalid item selection. Taking you back...\n");
                        }
                    }
                    default ->
                        ui.showMessage("Invalid option. Please enter 1, 2, or 3.\n");
                }
            }

            // --- ENEMY TURN ---
            // Only execute if the enemy survived the player's attack
            if (enemy.isAlive()) {
                ui.showMessage("\n--- Enemy Turn ---");

                // Simple AI: 50% chance to attack, 50% chance to use their first skill
                int enemyAction = random.nextInt(2);

                if (enemyAction == 0) {
                    ui.showMessage(enemy.attack(player));
                } else {
                    ui.showMessage(enemy.useSkill(player, 0));
                }
            }

            ui.showMessage("\n-------------------------------");
        }

        // --- 3. BATTLE WRAP-UP ---
        ui.showMessage("\n===============================");

        if (player.isAlive()) {
            ui.showMessage("Victory! The winner is: " + player.getName());
        } else {
            ui.showMessage("Game Over! The winner is: " + enemy.getName());
        }

        // Clean up resources
        ui.close();
    }
}
