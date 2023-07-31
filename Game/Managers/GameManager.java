package Game.Managers;

import Enums.Command;
import Game.GameMap;
import Game.Controllers.PlayerController;
import UI.MiniMapPanel;

public class GameManager {
    private GameMap gameMap;
    private PlayerController playerController;
    private MiniMapPanel miniMapPanel;
    private LevelManager levelManager;
    public GameManager(PlayerController playerController, MiniMapPanel miniMapPanel, LevelManager levelManager,GameMap gameMap) {
        this.playerController = playerController;
        this.miniMapPanel = miniMapPanel;
        this.levelManager = levelManager;
        this.gameMap = gameMap;
    }


    public void executeCommand(Command command) {
        switch (command) {
            case TURN_LEFT -> {
                playerController.turnLeft();
            }
            case TURN_RIGHT -> {
                playerController.turnRight();
//                miniMapPanel.updateMiniMap(playerController.getCurrentRoom(), playerController.getCurrentDirection());
            }
            case MOVE -> {
                playerController.moveForward();
                if(playerController.getCurrentRoom().isFinish()) nextMap();
            }
            case ATTACK -> {
                playerController.attack();
            }
            case SPELL -> {
                playerController.spell();
            }
            case REST -> {
                playerController.rest();
            }
            case HEALTH_POTION -> {
                playerController.useHealthPot();
            }
            case MANA_POTION -> {
                playerController.useManaPot();
            }

        }
        miniMapPanel.updateMiniMap(playerController.getCurrentRoom(), playerController.getCurrentDirection());

    }

    public void spawnNewEnemy() {
        levelManager.generateEnemies(); // generates the list of enemies to be spawned depending on player level
        EnemySpawnManager spawnManager = new EnemySpawnManager(gameMap.getEmptyRooms(), levelManager.getEnemiesToSpawn());
        spawnManager.spawnEnemy();
    }
    public void nextMap() {
        // Generate a new map
        gameMap = levelManager.generateNextMap(); // Here also enemies are spawned

        // Update the player's current room to the starting room of the new map
        playerController.setCurrentRoom(gameMap.map[0][0]);

        // Update the mini map
        miniMapPanel.updateMiniMap(playerController.getCurrentRoom(), playerController.getCurrentDirection());
    }
}

