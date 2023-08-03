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
    public static int mapCounter = 1;
    public final static int NUMBER_OF_MAPS = 5;
    public static JTextArea logText;

    public static void main(String[] args){

        //Creating the Player
        OrcMage Radagon = new OrcMage("Radagon");

        GameManager gameManager = new GameManager(Radagon);
        //Panels
        MapPanel mapPanel = gameManager.getMapPanel() ;
        MiniMapPanel miniMapPanel = gameManager.getMiniMapPanel();
        PlayerStatus playerStatus = gameManager.getPlayerStatus();

        InputHandler inputHandler = new InputHandler(gameManager);

        logText = new JTextArea(10,20);
        logText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //Frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(miniMapPanel, BorderLayout.WEST);
        frame.add(mapPanel,BorderLayout.CENTER);
        frame.add(playerStatus,BorderLayout.EAST);
        frame.add(scrollPane, BorderLayout.SOUTH);

        frame.pack(); // sizes the frame to the preferred size of its components
        frame.setLocationRelativeTo(null); // centers the frame on screen
        frame.setVisible(true);
        frame.requestFocusInWindow();

        frame.addKeyListener(inputHandler);



    }
    public static boolean isFinalRound(){
        return mapCounter == NUMBER_OF_MAPS;
    }
    public static void log(String message) {
        logText.append(message + "\n");
        logText.setCaretPosition(logText.getDocument().getLength());
    }

}
