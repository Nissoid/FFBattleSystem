package ffbattlesystem;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Graphical User Interface (GUI) layer.
 * Replaces TerminalUI to provide an interactive desktop window for combat,
 * managing the HUD, real-time statistics, battle logs, and modal menus.
 */
public class SwingUI extends JFrame {

    private JTextArea logTextArea;
    private JLabel playerNameLabel, playerHpLabel, playerMpLabel, playerLimitLabel;
    private JLabel enemyNameLabel, enemyHpLabel, enemyMpLabel, enemyLimitLabel;
    private JPanel actionButtonPanel;

    public SwingUI() {
        setTitle("FF Battle System - Swing Edition");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers window on screen
        setLayout(new BorderLayout(10, 10));

        // --- 1. TOP PANEL: HEAD-UP DISPLAY (HUD STATISTICS) ---
        JPanel hudPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        hudPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hudPanel.setBackground(new Color(30, 30, 30)); // Dark mode aesthetic

        // Player HUD block
        JPanel playerHud = new JPanel(new GridLayout(4, 1));
        playerHud.setOpaque(false);
        playerNameLabel = new JLabel("Player: ");
        playerNameLabel.setForeground(Color.WHITE);
        playerHpLabel = new JLabel("HP: ");
        playerHpLabel.setForeground(Color.GREEN);
        playerMpLabel = new JLabel("MP: ");
        playerMpLabel.setForeground(Color.CYAN);
        playerLimitLabel = new JLabel("Limit: [----------] 0%");
        playerLimitLabel.setForeground(Color.YELLOW);
        
        playerHud.add(playerNameLabel);
        playerHud.add(playerHpLabel);
        playerHud.add(playerMpLabel);
        playerHud.add(playerLimitLabel);

        // Enemy HUD block (Right-aligned text)
        JPanel enemyHud = new JPanel(new GridLayout(4, 1));
        enemyHud.setOpaque(false);
        enemyNameLabel = new JLabel("Enemy: ", SwingConstants.RIGHT);
        enemyNameLabel.setForeground(Color.WHITE);
        enemyHpLabel = new JLabel("HP: ", SwingConstants.RIGHT);
        enemyHpLabel.setForeground(Color.RED);
        enemyMpLabel = new JLabel("MP: ", SwingConstants.RIGHT);
        enemyMpLabel.setForeground(Color.CYAN);
        enemyLimitLabel = new JLabel("Limit: [----------] 0%", SwingConstants.RIGHT);
        enemyLimitLabel.setForeground(Color.YELLOW);

        enemyHud.add(enemyNameLabel);
        enemyHud.add(enemyHpLabel);
        enemyHud.add(enemyMpLabel);
        enemyHud.add(enemyLimitLabel);

        hudPanel.add(playerHud);
        hudPanel.add(enemyHud);
        add(hudPanel, BorderLayout.NORTH);

        // --- 2. CENTER PANEL: COMBAT LOG & EVENT HISTORY ---
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setBackground(new Color(15, 15, 15));
        logTextArea.setForeground(new Color(220, 220, 220));
        logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(null, " Battle Log ", 0, 0, null, Color.WHITE));
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. BOTTOM PANEL: INTERACTIVE ACTION BUTTON CONTAINER ---
        actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionButtonPanel.setBackground(new Color(40, 40, 40));
        add(actionButtonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Appends a new message to the battle log and auto-scrolls to the latest entry.
     */
    public void showMessage(String message) {
        logTextArea.append(message + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    /**
     * Refreshes real-time combat stats (HP, MP, and Limit gauges) for both combatants.
     */
    public void updateStatus(Character player, Character enemy) {
        playerNameLabel.setText("Player: " + player.getName());
        playerHpLabel.setText(String.format("HP: %d / %d", player.getCurrentHp(), player.getMaxHp()));
        playerMpLabel.setText(String.format("MP: %d / %d", player.getCurrentMp(), player.getMaxMp()));
        playerLimitLabel.setText("Limit: " + getLimitBarString(player));

        enemyNameLabel.setText("Enemy: " + enemy.getName());
        enemyHpLabel.setText(String.format("HP: %d / %d", enemy.getCurrentHp(), enemy.getMaxHp()));
        enemyMpLabel.setText(String.format("MP: %d / %d", enemy.getCurrentMp(), enemy.getMaxMp()));
        enemyLimitLabel.setText("Limit: " + getLimitBarString(enemy));
    }

    /**
     * Generates a visual ASCII progress bar for the Limit gauge.
     */
    private String getLimitBarString(Character c) {
        if (c.getLimitSkill() == null && c.getFf7Summon() == null) return "N/A";
        int bars = c.getLimitGauge() / 10;
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            sb.append(i < bars ? "#" : "-");
        }
        sb.append("] ").append(c.getLimitGauge()).append("%");
        if (c.getLimitGauge() >= 100) sb.append(" MAX!");
        return sb.toString();
    }

    /**
     * Opens a modal dropdown dialog allowing the user to select characters or bosses.
     */
    public int askCharacterSelection(String title) {
        String[] options = {
            "Cloud", "Barret", "Tidus", "Yuna", 
            "Wakka", "Sephiroth", "Jenova", "Rufus", 
            "Seymour", "Yunalesca", "Sinh", "Aeris"
        };
        
        String selection = (String) JOptionPane.showInputDialog(
            this, 
            title, 
            "Character Selection", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]
        );

        if (selection == null) return 1; // Default fallback to Cloud

        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(selection)) {
                return i + 1; // Returns matched character ID (1-12)
            }
        }
        return 1;
    }
}