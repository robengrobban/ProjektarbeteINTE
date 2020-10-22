package se.su.dsv.inte.projektarbete.characters;

import org.junit.jupiter.api.Test;
import se.su.dsv.inte.projektarbete.ElementType;
import se.su.dsv.inte.projektarbete.map.Map;
import se.su.dsv.inte.projektarbete.weapon.Weapon;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStateControllerTest {
    private static final CharacterStateController NEUTRAL_CONTROLLER = new CharacterStateController(StateType.NEUTRAL);
    private static final CharacterStateController HOSTILE_CONTROLLER = new CharacterStateController(StateType.HOSTILE);
    private static final State NEUTRAL_STATE = new NeutralState(NEUTRAL_CONTROLLER);
    private static final State HOSTILE_STATE = new HostileState(NEUTRAL_CONTROLLER);
    private static final State DEAD_STATE = new DeadState(NEUTRAL_CONTROLLER);
    private static final State CHASING_STATE = new ChasingState(NEUTRAL_CONTROLLER);

    @Test
    public void constructorSetsCorrectValues() {
        System.out.println("N: " + NEUTRAL_CONTROLLER.getCurrentState().toString());
        System.out.println("H: " + HOSTILE_CONTROLLER.getCurrentState().toString());
        assertEquals(NEUTRAL_STATE.toString(), NEUTRAL_CONTROLLER.getCurrentState().toString());
        assertEquals(HOSTILE_STATE.toString(), HOSTILE_CONTROLLER.getCurrentState().toString());
    }

    @Test
    public void constructorHandlesNullValues() {
        CharacterStateController nullController = new CharacterStateController(null);
        assertEquals(NEUTRAL_STATE.toString(), nullController.getCurrentState().toString());

    }

    @Test
    public void setsCorrectState() {
        NEUTRAL_CONTROLLER.setCurrentState(StateType.HOSTILE);
        assertEquals(HOSTILE_STATE.toString(), NEUTRAL_CONTROLLER.getCurrentState().toString());
        NEUTRAL_CONTROLLER.setCurrentState(StateType.NEUTRAL);
        assertEquals(NEUTRAL_STATE.toString(), NEUTRAL_CONTROLLER.getCurrentState().toString());
        NEUTRAL_CONTROLLER.setCurrentState(null);
        assertEquals(NEUTRAL_STATE.toString(), NEUTRAL_CONTROLLER.getCurrentState().toString());
        NEUTRAL_CONTROLLER.setCurrentState(StateType.DEAD);
        assertEquals(DEAD_STATE.toString(), NEUTRAL_CONTROLLER.getCurrentState().toString());
        NEUTRAL_CONTROLLER.setCurrentState(StateType.CHASING);
        assertEquals(CHASING_STATE.toString(), NEUTRAL_CONTROLLER.getCurrentState().toString());
    }
}