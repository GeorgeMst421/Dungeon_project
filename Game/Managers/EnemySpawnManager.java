package Game.Managers;

import Enemy.AbstractEnemy;
import Game.Room;

import java.util.List;
import java.util.Random;

public class EnemySpawnManager {
    private List<Room> spawnPoints;
    private List<AbstractEnemy> enemyTypes;

    public EnemySpawnManager(List<Room> spawnPoints, List<AbstractEnemy> enemyTypes) {
        this.spawnPoints = spawnPoints;
        this.enemyTypes = enemyTypes;
    }

    public void spawnEnemy() {
        if (enemyTypes.isEmpty()) {
            return;
        }

        // Select a random spawn point from the list
        Room spawnPoint = spawnPoints.get(new Random().nextInt(spawnPoints.size()));

        // Select a random enemy type from the list
        AbstractEnemy enemy = enemyTypes.get(new Random().nextInt(enemyTypes.size()));

        //Spawn it
        spawnPoint.setEnemy(enemy);
        spawnPoint.occupy();
        enemy.setRoom(spawnPoint);
        // Remove enemy from the list
        enemyTypes.remove(enemy);
    }
}
