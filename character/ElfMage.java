package character;

import Enums.DamageType;
import character.AbstractChars.AbstractMage;

import java.util.Map;

public class ElfMage extends AbstractMage {
//    Map<DamageType, Integer> defenses = Map.of( DamageType.SLASHING,1,
//                                                DamageType.BLUNT,0,
//                                                DamageType.MAGICAL,2);
    public ElfMage(String name){
        this.name = name;
        maxHP = currentHP = baseHP = 60;
        maxMP = currentMP = baseMP = 15;
        strength = baseStr = 6;
        intelligence = baseInt = 12;
        defenses = Map.of(  DamageType.SLASHING,1,
                            DamageType.BLUNT,0,
                            DamageType.MAGICAL,2);
        this.levelUP();
    }

    @Override
    public String getPlayerRace(){
        return "Elf";
    }
}
