package Interfaces;

public interface Consumable extends Item {
    int getUsesLeft();
    void use();
}
