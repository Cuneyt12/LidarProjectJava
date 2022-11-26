import gui.ShowGui;

import java.awt.*;

public class Run {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                ShowGui showGui = new ShowGui();
                showGui.setVisible(true);
            }
        });
    }
}
