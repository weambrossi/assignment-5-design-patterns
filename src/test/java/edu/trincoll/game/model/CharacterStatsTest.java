package edu.trincoll.game.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CharacterStatsTest {

    @Test
    void create_initializesFullHealthAndMana() {
        CharacterStats stats = CharacterStats.create(120, 45, 30, 80);

        assertThat(stats.health()).isEqualTo(120);
        assertThat(stats.maxHealth()).isEqualTo(120);
        assertThat(stats.mana()).isEqualTo(80);
        assertThat(stats.maxMana()).isEqualTo(80);
    }

    @Test
    void withHealth_clampsWithinBounds() {
        CharacterStats stats = CharacterStats.create(100, 40, 25, 50);

        assertThat(stats.withHealth(75).health()).isEqualTo(75);
        assertThat(stats.withHealth(200).health()).isEqualTo(100);
        assertThat(stats.withHealth(-10).health()).isZero();
    }

    @Test
    void withMana_clampsWithinBounds() {
        CharacterStats stats = CharacterStats.create(90, 30, 20, 40);

        assertThat(stats.withMana(15).mana()).isEqualTo(15);
        assertThat(stats.withMana(100).mana()).isEqualTo(40);
        assertThat(stats.withMana(-5).mana()).isZero();
    }

    @Test
    void isAliveAndDead_reflectCurrentHealth() {
        CharacterStats stats = CharacterStats.create(50, 20, 10, 0);
        assertThat(stats.isAlive()).isTrue();
        assertThat(stats.isDead()).isFalse();

        CharacterStats downed = stats.withHealth(0);
        assertThat(downed.isAlive()).isFalse();
        assertThat(downed.isDead()).isTrue();
    }

    @Test
    void constructor_rejectsNegativeHealthOrMaxHealth() {
        assertThatThrownBy(() -> new CharacterStats(-1, 50, 10, 10, 0, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Health values must be positive");

        assertThatThrownBy(() -> new CharacterStats(10, 0, 10, 10, 0, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Health values must be positive");
    }

    @Test
    void constructor_rejectsHealthExceedingMax() {
        assertThatThrownBy(() -> new CharacterStats(120, 100, 15, 10, 0, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Health cannot exceed max health");
    }

    @Test
    void constructor_rejectsNegativeCombatStats() {
        assertThatThrownBy(() -> new CharacterStats(10, 10, -1, 5, 0, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Stats cannot be negative");

        assertThatThrownBy(() -> new CharacterStats(10, 10, 5, -1, 0, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Stats cannot be negative");
    }

    @Test
    void constructor_rejectsInvalidManaValues() {
        assertThatThrownBy(() -> new CharacterStats(10, 10, 5, 5, -1, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Mana values cannot be negative");

        assertThatThrownBy(() -> new CharacterStats(10, 10, 5, 5, 5, -1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Mana values cannot be negative");

        assertThatThrownBy(() -> new CharacterStats(10, 10, 5, 5, 6, 5))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Mana cannot exceed max mana");
    }
}
