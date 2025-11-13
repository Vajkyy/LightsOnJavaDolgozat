package tests;

import controllers.LightsOnController;
import models.LightsOnModel;
import view.LightsOnView;

public class LightsOnControllerTest {
    public static void main(String[] args) {
         LightsOnControllerTest teszt = new LightsOnControllerTest();
        teszt.tesztNullNezet();
        teszt.tesztNullModel();
        System.out.println("LightsOnController tesztek vége.");
    }
        public void tesztNullNezet() {
        LightsOnModel model = new LightsOnModel();
        try {
            new LightsOnController(null, model);
        } catch (NullPointerException ex) {
            System.err.println("null nézet teszt: " + ex.getMessage());
        }
    }
            public void tesztNullModel() {
        try {
            new LightsOnController(new LightsOnView(), null);           
        } catch (NullPointerException ex) {
            System.err.println("null modell teszt: " + ex.getMessage());
        }
    }
}
