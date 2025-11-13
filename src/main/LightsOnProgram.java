package main;

import javax.swing.SwingUtilities;
import controllers.LightsOnController;
import models.LightsOnModel;
import view.LightsOnView;

public class LightsOnProgram {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LightsOnModel model = new LightsOnModel();
                LightsOnView nNezet = new LightsOnView();
                new LightsOnController(nNezet, model);
                nNezet.setVisible(true);
            }
        });
    }
}
