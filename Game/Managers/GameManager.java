package Game.Managers;

import Enums.*;
import Game.*;
import Game.Controllers.*;
import Interfaces.EventListener;
import UI.*;
import character.AbstractChars.*;

import java.util.*;


public class GameManager implements EventListener {
    private GameMap gameMap;
    private final PlayerController playerController;
    private final List<EnemyController> enemyControllers = new ArrayList<>();
    private final EnemySpawnManager enemySpawnManager;
    private final EnemyTurnManager enemyTurnManager;
    private final PlayerTurnManager playerTurnManager;
    private final MiniMapPanel miniMapPanel;
    private final MapPanel mapPanel;
    private final PlayerStatus playerStatus;

    public GameManager(AbstractPlayer player) {

        gameMap = new GameMap(25,25);

        playerController = new PlayerController(player,gameMap.getStartingRoom());

        enemySpawnManager = new EnemySpawnManager(player,gameMap);

        enemyTurnManager = new EnemyTurnManager(playerController,enemyControllers);
        enemyTurnManager.addEventListener(this);

        playerTurnManager = new PlayerTurnManager(playerController, enemyControllers);
        playerTurnManager.addEventListener(this);

        mapPanel = new MapPanel(playerController,gameMap);
        miniMapPanel = new MiniMapPanel(gameMap,playerController.getCurrentRoom());
        playerStatus = new PlayerStatus(player);
        playerStatus.addEventListener(this);
        spawnEnemies();

    }

    public void executeCommand(Command command) {
        boolean isEnemyTurn = playerTurnManager.playerTurn(command); // returns a boolean for the enemy turn

        if (playerController.getCurrentRoom().isFinish()) {
            nextMap();
        }
        if(enemyControllers.size() < 3) spawnEnemies();
        if(isEnemyTurn)
            enemyTurnManager.enemyTurn();
        else onEvent();

    }

    private void spawnEnemies() {
        while(enemyControllers.size() < 3)
            enemyControllers.add(enemySpawnManager.spawnEnemy());
    }
    private void nextMap() {
        // Generate a new map
        Game.mapCounter++;
        gameMap = new GameMap(25,25);


        // Update the player's current room to the starting room of the new map
        playerController.setCurrentRoom(gameMap.getStartingRoom());
        playerController.setCurrentDirection(Direction.SOUTH);

        enemySpawnManager.setGameMap(gameMap);

        miniMapPanel.setGameMap(gameMap);
        mapPanel.setGameMap(gameMap);

        enemyControllers.clear();
        spawnEnemies();

    }

    @Override
    public void onEvent() {
        updateMap();
        playerStatus.updateStatus();
    }

    private void updateMap(){
        miniMapPanel.updateMiniMap(playerController.getCurrentRoom(), playerController.getCurrentDirection());
        mapPanel.updateRoomAndRepaint(playerController.getCurrentRoom());

    }

    public MapPanel getMapPanel(){
        return mapPanel;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public MiniMapPanel getMiniMapPanel() {
        return miniMapPanel;
    }
}

