package Game;

import Enums.*;
import Game.Controllers.PlayerController;
import Game.Managers.GameManager;
import Game.Managers.InputHandler;
import Game.Managers.LevelManager;
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
        OrcMage tw = new OrcMage("Radagon");
        tw.equip(SlotType.MAIN_HAND,new PlayerWeapon("SimpleMace","A plain mace",
                List.of(new Damage(DamageType.BLUNT,new Dice(1,6),0)),
                List.of(new ItemEffect(EffectType.STR_BOOST,1)), SlotType.MAIN_HAND)
        );
        LevelManager levelManager = new LevelManager(tw);

        PlayerController playerController = new PlayerController(tw,gameMap.map[0][0],Direction.SOUTH);

        MiniMapPanel miniMapPanel = new MiniMapPanel(gameMap,gameMap.map[0][0],Direction.SOUTH);

        GameManager gameManager = new GameManager(playerController,miniMapPanel,levelManager,gameMap);

        InputHandler inputHandler = new InputHandler(gameManager);






        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());



        frame.add(miniMapPanel, BorderLayout.CENTER);
        frame.pack(); // sizes the frame to the preferred size of its components
        frame.setLocationRelativeTo(null); // centers the frame on screen
        frame.setVisible(true);
        frame.requestFocusInWindow();

        frame.addKeyListener(inputHandler);



    }
}
