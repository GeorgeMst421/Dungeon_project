package Enemy;

import Enums.DamageType;
import Game.Room;
import Items.Weapons.*;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractEnemy {
    private int hitPoints;
    private final String name;
    private final EnemyWeapon enemyWeapon;

    public AbstractEnemy(int hp, String n, EnemyWeapon wp) {
        hitPoints = hp;
        name = n;
        enemyWeapon = wp;
    }

    protected abstract int calculateDamageTaken(DamageType dmgType, int dmg);

    public abstract int giveEXP();

    public abstract Image getSprite() throws IOException;

    public Map<DamageType, Integer> attack() {
        return enemyWeapon.hit();
    }

    public void takeDamage(Map<DamageType, Integer> damages) {
        for (Map.Entry<DamageType, Integer> entry : damages.entrySet()) {
            DamageType dmgType = entry.getKey();
            int dmg = entry.getValue();
            int dmgTaken = calculateDamageTaken(dmgType, dmg);
            hitPoints -= dmgTaken;
        }

    }

    public boolean isDead() {
        return hitPoints <= 0;
    }

    public int getHP() {
        return hitPoints;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " HP: " + hitPoints;
    }
}

