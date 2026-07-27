package ffbattlesystem;

import java.util.Random;

/**
 * Controller class orchestrating the ATB (Active Time Battle) loop, 
 * turns, menus, and character substitutions.
 */
public class FFBattleSystem {

    public static void main(String[] args) {

        GameFactory factory = new GameFactory();
        TerminalUI ui = new TerminalUI();
        Random random = new Random();

        int playerSelection = ui.askCharacterSelection("SELECT YOUR CHARACTER");
        Character player = factory.createCharacterById(playerSelection);
        
        // POO Magic 1: Store reference to the original character (e.g., Yuna) 
        // to restore them when an Aeon falls.
        Character originalPlayer = player;

        int enemySelection = ui.askCharacterSelection("SELECT YOUR ENEMY");
        Character enemy = factory.createCharacterById(enemySelection);

        ui.showMessage("\nBATTLE START!");
        ui.showMessage(player.getName() + " VS " + enemy.getName());
        ui.showMessage("===============================\n");

        // --- ATB (Active Time Battle) VARIABLES ---
        int playerATB = 0;
        int enemyATB = 0;
        final int ATB_THRESHOLD = 100;

        // The game loop monitors the original player's life, not the temporary Aeon's life.
        while (originalPlayer.isAlive() && enemy.isAlive()) {

            // Fill ATB gauges based on character speed stats
            playerATB += player.getSpeed();
            enemyATB += enemy.getSpeed();

            // ==========================================
            // 1. PLAYER TURN PROCESSING
            // ==========================================
            if (playerATB >= ATB_THRESHOLD) {
                playerATB -= ATB_THRESHOLD; // Consume gauge to take action

                ui.showBattleStatus(player, enemy);
                ui.showMessage("\n--- " + player.getName() + "'s Turn ---");
                
                String playerStatusLog = player.processStatuses();
                if (!playerStatusLog.isEmpty()) ui.showMessage(playerStatusLog);

                // Check if poison/status effects killed the active character before they could move
                if (!player.isAlive()) {
                    if (player != originalPlayer) { // Aeon died
                        ui.showMessage("\n>> " + player.getName() + " fades away... " + originalPlayer.getName() + " returns to the battlefield! <<\n");
                        player = originalPlayer; // Restore summoner
                        playerATB = 0;
                        continue; // Skip the rest of the dead Aeon's turn
                    } else {
                        break; // Original player died, Game Over
                    }
                }

                if (!player.canAct()) {
                    ui.showMessage(player.getName() + " is unable to move this turn!");
                } else {
                    boolean validTurn = false;

                    // Loop until a valid action is successfully executed (handles 'Back' selections)
                    while (!validTurn) {
                        int actionChoice = ui.askMainMenu(player);

                        switch (actionChoice) {
                            case 1 -> { // ATTACK
                                ui.showMessage(player.attack(enemy));
                                validTurn = true;
                            }
                            case 2 -> { // PHYSICAL SKILLS
                                int skillIdx = ui.askFilteredSkillMenu(player, SkillType.PHYSICAL, "PHYSICAL ABILITIES");
                                if (skillIdx == -1) {
                                    ui.showMessage("Returning to main menu...\n");
                                } else if (skillIdx >= 0 && skillIdx < player.getSkills().size() && player.getSkills().get(skillIdx).getType() == SkillType.PHYSICAL) {
                                    ui.showMessage(player.useSkill(enemy, skillIdx));
                                    validTurn = true;
                                } else {
                                    ui.showMessage("Invalid selection. Try again.\n");
                                }
                            }
                            case 3 -> { // MAGIC SUB-MENUS
                                boolean choosingMagic = true;
                                while (choosingMagic) {
                                    int magicCat = ui.askMagicCategoryMenu();
                                    
                                    if (magicCat == 0) {
                                        choosingMagic = false; 
                                    } else if (magicCat == 1) { // BLACK MAGIC
                                        int skillIdx = ui.askFilteredSkillMenu(player, SkillType.BLACK_MAGIC, "BLACK MAGIC");
                                        if (skillIdx == -1) {
                                            ui.showMessage("Returning to categories...\n");
                                        } else if (skillIdx >= 0 && skillIdx < player.getSkills().size() && player.getSkills().get(skillIdx).getType() == SkillType.BLACK_MAGIC) {
                                            ui.showMessage(player.useSkill(enemy, skillIdx));
                                            validTurn = true;
                                            choosingMagic = false;
                                        } else {
                                            ui.showMessage("Invalid selection.\n");
                                        }
                                    } else if (magicCat == 2) { // WHITE MAGIC
                                        int skillIdx = ui.askFilteredSkillMenu(player, SkillType.WHITE_MAGIC, "WHITE MAGIC");
                                        if (skillIdx == -1) {
                                            ui.showMessage("Returning to categories...\n");
                                        } else if (skillIdx >= 0 && skillIdx < player.getSkills().size() && player.getSkills().get(skillIdx).getType() == SkillType.WHITE_MAGIC) {
                                            ui.showMessage(player.useSkill(enemy, skillIdx));
                                            validTurn = true;
                                            choosingMagic = false;
                                        } else {
                                            ui.showMessage("Invalid selection.\n");
                                        }
                                    }
                                }
                            }
                            case 4 -> { // ITEMS
                                int itemIndex = ui.askItemMenu(player);
                                if (itemIndex == -1) {
                                    ui.showMessage("Returning to main menu...\n");
                                } else if (itemIndex >= 0 && itemIndex < player.getItems().size()) {
                                    ui.showMessage(player.useItem(itemIndex));
                                    validTurn = true;
                                } else {
                                    ui.showMessage("Invalid item selection.\n");
                                }
                            }
                            case 5 -> { // FFX SUMMON AEON (Liskov Substitution Principle)
                                if (!player.getSummons().isEmpty()) {
                                    int summonIndex = ui.askSummonMenu(player);
                                    if (summonIndex == -1) {
                                        ui.showMessage("Returning to main menu...\n");
                                    } else if (summonIndex >= 0 && summonIndex < player.getSummons().size()) {
                                        Character chosenAeon = player.getSummons().get(summonIndex);
                                        
                                        if (!chosenAeon.isAlive()) {
                                            ui.showMessage("\n" + chosenAeon.getName() + " cannot be summoned right now! (Fallen)");
                                        } else {
                                            ui.showMessage("\n>> " + player.getName() + " summons " + chosenAeon.getName() + "! <<\n");
                                            
                                            // Override the active player pointer with the Aeon
                                            player = chosenAeon;
                                            playerATB = 0; // Aeon starts with empty ATB gauge
                                            validTurn = true;
                                        }
                                    } else {
                                        ui.showMessage("Invalid Aeon selection.\n");
                                    }
                                } else {
                                    ui.showMessage("Invalid option.\n");
                                }
                            }
                            case 6 -> { // LIMIT BREAK
                                if (player.getLimitSkill() != null && player.getLimitGauge() >= 100) {
                                    ui.showMessage(player.useLimitBreak(enemy));
                                    validTurn = true;
                                } else {
                                    ui.showMessage("Invalid option. Limit is not ready!\n");
                                }
                            }
                            case 7 -> { // FF7 MATERIA SUMMON
                                if (player.getFf7Summon() != null && player.getLimitGauge() >= 100) {
                                    ui.showMessage(player.useSummon(enemy));
                                    validTurn = true;
                                } else {
                                    ui.showMessage("Invalid option. Summon is not ready!\n");
                                }
                            }
                            default -> ui.showMessage("Invalid option.\n");
                        }
                    }
                }
                ui.showMessage("\n-------------------------------");
            }

            // ==========================================
            // 2. ENEMY TURN PROCESSING
            // ==========================================
            if (enemyATB >= ATB_THRESHOLD && originalPlayer.isAlive() && enemy.isAlive()) {
                enemyATB -= ATB_THRESHOLD; 

                ui.showMessage("\n--- " + enemy.getName() + "'s Turn ---");

                String enemyStatusLog = enemy.processStatuses();
                if (!enemyStatusLog.isEmpty()) ui.showMessage(enemyStatusLog);

                if (!enemy.isAlive()) break;

                if (!enemy.canAct()) {
                    ui.showMessage(enemy.getName() + " is unable to move this turn!");
                } else {
                    
                    // Advanced AI: Prioritize ultimate attacks if the limit gauge is full
                    if (enemy.getLimitSkill() != null && enemy.getLimitGauge() >= 100) {
                        ui.showMessage(enemy.useLimitBreak(player));
                    } else {
                        // Basic weighted AI (50% physical, 50% first available skill)
                        int enemyAction = random.nextInt(2);
                        if (enemyAction == 0 || enemy.getSkills().isEmpty()) {
                            ui.showMessage(enemy.attack(player));
                        } else {
                            ui.showMessage(enemy.useSkill(player, 0));
                        }
                    }
                }
                
                // Post-attack check: Did the enemy kill the player's Aeon?
                if (!player.isAlive()) {
                    if (player != originalPlayer) {
                        ui.showMessage("\n>> " + player.getName() + " fades away... " + originalPlayer.getName() + " returns to the battlefield! <<\n");
                        player = originalPlayer; 
                        playerATB = 50; // Grant the returning summoner a 50% ATB head start
                    } else {
                        break; // Real Game Over
                    }
                }
                
                ui.showMessage("\n-------------------------------");
            }
        } // End of ATB Loop

        ui.showMessage("\n===============================");

        if (originalPlayer.isAlive()) {
            ui.showMessage("Victory! The winner is: " + originalPlayer.getName());
        } else {
            ui.showMessage("Game Over! The winner is: " + enemy.getName());
        }

        ui.close();
    }
}