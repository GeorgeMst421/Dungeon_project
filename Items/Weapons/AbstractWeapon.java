package Items.Weapons;

import Enums.DamageType;
import Enums.SlotType;
import Items.AbstractItem;

import java.util.*;

public abstract class AbstractWeapon extends AbstractItem{

    protected List<Damage> damages;

    public AbstractWeapon(String name, String description, List<Damage> damages) {
        super(name, description);
        this.damages = damages;
    }

    // returns a list of damages dealt by this weapon
    public Map<DamageType, Integer> hit() {
        Map<DamageType, Integer> results = new HashMap<>();
        for (Damage damage : damages) {
            results.put(damage.getDamageType(), damage.roll());
        }
        return results;
    }

}

//    private final Dice dice;
//    private int diceBonus=0;
//    private final DamageType dmgType;
//
//    public AbstractWeapon(String name, String description, Dice dice, int diceBonus, DamageType dmgType){
//        super(name,description);
//        this.dice = dice;
//        this.diceBonus = diceBonus;
//        this.dmgType = dmgType;
//    }
//    public DamageType getDamageType(){
//        return dmgType;
//    }
//    public int hit(){
//        return dice.Roll(diceBonus);
//    }
//

