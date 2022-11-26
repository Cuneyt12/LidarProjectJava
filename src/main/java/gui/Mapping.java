package gui;

import model.GetLidarInfo;
import service.RPLidarA1Driver;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Mapping extends JPanel implements ActionListener {
    private final int DELAY = 150;
    private boolean gridCreated = false;
    private Timer timer;
    RPLidarA1Driver lidar;

    public Mapping() {
        if (lidar == null) {
            lidar = new RPLidarA1Driver();
        }
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer() {
        return timer;
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.CYAN);
        g2d.fillRect(centerX, centerY, 2, 2);

        g2d.setColor(new Color(7, 143, 32, 163));
        g2d.drawRect(centerX-20, centerY-40, 40, 80);
        g2d.setColor(Color.white);
        g2d.fillOval(centerX, centerY, 3, 3);
//        g2d.drawString("Araç", centerX-11, centerY + 3);
        g2d.setColor(Color.gray);

        for (int i = 0; i < 390; i+=10){
            if (i < 150 || i > 230){
                g2d.drawLine(centerX - 180, centerY - 190 + i, centerX + 180, centerY - 190 + i); //üst-alt yatay çizgiler
            }
            if ((i >= 0 && i <= 150) || (i > 200 && i < 370)){
                g2d.drawLine(centerX - 180 + i, centerY - 190, centerX - 180 + i, centerY + 190);//sol sağ dikey çizgiler
            }
            if ((i > 150 && i <= 200) ){
                g2d.drawLine(centerX - 180 + i, centerY - 190, centerX - 180 + i, centerY - 50);//orta üst dikey çizgiler
                g2d.drawLine(centerX - 180 + i, centerY + 50, centerX - 180 + i, centerY + 190);//orta alt dikey çizgiler
            }
            if (i >= 140 && i <= 240){
                g2d.drawLine(centerX - 180, centerY - 190 + i, centerX - 30, centerY - 190 + i); //orta sol yatay çizgiler
                g2d.drawLine(centerX + 30, centerY - 190 + i, centerX + 180, centerY - 190 + i); //orta sağ yatay çizgiler
            }
        }

        lidar.readData();

        if (lidar.getDistanceSize() > 0) {
            for (int i = 0; i < lidar.getAngle().size(); i++) {
                int x = (int) ((lidar.getDistance().get(i)) * Math.cos(Math.toRadians(lidar.getAngle().get(i))));
                int y = (int) ((lidar.getDistance().get(i)) * Math.sin(Math.toRadians(lidar.getAngle().get(i))));

                if (lidar.getDistance().get(i) <= 10.)
                    g2d.setColor(Color.RED);
                else
                    g2d.setColor(Color.ORANGE);

                g2d.fillOval(centerX + y, centerY - 4 - x, 4, 4);

//                if (lidar.getAngle().get(i) > 0 && lidar.getAngle().get(i) <= 90){
//                    g2d.fillOval(centerX + y + 20, centerY  - 4 - x - 40, 4, 4);
//                }
//                if (lidar.getAngle().get(i) > 90 && lidar.getAngle().get(i) <= 135){
//                    g2d.fillOval(centerX + y + 20, centerY - 4 - x + 40, 4, 4);
//                }
//
//                if (lidar.getAngle().get(i) > 135 && lidar.getAngle().get(i) < 225){
//                    g2d.fillOval(centerX + y - 20, centerY - 4 - x + 40, 4, 4);
//                }
//
//                if (lidar.getAngle().get(i) >= 225 && lidar.getAngle().get(i) < 270){
//                    g2d.fillOval(centerX + y - 20, centerY - 4 - x - 40, 4, 4);
//                }
//                if (lidar.getAngle().get(i) > 270 && lidar.getAngle().get(i) < 360){
//                    g2d.fillOval(centerX + y - 20, centerY - 4 - x - 40, 4, 4);
//                }
//                if (lidar.getAngle().get(i) == 0 || lidar.getAngle().get(i) == 360){
//                    g2d.fillOval(centerX + y , centerY - 4 - x - 40, 4, 4);
//                }
//                if (lidar.getAngle().get(i) == 90){
//                    g2d.fillOval(centerX + y + 20, centerY - 4 - x, 4, 4);
//                }
//                if (lidar.getAngle().get(i) >= 179 && lidar.getAngle().get(i) <= 180){
//                    g2d.fillOval(centerX + y, centerY - 4 - x + 40, 4, 4);
//                }
//                if (lidar.getAngle().get(i) == 270){
//                    g2d.fillOval(centerX + y - 20, centerY - 4 - x, 4, 4);
//                }
            }
        }
        lidar.clearLists();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
