package Items;

import Enums.*;
import java.util.*;

import Game.Dice;
import Interfaces.*;
import Items.Weapons.Damage;
import Items.Weapons.PlayerWeapon;

public class ItemGenerator {
    static Random rng = new Random();

    public enum Rarity {
        NONE(0),
        FEEBLE(1),
        COMMON(4),
        RARE(8),
        SUPREME(12);
        int totalBonus;

        private Rarity(int totalBonus) {
            this.totalBonus = totalBonus;
        }
//        int getTotalBonus(){return totalBonus;}
    }

    static String[] namePrefix = {"laughing", "deadly", "eternal",
            "amphibious", "improbable",
            "reckless", "smiting", "boring",
            "mysterious"};
    // ...
    static Map<SlotType, List<String>> slotsMap = Map.of(
            SlotType.NECK, List.of("Necklace", "Scarf", "Amulet",
                    "Necktie", "Bowtie"),
            SlotType.FINGER, List.of("Ring", "Mittens", "Glove"),
            SlotType.MAIN_HAND, List.of("Sword", "Katana", "Staff",
                    "Axe", "Mace", "Halberd", "Cutlass", "Morningstar"),
            SlotType.OFF_HAND, List.of("Short Sword", "Dagger", "Baton",
                    "Spiked Skull")
    );
    // ...
    static String[] nameSuffix = {"of Doom", "of Pestilence",
            "of the Dead", "of Mythos",
            "of the clan McCloud",
            "of the Stars"};

    public static String nameGenerator(Rarity rarity, SlotType slotType) {

        List<String> slotNames = slotsMap.get(slotType);
        String slotName = slotNames.get(rng.nextInt(slotNames.size()));
        String rarityName;
        if (rarity == Rarity.NONE) {
            rarityName = "";
        } else {
            rarityName = rarity.toString().toLowerCase() + " ";
        }
        return new StringBuilder(rarityName)
                .append(namePrefix[rng.nextInt(namePrefix.length)])
                .append(" ")
                .append(slotName)
                .append(" ")
                .append(nameSuffix[rng.nextInt(nameSuffix.length)])
                .toString();
    }

    // ...
    public static Equippable randomEquippable() {
// None, Feeble, Common, Rare, Supreme
        Rarity[] rarityProbabilities =
                {Rarity.NONE, Rarity.NONE, Rarity.NONE, Rarity.NONE,
                        Rarity.NONE, Rarity.FEEBLE, Rarity.FEEBLE, Rarity.FEEBLE,
                        Rarity.FEEBLE, Rarity.FEEBLE, Rarity.COMMON,
                        Rarity.COMMON, Rarity.COMMON, Rarity.COMMON,
                        Rarity.RARE, Rarity.RARE, Rarity.RARE,
                        Rarity.SUPREME};
        Rarity rarity = rarityProbabilities[rng.nextInt(rarityProbabilities
                .length)];
        SlotType slotType = SlotType.values()[rng.nextInt(SlotType
                .values().length)];
        boolean isWeapon = slotType == SlotType.MAIN_HAND || slotType == SlotType.OFF_HAND;
        String itemName = nameGenerator(rarity, slotType);
// we have to split the bonus points of the item into 1-3 of the possible bint totalBonus = rarity.totalBonus;
// HP_BONUS, MP_BONUS, STR_BONUS, INT_BONUS
        int[] bonusDiv = {0, 0, 0, 0};
        EffectType[] bonusItm = {EffectType.HP_BOOST, EffectType.MP_BOOST,
                EffectType.STR_BOOST, EffectType.INT_BOOST};
        while (rarity.totalBonus > 0) {
            bonusDiv[rng.nextInt(bonusDiv.length)] += 1;
            rarity.totalBonus -= 1;
        }
        List<ItemEffect> itemBonuses = new ArrayList<>();
        for (int i = 0; i < bonusDiv.length; i++) {
            if (bonusDiv[i] > 0) {
                itemBonuses.add(new ItemEffect(bonusItm[i], bonusDiv[i]));
            }
        }
        if (isWeapon) {
            List<Damage> weaponDamages = new ArrayList<>();
            // Generate 1-3 random damages
            for (int i = 0; i < rng.nextInt(3) + 1; i++) {
                DamageType dmgType = DamageType.values()[rng.nextInt(DamageType.values().length)];
                Dice dice = new Dice(rng.nextInt(6) + 1, rng.nextInt(6) + 1);
                int bonus = rng.nextInt(rarity.totalBonus + 1);
                weaponDamages.add(new Damage(dmgType, dice, bonus));
            }
// I always use simple names for the variable "name" for example name = "PlayerWeapon". The variable itemName is actually the description of the weapon
            return new PlayerWeapon(slotType.name(), itemName, weaponDamages, itemBonuses, slotType);
        } else {
            return new Equippable() {
                @Override
                public SlotType getSlotType() {
                    return slotType;
                }

                @Override
                public boolean isWeapon() {
                    return false;
                }

                @Override
                public String getName() {
                    return "Armor";
                }

                @Override
                public String getDescription() {
                    return itemName;
                }

                @Override
                public ItemType getItemType() {
                    return ItemType.EQUIPPABLE;
                }

                @Override
                public List<ItemEffect> getItemEffects() {
                    return itemBonuses;
                }
                @Override
                public String toString(){ return getName()+ " " + getDescription();}
            };
        }
    }
}

