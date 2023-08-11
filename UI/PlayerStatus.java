package UI;

import Interfaces.*;
import Interfaces.EventListener;
import Items.Consumables.*;
import character.AbstractChars.AbstractPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayerStatus extends JLabel  {

    private final List<EventListener> listeners = new ArrayList<>();

    public static JLabel playerInfo;
    private final AbstractPlayer player;
    public PlayerStatus(AbstractPlayer player){
        this.player = player;
        this.setPreferredSize(new Dimension(256,256));
//        this.setBorder(new EmptyBorder(0,0,0,0));
        updateStatus();
    }

    public void updateStatus(){
        StringBuilder pstate = new StringBuilder("<html>");
        pstate.append(player.getName()).append("<br/>");
        pstate.append(player.getPlayerClass()).append(" / " );
        pstate.append(player.getPlayerRace()).append("<br/>");
        pstate.append(player.getLevel()).append("/").
                append(player.getExperiencePoints()).append("<br/>").
                append("<hr/>");

        pstate.append("HP: ").append(player.getCurrentHP())
                .append("/").append(player.getMaxHP()).append("<br/>");

        pstate.append("MP: ").append(player.getCurrentMP())
                .append("/").append(player.getMaxMP()).append("<br/>");

        pstate.append("Str: ").append(player.getStrength()).append("<br/>");
        pstate.append("Int: ").append(player.getIntelligence()).append("<br/>");
        pstate.append("<hr/>");

        pstate.append("Items<br>");
        for(Equippable e: player.getEquippedItems().values()) {
            pstate.append(e.getSlotType()).append(": ").append(e.getName()).append("<br/>");
        }
        pstate.append("<hr/>");

        Map<String, Integer> potionStats = new HashMap<>();
        for(Item u: player.getInventory()) {
            if(u instanceof HealthPotion) {
                potionStats.put("Healing", potionStats.getOrDefault("Healing", 0) + 1);
            } else if(u instanceof ManaPotion) {
                potionStats.put("Mana", potionStats.getOrDefault("Mana", 0) + 1);
            }
        }

        pstate.append("Healing Potions: ")
                .append(potionStats.getOrDefault("Healing", 0))
                .append("<br/>");
        pstate.append("Mana Potions: ")
                .append(potionStats.getOrDefault("Mana", 0))
                .append("<br/>");
        this.setText(pstate.toString());
    }
    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }
}
