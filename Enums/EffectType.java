package Enums;

public enum EffectType {
    NONE(false),
    HP_REPLENISH(true),
    MP_REPLENISH(true),
    HP_BOOST(false),
    MP_BOOST(false),
    STR_BOOST(false),
    INT_BOOST(false);

    final private boolean usable;

    EffectType(boolean b){
        usable = b;
    }

    boolean isUsable() {return usable;}
}
