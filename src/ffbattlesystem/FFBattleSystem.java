/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ffbattlesystem;

import java.util.Random;

/**
 * Main class that controls the flow of the game. Orchestrates the setup,
 * character selection, and the main battle loop.
 */
public class FFBattleSystem {

    public static void main(String[] args) {

        // --- 1. ENGINE INITIALIZATION ---
        GameFactory factory = new GameFactory();
        TerminalUI ui = new TerminalUI();
        Random random = new Random();

        // --- 2. CHARACTER SELECTION ---
        // Select the player character via UI
        int playerSelection = ui.askCharacterSelection("SELECT YOUR CHARACTER");
        Character player = factory.createCharacterById(playerSelection);

        // Select the enemy character via UI
        int enemySelection = ui.askCharacterSelection("SELECT YOUR ENEMY");
        Character enemy = factory.createCharacterById(enemySelection);

        ui.showMessage("\nBATTLE START!");
        ui.showMessage(player.getName() + " VS " + enemy.getName());
        ui.showMessage("===============================\n");

        // --- 3. MAIN GAME LOOP ---
        // Loop continues until one of the characters drops to 0 HP
        while (player.isAlive() && enemy.isAlive()) {

            ui.showBattleStatus(player, enemy);

            // ==========================================
            //               PLAYER TURN
            // ==========================================
            ui.showMessage("\n--- " + player.getName() + "'s Turn ---");

            // 1. Process Status Effects (Poison, etc.)
            String playerStatusLog = player.processStatuses();
            if (!playerStatusLog.isEmpty()) {
                ui.showMessage(playerStatusLog);
            }

            // Check if player fainted from status effects
            if (!player.isAlive()) {
                ui.showMessage(player.getName() + " has fainted!");
            } else {
                // 2. Check if the character can move (Paralysis, Sleep)
                if (!player.canAct()) {
                    ui.showMessage(player.getName() + " is unable to move this turn!");
                } else {
                    // 3. If able to move, show the action menu
                    boolean validTurn = false;

                    while (!validTurn) {
                        int actionChoice = ui.askMainMenu(player);

                        switch (actionChoice) {
                            case 1 -> {
                                // 1. ATTACK
                                String result = player.attack(enemy);
                                ui.showMessage(result);
                                validTurn = true; // Fin del turno
                            }
                            case 2 -> {
                                // 2. PHYSICAL SKILLS
                                int skillIdx = ui.askFilteredSkillMenu(player, SkillType.PHYSICAL, "PHYSICAL ABILITIES");

                                // Si el usuario pulsa -1, cancela y volvemos al menú principal
                                if (skillIdx == -1) {
                                    ui.showMessage("Returning to main menu...\n");
                                    // validTurn sigue en false, por lo que el bucle vuelve a pedir el menú principal
                                } else if (skillIdx >= 0 && skillIdx < player.getSkills().size() && player.getSkills().get(skillIdx).getType() == SkillType.PHYSICAL) {
                                    ui.showMessage(player.useSkill(enemy, skillIdx));
                                    validTurn = true; // Fin del turno
                                } else {
                                    ui.showMessage("Invalid selection. Try again.\n");
                                }
                            }
                            case 3 -> {
                                // 3. MAGIC (Sub-menu with back navigation)
                                boolean choosingMagic = true;

                                while (choosingMagic) {
                                    int magicCat = ui.askMagicCategoryMenu();

                                    if (magicCat == 0) {
                                        // Volver al menú principal
                                        choosingMagic = false;
                                    } else if (magicCat == 1) {
                                        // Black Magic
                                        int skillIdx = ui.askFilteredSkillMenu(player, SkillType.BLACK_MAGIC, "BLACK MAGIC");

                                        if (skillIdx == -1) {
                                            // Vuelve al selector de categorías (no rompe el choosingMagic)
                                            ui.showMessage("Returning to categories...\n");
                                        } else if (skillIdx >= 0 && skillIdx < player.getSkills().size() && player.getSkills().get(skillIdx).getType() == SkillType.BLACK_MAGIC) {
                                            ui.showMessage(player.useSkill(enemy, skillIdx));
                                            validTurn = true;      // Fin del turno
                                            choosingMagic = false; // Salimos del bucle de magia
                                        } else {
                                            ui.showMessage("Invalid selection. Try again.\n");
                                        }
                                    } else if (magicCat == 2) {
                                        // White Magic
                                        int skillIdx = ui.askFilteredSkillMenu(player, SkillType.WHITE_MAGIC, "WHITE MAGIC");

                                        if (skillIdx == -1) {
                                            // Vuelve al selector de categorías
                                            ui.showMessage("Returning to categories...\n");
                                        } else if (skillIdx >= 0 && skillIdx < player.getSkills().size() && player.getSkills().get(skillIdx).getType() == SkillType.WHITE_MAGIC) {
                                            ui.showMessage(player.useSkill(enemy, skillIdx));
                                            validTurn = true;      // Fin del turno
                                            choosingMagic = false; // Salimos del bucle de magia
                                        } else {
                                            ui.showMessage("Invalid selection. Try again.\n");
                                        }
                                    } else {
                                        ui.showMessage("Invalid category. Try again.\n");
                                    }
                                }
                            }
                            case 4 -> {
                                // 4. ITEMS
                                int itemIndex = ui.askItemMenu(player);

                                if (itemIndex == -1) {
                                    ui.showMessage("Returning to main menu...\n");
                                } else if (itemIndex >= 0 && itemIndex < player.getItems().size()) {
                                    ui.showMessage(player.useItem(itemIndex));
                                    validTurn = true; // Fin del turno
                                } else {
                                    ui.showMessage("Invalid item selection. Try again.\n");
                                }
                            }
                            default ->
                                ui.showMessage("Invalid option. Please enter 1, 2, 3, or 4.\n");
                        }
                    }
                }

                // End of Player Turn: decay statuses
                String playerDecayLog = player.decayStatuses();
                if (!playerDecayLog.isEmpty()) {
                    ui.showMessage(playerDecayLog);
                }
            }

            // ==========================================
            //               ENEMY TURN
            // ==========================================
            // Only execute turn if the enemy survived the player's attack
            if (enemy.isAlive()) {
                ui.showMessage("\n--- " + enemy.getName() + "'s Turn ---");

                // 1. Process enemy's Status Effects
                String enemyStatusLog = enemy.processStatuses();
                if (!enemyStatusLog.isEmpty()) {
                    ui.showMessage(enemyStatusLog);
                }

                // Check if enemy fainted from status effects
                if (!enemy.isAlive()) {
                    ui.showMessage(enemy.getName() + " has fainted!");
                } else {
                    // 2. Check if the enemy can move
                    if (!enemy.canAct()) {
                        ui.showMessage(enemy.getName() + " is unable to move this turn!");
                    } else {
                        // 3. Simple Artificial Intelligence (AI)
                        // 50% chance to attack, 50% chance to use their first skill
                        int enemyAction = random.nextInt(2);

                        if (enemyAction == 0) {
                            ui.showMessage(enemy.attack(player));
                        } else {
                            // The enemy uses the skill at index 0 of their list
                            ui.showMessage(enemy.useSkill(player, 0));
                        }
                    }

                    // End of Enemy Turn: decay statuses
                    String enemyDecayLog = enemy.decayStatuses();
                    if (!enemyDecayLog.isEmpty()) {
                        ui.showMessage(enemyDecayLog);
                    }
                }
            }

            ui.showMessage("\n-------------------------------");
        }

        // --- 4. BATTLE WRAP-UP ---
        ui.showMessage("\n===============================");

        if (player.isAlive()) {
            ui.showMessage("Victory! The winner is: " + player.getName());
        } else {
            ui.showMessage("Game Over! The winner is: " + enemy.getName());
        }

        // Close the scanner and free resources
        ui.close();
    }
}
