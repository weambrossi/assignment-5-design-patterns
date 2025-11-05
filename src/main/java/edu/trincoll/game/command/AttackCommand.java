package edu.trincoll.game.command;

import edu.trincoll.game.model.Character;

/**
 * Command to execute an attack from one character to another.
 *
 * TODO 4a: Implement execute() and undo()
 *
 * Requirements for execute():
 * 1. Calculate damage: attacker.attack(target)
 * 2. Apply damage: target.takeDamage(calculatedDamage)
 * 3. Store the damage dealt for potential undo
 *
 * Requirements for undo():
 * 1. Heal the target for the amount of damage that was dealt
 * 2. Use target.heal(damageDealt)
 *
 * Note: This is a simplified undo - in a real game, you'd need to
 * restore mana usage, status effects, etc.
 */
public class AttackCommand implements GameCommand {
    private final Character attacker;
    private final Character target;
    private int damageDealt;

    public AttackCommand(Character attacker, Character target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public void execute() {
        int healthBefore = target.getStats().health();
        int damage = attacker.attack(target);
        target.takeDamage(damage);
        int healthAfter = target.getStats().health();
        // Store the actual net damage dealt (accounts for defense)
        damageDealt = Math.max(0, healthBefore - healthAfter);
    }

    @Override
    public void undo() {
        // Restore the target's health by the exact damage dealt
        target.heal(damageDealt);
    }

    @Override
    public String getDescription() {
        return String.format("%s attacks %s", attacker.getName(), target.getName());
    }
}
