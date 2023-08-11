package Game.Managers;

import Enums.Command;
import Game.Managers.GameManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    private final GameManager gameManager;

    public InputHandler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Command command = convertKeyEventToCommand(e);
        gameManager.executeCommand(command);
    }

    private Command convertKeyEventToCommand(KeyEvent e) {
        return switch (e.getKeyChar()) {
            case 'a' -> Command.TURN_LEFT;
            case 'd' -> Command.TURN_RIGHT;
            case 'w' -> Command.MOVE;
            case 'x' -> Command.ATTACK;
            case 'h' -> Command.HEALTH_POTION;
            case 'm' -> Command.MANA_POTION;
            case 'r' -> Command.REST;
            case 'c' -> Command.SPELL;
            case 'p' -> Command.PICK_UP;
            case 'y' -> Command.YES;
            case 'n' -> Command.NO;
            case 'e' -> Command.EXIT;
            default  -> Command.NO_ACTION;
        };
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}

