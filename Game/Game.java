package Game;

import Enums.*;
import Game.Controllers.*;
import Game.Managers.*;
import Items.ItemEffect;
import Items.Weapons.*;
import UI.*;
import character.*;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Game {
    public static void main(String[] args){

        GameMap gameMap = new GameMap(25,25);
        gameMap.printMap();
        //Creating the Player with it's starting weapon
        OrcMage Radagon = new OrcMage("Radagon");
        Radagon.equip(SlotType.MAIN_HAND,new PlayerWeapon("SimpleMace","A plain mace",
                List.of(new Damage(DamageType.BLUNT,new Dice(1,6),0)),
                List.of(new ItemEffect(EffectType.STR_BOOST,1)), SlotType.MAIN_HAND)
        );

        LevelManager levelManager = new LevelManager(Radagon);
        PlayerController playerController = new PlayerController(Radagon,gameMap.getStartingRoom());


        //Panels
        MiniMapPanel miniMapPanel = new MiniMapPanel(gameMap,playerController.getCurrentRoom());
        MapPanel mapPanel = new MapPanel(playerController,gameMap);
        mapPanel.setCurrentRoom(playerController.getCurrentRoom());
        PlayerStatus playerStatus = new PlayerStatus(Radagon);

        //MainGameManager
        GameManager gameManager = new GameManager(playerController,miniMapPanel,levelManager,gameMap,mapPanel);
        InputHandler inputHandler = new InputHandler(gameManager);




        //Frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(miniMapPanel, BorderLayout.WEST);
        frame.add(mapPanel,BorderLayout.CENTER);
        frame.add(playerStatus,BorderLayout.EAST);

        frame.pack(); // sizes the frame to the preferred size of its components
        frame.setLocationRelativeTo(null); // centers the frame on screen
        frame.setVisible(true);
        frame.requestFocusInWindow();

        frame.addKeyListener(inputHandler);



    }
}
