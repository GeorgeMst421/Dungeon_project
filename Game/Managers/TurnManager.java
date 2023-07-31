package Game.Managers;

import Game.Controllers.PlayerController;
import Game.Controllers.EnemiesController;
import Game.GameMap;

public class TurnManager {
    private final PlayerController playerController;
    private final EnemiesController enemiesController;
    private final GameMap gameMap;
    private boolean playerTurn;

    public TurnManager(PlayerController playerController, EnemiesController enemiesController, GameMap gameMap) {
        this.playerController = playerController;
        this.enemiesController = enemiesController;
        this.gameMap = gameMap;
        this.playerTurn = true; // assuming player goes first
    }

    public void nextTurn() {
        if (playerTurn) {
            // Execute player turn logic
            // This could involve prompting for input and performing player actions
            playerTurn = false;
        } else {
            // Execute enemy turn logic
            // This could involve iterating over all enemies and executing their behaviors
            enemiesController.moveEnemiesTowardsPlayer();
            playerTurn = true;
        }
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
