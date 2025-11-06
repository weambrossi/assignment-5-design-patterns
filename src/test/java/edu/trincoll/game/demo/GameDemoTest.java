package edu.trincoll.game.demo;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class GameDemoTest {

    @Test
    void main_runsAllDemosAndProducesNarrative() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capture = new ByteArrayOutputStream();
        PrintStream capturingStream = new PrintStream(capture, true, StandardCharsets.UTF_8);
        try {
            System.setOut(capturingStream);
            GameDemo.main(new String[0]);
        } finally {
            System.setOut(originalOut);
            capturingStream.close();
        }

        String output = capture.toString(StandardCharsets.UTF_8);
        assertThat(output).contains(
            "DESIGN PATTERNS GAME DEMO",
            "DEMO 1: FACTORY METHOD PATTERN",
            "DEMO 2: STRATEGY PATTERN",
            "DEMO 3: COMMAND PATTERN",
            "DEMO 4: TEMPLATE METHOD PATTERN",
            "DEMO 5: ALL PATTERNS WORKING TOGETHER",
            "Demo complete! All patterns working together."
        );

        assertThat(output)
            .as("demo output should highlight key collaboration moments")
            .contains("✓ Hero attacks Boss", "✓ Boss uses POWER ATTACK", "✓ Hero healed +25 HP");
    }
}
