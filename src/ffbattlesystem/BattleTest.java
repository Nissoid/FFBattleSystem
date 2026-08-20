package ffbattlesystem;

/**
 * Self-contained verification and test suite for FFBattleSystem.
 */
public class BattleTest {

    public static void main(String[] args) {
        System.out.println("Running FFBattleSystem Test Suite...");

        testGameFactory();
        testCharacterCombatAndHealing();
        testItemValidation();
        testStatusEffects();

        System.out.println("\nAll tests passed successfully! ✅");
    }

    private static void testGameFactory() {
        System.out.print("[Test] GameFactory character creation... ");
        GameFactory factory = new GameFactory();
        
        Character cloud = factory.createCharacterById(1);
        assert cloud != null && cloud.getName().equals("Cloud") : "Cloud creation failed";
        
        Character sephiroth = factory.createCharacterById(6);
        assert sephiroth != null && sephiroth.getName().equals("Sephiroth") : "Sephiroth creation failed";

        Character invalid = factory.createCharacterById(999);
        assert invalid != null && invalid.getName().equals("Cloud") : "Fallback creation failed";

        System.out.println("PASSED");
    }

    private static void testCharacterCombatAndHealing() {
        System.out.print("[Test] Combat damage and healing... ");
        Character attacker = new Character("Hero", 200, 100, 50, 40, 20, 20, 30, 0); // 0 luck to avoid random crits in test
        Character target = new Character("Dummy", 200, 100, 30, 30, 10, 10, 20, 0);

        int initialHp = target.getCurrentHp();
        attacker.attack(target);
        // Damage = physAttack (50) - target physDefense (10) = 40
        assert target.getCurrentHp() == initialHp - 40 : "Physical damage calculation incorrect: expected " + (initialHp - 40) + ", got " + target.getCurrentHp();

        String healLog = target.healing(30);
        assert healLog != null && target.getCurrentHp() == initialHp - 10 : "Healing calculation incorrect";

        System.out.println("PASSED");
    }

    private static void testItemValidation() {
        System.out.print("[Test] Item validation (cannot use status items when healthy)... ");
        GameFactory factory = new GameFactory();
        Character tidus = factory.createTidus();

        // Tidus starts with Potion, Ether, Antidote. Let's find Antidote item index.
        int antidoteIdx = -1;
        for (int i = 0; i < tidus.getItems().size(); i++) {
            if (tidus.getItems().get(i).getName().equals("Antidote")) {
                antidoteIdx = i;
                break;
            }
        }

        assert antidoteIdx != -1 : "Tidus should have an Antidote";

        // Tidus is healthy (no poison), so using Antidote should return null and not consume it
        int initialQuantity = tidus.getItems().get(antidoteIdx).getQuantity();
        String result = tidus.useItem(antidoteIdx);
        assert result == null : "Antidote should not be usable when healthy";
        assert tidus.getItems().get(antidoteIdx).getQuantity() == initialQuantity : "Antidote quantity should not decrease";

        // Now inflict poison
        StatusEffect poison = new StatusEffect("Poison", 10, 3, false);
        tidus.addStatusEffect(poison);
        assert tidus.hasStatus("Poison") : "Tidus should be poisoned";

        // Now using Antidote should succeed
        result = tidus.useItem(antidoteIdx);
        assert result != null : "Antidote should be usable when poisoned";
        assert !tidus.hasStatus("Poison") : "Antidote should cure poison";

        System.out.println("PASSED");
    }

    private static void testStatusEffects() {
        System.out.print("[Test] Status effects processing and turn skipping... ");
        Character target = new Character("Target", 100, 50, 40, 40, 20, 20, 30, 0);

        StatusEffect poison = new StatusEffect("Poison", 15, 2, false);
        target.addStatusEffect(poison);

        int hpBefore = target.getCurrentHp();
        String log = target.processStatuses();
        assert target.getCurrentHp() == hpBefore - 15 : "Poison damage should apply";
        assert log.contains("suffers 15 damage") : "Poison log missing";

        StatusEffect paralysis = new StatusEffect("Paralysis", 0, 1, true);
        target.addStatusEffect(paralysis);
        assert !target.canAct() : "Paralyzed character cannot act";

        System.out.println("PASSED");
    }
}
