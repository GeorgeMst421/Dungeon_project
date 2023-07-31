package Game.Managers;

import Enums.Command;
import Enums.Direction;
import Game.Controllers.EnemiesController;
import Game.GameMap;
import Game.Controllers.PlayerController;
import UI.MapPanel;
import UI.MiniMapPanel;

public class GameManager {
    private GameMap gameMap;
    private PlayerController playerController;
    private EnemiesController enemiesController;
    private MiniMapPanel miniMapPanel;
    private LevelManager levelManager;
    private MapPanel mapPanel;
    public GameManager(PlayerController pC, MiniMapPanel mMP, LevelManager lM,GameMap gM,MapPanel mP) {
        playerController = pC;
        miniMapPanel = mMP;
        levelManager = lM;
        gameMap = gM;
        mapPanel = mP;
        enemiesController = new EnemiesController(gM,pC);
    }


    public void executeCommand(Command command) {
        switch (command) {
            case TURN_LEFT -> {
                playerController.turnLeft();
            }
            case TURN_RIGHT -> {
                playerController.turnRight();
            }
            case MOVE -> {
                playerController.moveForward();
                if(playerController.getCurrentRoom().isFinish()) {
                    nextMap();
                };
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
        mapPanel.updateRoomAndRepaint(playerController.getCurrentRoom());
    }

    private void spawnNewEnemies() {
        levelManager.spawnEnemies(gameMap, enemiesController);
    }
    private void nextMap() {
        // Generate a new map
        gameMap = levelManager.generateNextMap(); // Here also enemies are spawned

        // Update the player's current room to the starting room of the new map
        playerController.setCurrentRoom(gameMap.getStartingRoom());
        playerController.setCurrentDirection(Direction.SOUTH);

        miniMapPanel.setGameMap(gameMap);

        spawnNewEnemies();

    }
}

