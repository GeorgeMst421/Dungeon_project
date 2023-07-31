package Enemy;

import Enums.DamageType;
import Game.Room;
import Items.Weapons.*;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractEnemy {
    private Room room;
    private int hitPoints;
    private final String name;
    private final EnemyWeapon enemyWeapon;

    public AbstractEnemy(int hp, String n, EnemyWeapon wp){
        hitPoints = hp;
        name = n;
        enemyWeapon = wp;
        room = null;
    }

    protected abstract int calculateDamageTaken(DamageType dmgType, int dmg) ;
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
    public boolean isAlive(){
        return hitPoints > 0;
    }
    public int getHP(){
        return hitPoints;
    }
    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return name + " HP: " + hitPoints ;
    }

    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room){
        this.room = room;
    }
}
