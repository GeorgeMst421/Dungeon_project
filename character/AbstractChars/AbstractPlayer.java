package character.AbstractChars;

import Enums.*;
import Items.ItemEffect;
import Interfaces.*;
import Items.Weapons.*;

import java.util.*;
import java.util.stream.Collectors;


public abstract class AbstractPlayer {
    protected String name;
    protected int baseHP, baseMP, baseStr, baseInt, strength, intelligence;
    protected int maxHP,currentHP,maxMP,currentMP;
    protected int experiencePoints=0,level=0;
    protected Map<Integer, Integer> bonusHP, bonusMP, bonusStr, bonusInt;
    public Map<DamageType, Integer> defenses;
    public Map<SlotType, Equippable> equipedItems;
    public List<Consumable> inventory;

    public AbstractPlayer(){
        equipedItems = new HashMap<>();
        inventory = new ArrayList<>();
    }

    public Map<DamageType, Integer> weaponAttack(){

        Map<DamageType, Integer> finalDamages = ((PlayerWeapon) equipedItems.get(SlotType.MAIN_HAND)).hit();

        finalDamages.put(DamageType.SLASHING, finalDamages.get(DamageType.SLASHING) + strength);
        finalDamages.put(DamageType.BLUNT, finalDamages.get(DamageType.BLUNT) + strength);
        finalDamages.put(DamageType.MAGICAL, finalDamages.get(DamageType.MAGICAL) + intelligence);

        return finalDamages;
    }
    public void addXP(int exp) {
        experiencePoints += exp;
        while (true) {
            if (level == 1 && experiencePoints >= 300) {
                levelUP();
            } else if (level == 2 && experiencePoints >= 900) {
                levelUP();
            } else if (level == 3 && experiencePoints >= 2700) {
                levelUP();
            } else if (level == 4 && experiencePoints >= 6500) {
                levelUP();
            } else if (level == 5 && experiencePoints >= 14000) {
                levelUP();
            } else if (level < 6 && experiencePoints >= 14000) {
                levelUP();
            } else {
                break;
            }
        }
    }
    public void levelUP(){
        level += 1;
        getBonuses(level);
    }

    void getBonuses(int level){
        maxHP += bonusHP.get(level);
        currentHP += bonusHP.get(level);
        maxMP += bonusMP.get(level);
        currentMP += bonusMP.get(level);
        strength += bonusStr.get(level);
        intelligence += bonusInt.get(level);
    }

    public Equippable equip(SlotType s, Equippable e) {

        //First remove the already equipped item if exists
        Equippable removedItem = null;
        if (equipedItems.get(s) != null) {
            removedItem = remove(s);
        }

        /*Then "Equip" the other. Get the list of effects and
         * apply them to the state of the player */
        equipedItems.put(s,e);
        var effectsList = e.getItemEffects();
        for (ItemEffect effect : effectsList) {

            EffectType effectType = effect.getEffectType(); // Type
            int amount = effect.getAmount(); //Amount
            switch (effectType) {
                case HP_BOOST -> maxHP += amount;
                case MP_BOOST -> maxMP += amount;
                case STR_BOOST -> strength += amount;
                case INT_BOOST -> intelligence += amount;
                case HP_REPLENISH -> {
                    currentHP += amount;
                    if (currentHP > maxHP) currentHP = maxHP;
                }
                case MP_REPLENISH -> {
                    currentMP += amount;
                    if (currentMP > maxMP) currentMP = maxMP;
                }
            }
        }
        return removedItem;
    }

    public Equippable remove(SlotType s) {

        Equippable removedItem = equipedItems.remove(s);

        var effectsList = removedItem.getItemEffects();
        for(ItemEffect effect: effectsList){
            EffectType type = effect.getEffectType();
            int amount = effect.getAmount();
            switch(type){
                case HP_BOOST ->{
                    maxHP -= amount;
                    if (currentHP > maxHP) currentHP = maxHP;
                }
                case MP_BOOST ->{
                    maxMP -= amount;
                    if (currentMP > maxMP) currentMP = maxMP;
                }
                case STR_BOOST -> strength -= amount;
                case INT_BOOST -> intelligence -= amount;
            }
        }
        return removedItem;
    }
    public <T extends Consumable> List<T> getConsumableItems(Class<T> type) {
        return inventory.stream()
                .filter(item -> type.isInstance(item) && item.getUsesLeft() > 0)
                .map(item -> (T) item)
                .collect(Collectors.toList());
    }

    public void useItem(Consumable item){
        if(!inventory.contains(item)) return;
        if(item.getUsesLeft() == 0) return;

        item.use();
        var effects = item.getItemEffects();
        for(ItemEffect effect: effects){
            switch (effect.getEffectType()){
                case HP_REPLENISH -> {
                    currentHP += effect.getAmount();
                    if(currentHP>maxHP) currentHP = maxHP;
                }
                case MP_REPLENISH -> {
                    currentMP += effect.getAmount();
                    if(currentMP>maxMP) currentMP = maxMP;
                }
            }
        }
    }
    public void pickItem(Consumable i){
        inventory.add(i);
    }
    public void dropItem(Consumable i){
        inventory.remove(i);
    }
    public void rest(){

        if ((currentHP + 5) > maxHP) {
            currentHP = maxHP;
        } else {
            currentHP += 5;
        }

        if ((currentMP + 5) > maxMP) {
            currentMP = maxMP;
        } else {
            currentMP += 5;
        }

    }
    public void reduceMP(int mp){
        currentMP -= mp;
    }

    public abstract String getPlayerClass();
    public abstract String getPlayerRace();

    public String getName(){return name;}
    public int getBaseStr() {
        return baseStr;
    }
    public int getBaseInt() {
        return baseInt;
    }
    public int getBaseHP() {
        return baseHP;
    }
    public int getBaseMP() {
        return baseMP;
    }
    public int getStrength() {
        return strength;
    }
    public int getIntelligence() {
        return intelligence;
    }
    public int getMaxHP() {
        return maxHP;
    }
    public int getCurrentHP() {
        return currentHP;
    }
    public int getMaxMP() {
        return maxMP;
    }
    public int getCurrentMP() {
        return currentMP;
    }
    public int getLevel() {
        return level;
    }
    public int getExperiencePoints(){return experiencePoints;}
    public Map<SlotType, Equippable> getEquippedItems(){return equipedItems;}

    public List<Consumable> getInventory(){return inventory;}

}