package ffbattlesystem;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Controller class orchestrating the ATB (Active Time Battle) loop, 
 * turn handling, state updates, and GUI dialog interactions.
 */
public class FFBattleSystem {

    public static void main(String[] args) {

        GameFactory factory = new GameFactory();
        SwingUI ui = new SwingUI();
        Random random = new Random();

        // Graphical character selection phase
        int playerSelection = ui.askCharacterSelection("SELECT YOUR CHARACTER");
        Character player = factory.createCharacterById(playerSelection);
        
        // POO pattern: Preserve reference to original summoner (e.g., Yuna) 
        // to restore them when an active Aeon falls in combat.
        Character originalPlayer = player;

        int enemySelection = ui.askCharacterSelection("SELECT YOUR ENEMY");
        Character enemy = factory.createCharacterById(enemySelection);

        ui.showMessage("BATTLE START!");
        ui.showMessage(player.getName() + " VS " + enemy.getName());
        ui.showMessage("===============================\n");

        // --- ATB (Active Time Battle) VARIABLES ---
        int playerATB = 0;
        int enemyATB = 0;
        final int ATB_THRESHOLD = 100;

        // Main combat controller loop
        while (originalPlayer.isAlive() && enemy.isAlive()) {

            // Accumulate ATB gauges based on character speed stats
            playerATB += player.getSpeed();
            enemyATB += enemy.getSpeed();

            // Refresh UI status dashboard
            ui.updateStatus(player, enemy);

            // ==========================================
            // 1. PLAYER TURN PROCESSING
            // ==========================================
            if (playerATB >= ATB_THRESHOLD) {
                playerATB -= ATB_THRESHOLD; // Consume ATB points to execute turn

                ui.showMessage("\n--- " + player.getName() + "'s Turn ---");
                
                String playerStatusLog = player.processStatuses();
                if (!playerStatusLog.isEmpty()) ui.showMessage(playerStatusLog);

                // Handle status damage knockout check before taking action
                if (!player.isAlive()) {
                    if (player != originalPlayer) { 
                        ui.showMessage("\n>> " + player.getName() + " fades away... " + originalPlayer.getName() + " returns! <<\n");
                        player = originalPlayer; 
                        playerATB = 0;
                        continue; 
                    } else {
                        break; // Game Over
                    }
                }

                if (!player.canAct()) {
                    ui.showMessage(player.getName() + " is unable to move this turn!");
                } else {
                    boolean validTurn = false;

                    // Loop until a valid combat action is successfully completed
                    while (!validTurn) {
                        String[] options = getDynamicMenuOptions(player);
                        
                        int choice = JOptionPane.showOptionDialog(
                            ui,
                            "What will " + player.getName() + " do?",
                            "Player Turn",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                        );

                        String selectedOption = (choice >= 0 && choice < options.length) ? options[choice] : "Attack";

                        if (selectedOption.equals("Attack")) {
                            ui.showMessage(player.attack(enemy));
                            validTurn = true;
                            
                        } else if (selectedOption.equals("Physical Skills")) {
                            if (player.getSkills().isEmpty()) {
                                ui.showMessage("No physical skills available!");
                                continue;
                            }
                            String[] skillNames = player.getSkills().stream().map(Skill::getName).toArray(String[]::new);
                            String chosenSkill = (String) JOptionPane.showInputDialog(ui, "Select Skill:", "Skills", JOptionPane.QUESTION_MESSAGE, null, skillNames, skillNames[0]);
                            
                            if (chosenSkill != null) {
                                int idx = -1;
                                for(int i=0; i<player.getSkills().size(); i++) {
                                    if(player.getSkills().get(i).getName().equals(chosenSkill)) idx = i;
                                }
                                if(idx != -1) {
                                    ui.showMessage(player.useSkill(enemy, idx));
                                    validTurn = true;
                                }
                            }
                            
                        } else if (selectedOption.equals("Magic")) {
                            String[] magicTypes = {"Black Magic", "White Magic"};
                            String mChoice = (String) JOptionPane.showInputDialog(ui, "Select Magic Type:", "Magic", JOptionPane.QUESTION_MESSAGE, null, magicTypes, magicTypes[0]);
                            
                            if (mChoice != null) {
                                SkillType type = mChoice.equals("Black Magic") ? SkillType.BLACK_MAGIC : SkillType.WHITE_MAGIC;
                                String[] magicNames = player.getSkills().stream().filter(s -> s.getType() == type).map(Skill::getName).toArray(String[]::new);
                                
                                if (magicNames.length == 0) {
                                    ui.showMessage("No spells available in this category!");
                                    continue;
                                }
                                
                                String chosenSpell = (String) JOptionPane.showInputDialog(ui, "Select Spell:", mChoice, JOptionPane.QUESTION_MESSAGE, null, magicNames, magicNames[0]);
                                if (chosenSpell != null) {
                                    int idx = -1;
                                    for(int i=0; i<player.getSkills().size(); i++) {
                                        if(player.getSkills().get(i).getName().equals(chosenSpell)) idx = i;
                                    }
                                    if(idx != -1) {
                                        ui.showMessage(player.useSkill(enemy, idx));
                                        validTurn = true;
                                    }
                                }
                            }
                            
                        } else if (selectedOption.equals("Use Item")) {
                            if (player.getItems().isEmpty()) {
                                ui.showMessage("Inventory is empty!");
                                continue;
                            }
                            String[] itemNames = player.getItems().stream().map(i -> i.getName() + " (x" + i.getQuantity() + ")").toArray(String[]::new);
                            String chosenItem = (String) JOptionPane.showInputDialog(ui, "Select Item:", "Inventory", JOptionPane.QUESTION_MESSAGE, null, itemNames, itemNames[0]);
                            
                            if (chosenItem != null) {
                                int idx = 0;
                                for(int i=0; i<player.getItems().size(); i++) {
                                    if(chosenItem.contains(player.getItems().get(i).getName())) idx = i;
                                }
                                ui.showMessage(player.useItem(idx));
                                validTurn = true;
                            }
                            
                        } else if (selectedOption.equals("Summon Aeon")) {
                            String[] aeonNames = player.getSummons().stream().map(Character::getName).toArray(String[]::new);
                            String chosenAeon = (String) JOptionPane.showInputDialog(ui, "Select Aeon:", "Grand Summon", JOptionPane.QUESTION_MESSAGE, null, aeonNames, aeonNames[0]);
                            
                            if (chosenAeon != null) {
                                for(Character a : player.getSummons()) {
                                    if(a.getName().equals(chosenAeon)) {
                                        if(!a.isAlive()) {
                                            ui.showMessage(a.getName() + " has already fallen!");
                                        } else {
                                            ui.showMessage(">> " + player.getName() + " summons " + a.getName() + "! <<");
                                            player = a; // Substitute active combatant with Aeon
                                            playerATB = 0;
                                            validTurn = true;
                                        }
                                    }
                                }
                            }
                            
                        } else if (selectedOption.startsWith("LIMIT:")) {
                            ui.showMessage(player.useLimitBreak(enemy));
                            validTurn = true;
                            
                        } else if (selectedOption.startsWith("SUMMON:")) {
                            ui.showMessage(player.useSummon(enemy));
                            validTurn = true;
                        }
                    }
                }
                ui.updateStatus(player, enemy);
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
                    // Autonomous AI: Prioritize limit break if available
                    if (enemy.getLimitSkill() != null && enemy.getLimitGauge() >= 100) {
                        ui.showMessage(enemy.useLimitBreak(player));
                    } else {
                        int enemyAction = random.nextInt(2);
                        if (enemyAction == 0 || enemy.getSkills().isEmpty()) {
                            ui.showMessage(enemy.attack(player));
                        } else {
                            ui.showMessage(enemy.useSkill(player, 0));
                        }
                    }
                }
                
                // Check if enemy attack defeated the player's active Aeon
                if (!player.isAlive()) {
                    if (player != originalPlayer) {
                        ui.showMessage("\n>> " + player.getName() + " fades away... " + originalPlayer.getName() + " returns! <<\n");
                        player = originalPlayer; 
                        playerATB = 50; // Head start for returning summoner
                    } else {
                        break; 
                    }
                }
                
                ui.updateStatus(player, enemy);
            }
        } // End of ATB loop

        ui.showMessage("\n===============================");
        if (originalPlayer.isAlive()) {
            ui.showMessage("Victory! Winner: " + originalPlayer.getName());
        } else {
            ui.showMessage("Game Over! Winner: " + enemy.getName());
        }
    }

    /**
     * Dynamically builds available menu options based on character state and gauges.
     */
    private static String[] getDynamicMenuOptions(Character player) {
        java.util.List<String> options = new java.util.ArrayList<>();
        options.add("Attack");
        options.add("Physical Skills");
        options.add("Magic");
        options.add("Use Item");
        
        if (!player.getSummons().isEmpty()) {
            options.add("Summon Aeon");
        }
        if (player.getLimitSkill() != null && player.getLimitGauge() >= 100) {
            options.add("LIMIT: " + player.getLimitSkill().getName());
        }
        if (player.getFf7Summon() != null && player.getLimitGauge() >= 100) {
            options.add("SUMMON: " + player.getFf7Summon().getName());
        }
        
        return options.toArray(new String[0]);
    }
}