package Game.Managers;

import Enemy.*;
import Game.Controllers.EnemiesController;
import Game.GameMap;
import Game.Managers.EnemySpawnManager;
import character.AbstractChars.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelManager {
    private final int MAX_MAPS = 5;
    private int currentMapIndex = 1;
    private List<AbstractEnemy> enemiesToSpawn = new ArrayList<>();
    private AbstractPlayer player;

    public LevelManager(AbstractPlayer player) {
        this.player = player;
    }

    public GameMap generateNextMap() {
        if (currentMapIndex > MAX_MAPS) {
            throw new IllegalStateException("Maximum number of maps reached.");
        }

        GameMap newMap = new GameMap(25, 25);

        currentMapIndex++;

        return newMap;
    }

    public void spawnEnemies(GameMap map, EnemiesController eC){
        generateEnemies();

        EnemySpawnManager spawnManager = new EnemySpawnManager(map.getEmptyRooms(), enemiesToSpawn, eC);

        for(int i=0; i<enemiesToSpawn.size(); i++){
            spawnManager.spawnEnemy();
        }
    }

     private void generateEnemies() {
        enemiesToSpawn.clear();
        Random rng = new Random();
        int numberOfEnemies = 3;

        for (int i = 0; i < numberOfEnemies; i++) {
            switch (player.getLevel()) {
                case 1 -> {
                    enemiesToSpawn.add(new Slime());
                }
                case 2 -> {
                    if (rng.nextBoolean()) {
                        enemiesToSpawn.add(new Slime());
                    } else {
                        enemiesToSpawn.add(new Goblin());
                    }
                }
                case 3 -> {
                    int random = rng.nextInt(2);
                    if (random == 0) enemiesToSpawn.add(new Goblin());
                    else enemiesToSpawn.add(new Ghost());
                }
                case 4 -> {
                    int random = rng.nextInt(3);
                    if (random == 0) enemiesToSpawn.add(new Goblin());
                    else if (random == 1) enemiesToSpawn.add(new Ghost());
                    else enemiesToSpawn.add(new Skeleton());
                }
                case 5 -> {
                    int random = rng.nextInt(4);
                    if (random == 0) enemiesToSpawn.add(new Ghost());
                    else if (random == 1) enemiesToSpawn.add(new Skeleton());
                    else if (random == 2) enemiesToSpawn.add(new SkDemon());
                    else enemiesToSpawn.add(new SkLord());
                }
                case 6 -> {
                    int random = rng.nextInt(3);
                    if (random == 0) enemiesToSpawn.add(new Skeleton());
                    else if (random == 1) enemiesToSpawn.add(new SkDemon());
                    else enemiesToSpawn.add(new SkLord());
                }
                default -> throw new IllegalStateException("Player level exceeds maximum level.");
            }
        }

        if (currentMapIndex == MAX_MAPS) {
            enemiesToSpawn.clear();
            enemiesToSpawn.add(new Leoric());
        }
    }
}
