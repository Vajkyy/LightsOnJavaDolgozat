package tests;

import java.io.IOException;
import models.LightsOnModel;

public class LightsOnModelTest {

    public static void main(String[] args) {
        LightsOnModelTest teszt = new LightsOnModelTest();
        teszt.tesztGetSzinIndexKivetel();
        teszt.tesztKapcsolIndexKivetel();
        teszt.tesztMentBetoltKivetel();
        System.out.println("LightsOnModel tesztek vége.");
    }

    public void tesztGetSzinIndexKivetel() {
        LightsOnModel model = new LightsOnModel();
        try {
            model.getSzin(-1);
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("getSzin negatív index teszt: " + ex.getMessage());
        }

        try {
            model.getSzin(9);
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("getSzin 9-es index teszt: " + ex.getMessage());
        }
    }

    public void tesztKapcsolIndexKivetel() {
        LightsOnModel model = new LightsOnModel();
        try {
            model.kapcsol(-1);          
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("kapcsol negatív index teszt: " + ex.getMessage());
        }

        try {
            model.kapcsol(9);
            
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("kapcsol 9-es index teszt: " + ex.getMessage());
        }
    }


    public void tesztMentBetoltKivetel() {
        LightsOnModel model = new LightsOnModel();
        try {
            model.ment(null);           
        } catch (IllegalArgumentException | IOException ex) {
            System.err.println("ment null fájl teszt: " + ex.getMessage());
        }

        try {
            model.betolt("nemletezo.txt");           
        } catch (IOException ex) {
            System.err.println("betolt nemlétező fájl teszt: " + ex.getMessage());
        }
    }
}
