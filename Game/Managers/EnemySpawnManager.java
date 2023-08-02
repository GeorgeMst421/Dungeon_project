package Game.Managers;

import Enemy.*;
import Game.Controllers.EnemyController;
import Game.Game;
import Game.GameMap;
import Game.Room;
import character.AbstractChars.AbstractPlayer;

import java.util.*;
import java.util.random.RandomGenerator;

public class EnemySpawnManager {
    private final AbstractPlayer player;
    private GameMap gameMap;

    public EnemySpawnManager(AbstractPlayer player, GameMap gameMap) {
        this.player = player;
        this.gameMap = gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }


    public EnemyController spawnEnemy() {
        AbstractEnemy enemy = generateEnemy();
        List<Room> spawnPoints = gameMap.getEmptyRooms();
        Room spawnPoint = spawnPoints.get(new Random().nextInt(spawnPoints.size()));
        spawnPoint.setEnemy(enemy);
        spawnPoint.occupy();
        System.out.println("Enemy spawned at " + spawnPoint.col + spawnPoint.row);
        return new EnemyController(enemy, gameMap, spawnPoint);
    }
    private AbstractEnemy generateEnemy() {
        AbstractEnemy enemy;
        RandomGenerator rng = new Random();
        switch (player.getLevel()) {
            case 1 -> {
                enemy = new Slime();
            }
            case 2 -> {
                if (rng.nextBoolean()) {
                    enemy = (new Slime());
                } else {
                    enemy = (new Goblin());
                }
            }
            case 3 -> {
                int random = rng.nextInt(2);
                if (random == 0) enemy = (new Goblin());
                else enemy = (new Ghost());
            }
            case 4 -> {
                int random = rng.nextInt(3);
                if (random == 0) enemy = (new Goblin());
                else if (random == 1) enemy = (new Ghost());
                else enemy = (new Skeleton());
            }
            case 5 -> {
                int random = rng.nextInt(4);
                if (random == 0) enemy = (new Ghost());
                else if (random == 1) enemy = (new Skeleton());
                else if (random == 2) enemy = (new SkDemon());
                else enemy = (new SkLord());
            }
            case 6 -> {
                int random = rng.nextInt(3);
                if (random == 0) enemy = (new Skeleton());
                else if (random == 1) enemy = (new SkDemon());
                else enemy = (new SkLord());
            }
            default -> throw new IllegalStateException("Player level exceeds maximum level.");
        }
        System.out.println("enemy added to enemiesToSpawn List");
        if (Game.mapCounter == Game.NUMBER_OF_MAPS) {
            enemy = (new Leoric());
        }
        return enemy;
    }


}
//    public List<EnemyController> spawnEnemies(){
//        List<AbstractEnemy> enemiesToSpawn =  generateEnemies();
//        List<Room> spawnPoints = gameMap.getEmptyRooms();
//        if (enemiesToSpawn.isEmpty()) {
//            return null;
//        }
//        List<EnemyController> enemyControllers = new ArrayList<>();
//        while (!enemiesToSpawn.isEmpty()){
//            // Select a random spawn point from the list
//            Room spawnPoint = spawnPoints.get(new Random().nextInt(spawnPoints.size()));
//
//            // Select a random enemy type from the list
//            AbstractEnemy enemy = enemiesToSpawn.get(new Random().nextInt(enemiesToSpawn.size()));
//
//            //Spawn it
//            spawnPoint.setEnemy(enemy);
//            spawnPoint.occupy();
//            System.out.println("Enemy spawned at " + spawnPoint.col + spawnPoint.row);
//
//            enemyControllers.add(new EnemyController(enemy, gameMap, spawnPoint));
//            // Remove enemy from the list
//            enemiesToSpawn.remove(enemy);
//        }
//        return enemyControllers;
//    }
//     private List<AbstractEnemy> generateEnemies() {
//         List<AbstractEnemy> enemiesToSpawn = new ArrayList<>();
//         Random rng = new Random();
//
//        for (int i = 0; i < Game.ENEMIES_PER_MAP; i++) {
//            switch (player.getLevel()) {
//                case 1 -> {
//                    enemiesToSpawn.add(new Slime());
//                }
//                case 2 -> {
//                    if (rng.nextBoolean()) {
//                        enemiesToSpawn.add(new Slime());
//                    } else {
//                        enemiesToSpawn.add(new Goblin());
//                    }
//                }
//                case 3 -> {
//                    int random = rng.nextInt(2);
//                    if (random == 0) enemiesToSpawn.add(new Goblin());
//                    else enemiesToSpawn.add(new Ghost());
//                }
//                case 4 -> {
//                    int random = rng.nextInt(3);
//                    if (random == 0) enemiesToSpawn.add(new Goblin());
//                    else if (random == 1) enemiesToSpawn.add(new Ghost());
//                    else enemiesToSpawn.add(new Skeleton());
//                }
//                case 5 -> {
//                    int random = rng.nextInt(4);
//                    if (random == 0) enemiesToSpawn.add(new Ghost());
//                    else if (random == 1) enemiesToSpawn.add(new Skeleton());
//                    else if (random == 2) enemiesToSpawn.add(new SkDemon());
//                    else enemiesToSpawn.add(new SkLord());
//                }
//                case 6 -> {
//                    int random = rng.nextInt(3);
//                    if (random == 0) enemiesToSpawn.add(new Skeleton());
//                    else if (random == 1) enemiesToSpawn.add(new SkDemon());
//                    else enemiesToSpawn.add(new SkLord());
//                }
//                default -> throw new IllegalStateException("Player level exceeds maximum level.");
//            }
//            System.out.println("enemy added to enemiesToSpawn List");
//        }
//
//        if (Game.mapCounter == Game.NUMBER_OF_MAPS) {
//            enemiesToSpawn.clear();
//            enemiesToSpawn.add(new Leoric());
//        }
//        return enemiesToSpawn;
//    }
//}
