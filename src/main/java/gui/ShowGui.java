package gui;

import service.RPLidarA1Driver;

import java.awt.*;
import javax.swing.JFrame;

public class ShowGui extends JFrame {
    final int windowSizeX = 1000;
    final int windowSizeY = 700;

    public ShowGui(){
        init();
    }

    public void init() {

        add(new Mapping());
        setTitle("Lidar Mapping");
        setSize(windowSizeX, windowSizeY);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
