package Game.Managers;

import Enemy.AbstractEnemy;
import Game.Controllers.EnemiesController;
import Game.Controllers.PlayerController;

public class TurnManager {

    PlayerController playerController;
    EnemiesController enemiesController;
    boolean isPlayersTurn;

    public TurnManager(PlayerController pC, EnemiesController eC){
        playerController = pC;
        enemiesController = eC;
        isPlayersTurn = true;
    }




}
