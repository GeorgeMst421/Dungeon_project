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
    private final MiniMapPanel miniMapPanel;
    private final MapPanel mapPanel;
    private final PlayerStatus playerStatus;

    public GameManager(AbstractPlayer player) {

        gameMap = new GameMap(25,25);

        playerController = new PlayerController(player,gameMap.getStartingRoom());

        enemySpawnManager = new EnemySpawnManager(player,gameMap);

        enemyTurnManager = new EnemyTurnManager(playerController,enemyControllers);
        enemyTurnManager.addEventListener(this);

        mapPanel = new MapPanel(playerController,gameMap);
        miniMapPanel = new MiniMapPanel(gameMap,playerController.getCurrentRoom());
        playerStatus = new PlayerStatus(player);
        playerStatus.addEventListener(this);
        spawnNewEnemies();

    }

    public void executeCommand(Command command) {
        boolean isEnemyTurn = true;
        switch (command) {
            case TURN_LEFT -> {
                playerController.turnLeft();
                isEnemyTurn = false;
            }
            case TURN_RIGHT -> {
                playerController.turnRight();
                isEnemyTurn = false;
            }
            case MOVE -> {
                if(playerController.facingEnemy()){
                    Game.log("Can't move. Facing Enemy");
                    isEnemyTurn = false;
                }
                else if(playerController.facingWall()){
                    Game.log("Can't move inside the wall");
                    isEnemyTurn = false;

                }else {
                    playerController.moveForward();
                    Game.log("You moved to " + playerController.getCurrentRoom().row + " " +playerController.getCurrentRoom().col);
                    if (playerController.getCurrentRoom().isFinish()) {
                        nextMap();
                    }
                }

            }
            case ATTACK -> {
                Optional<EnemyController> enemyControllerOptional = enemyControllers.stream()
                        .filter(eC -> eC.getRoom() == playerController.getFacingRoom())
                        .findFirst();

                if(enemyControllerOptional.isEmpty()){
                    Game.log("You are not facing an enemy");
                }
                else{
                    EnemyController enemyController = enemyControllerOptional.get();
                    playerController.attack(enemyController);
                    if (enemyController.isDead()) {
                        enemyControllers.remove(enemyController);
                        Game.log("Enemy died");
                        playerController.getEXP(enemyController.giveEXP());
                        Game.log("Got " + enemyController.giveEXP() + " exp");
                        
                    }
                }

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
        if(isEnemyTurn)
            enemyTurnManager.enemyTurn();
        else onEvent();

    }

    private void spawnNewEnemies() {
        //Ensuring that GameManager and EnemyTurnManager has the same reference to the List<EnemyController>
        List<EnemyController> spawnedEnemies = enemySpawnManager.spawnEnemies();
        enemyControllers.clear();
        enemyControllers.addAll(spawnedEnemies);
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
        spawnNewEnemies();

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

