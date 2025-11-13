package controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import models.LightsOnModel;
import view.LightsOnView;

public class LightsOnController {

    private LightsOnView nNezet;
    private LightsOnModel nModel;
    private JButton[] nGombok = new JButton[9];
    private int nFelkapcsSzam;

    public LightsOnController(LightsOnView nNezet, LightsOnModel nModel) {
        if (nNezet == null) {
            throw new NullPointerException("A nézet (LightsOnView) nem lehet null.");
        }
        if (nModel == null) {
            throw new NullPointerException("A modell (LightsOnModel) nem lehet null.");
        }
        this.nNezet = nNezet;
        this.nModel = nModel;
        bellit();
    }

    public void bellit() {
        nGombok[0] = nNezet.getjBtnGame0();
        nGombok[1] = nNezet.getjBtnGame1();
        nGombok[2] = nNezet.getjBtnGame2();
        nGombok[3] = nNezet.getjBtnGame3();
        nGombok[4] = nNezet.getjBtnGame4();
        nGombok[5] = nNezet.getjBtnGame5();
        nGombok[6] = nNezet.getjBtnGame6();
        nGombok[7] = nNezet.getjBtnGame7();
        nGombok[8] = nNezet.getjBtnGame8();

        for (int i = 0; i < nGombok.length; i++) {
            if (nGombok[i] == null) {
                throw new NullPointerException("A(z) " + i + ". gomb nincs inicializálva.");
            }
            nGombok[i].setBackground(nModel.getSzin(i));
        }

        nFelkapcsSzam = kezdetiSzamolas();
        nNezet.getjTxtFelkapcsLampa().setText(String.valueOf(nFelkapcsSzam));

        for (int nIndex = 0; nIndex < nGombok.length; nIndex++) {
            int nPozicio = nIndex;
            nGombok[nIndex].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    kapcsol(nPozicio);
                    frissitFelkapcsSzam();
                    ellenorizGyozelem();
                }
            });
        }

        nNezet.getjBtnUjra().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ujraindit();
            }
        });

        nNezet.getjMnuItemKilepes().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kilepesMegerosites();
            }
        });

        nNezet.getjMnuItemJatekSzabaly().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "A cél: minden lámpát lekapcsolni (sárgára váltani).\n"
                        + "Egy gombra kattintva az a lámpa és a szomszédosak színe megváltozik.\n"
                        + "Felkapcsolt: zöld\nKikapcsolt: sárga");
            }
        });

        nNezet.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                kilepesMegerosites();
            }
        });

        nNezet.getjBtnMentes().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    nModel.ment("lightsOnMentes.txt");
                    JOptionPane.showMessageDialog(null, "Játékállás mentve!");
                } catch (IOException ex) {
                    throw new RuntimeException("Hiba a mentés során: " + ex.getMessage());
                }
            }
        });

        nNezet.getjBtnBetoltes().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    nModel.betolt("lightsOnMentes.txt");
                    for (int i = 0; i < nGombok.length; i++) {
                        nGombok[i].setBackground(nModel.getSzin(i));
                    }
                    frissitFelkapcsSzam();
                    JOptionPane.showMessageDialog(null, "Játékállás betöltve!");
                    ellenorizGyozelem();
                } catch (IOException ex) {
                    throw new RuntimeException("Hiba a betöltés során: " + ex.getMessage());
                }
            }
        });
    }

    private void kapcsol(int nPozicio) {
        if (nPozicio < 0 || nPozicio >= nGombok.length) {
            throw new IndexOutOfBoundsException("Hibás pozíció a kapcsol metódusban: " + nPozicio);
        }
        nModel.kapcsol(nPozicio);
        for (int i = 0; i < nGombok.length; i++) {
            nGombok[i].setBackground(nModel.getSzin(i));
        }
    }

    private void frissitFelkapcsSzam() {
        nFelkapcsSzam = 0;
        for (int i = 0; i < nGombok.length; i++) {
            if (nGombok[i] == null) {
                throw new NullPointerException("Null referencia a frissitFelkapcsSzam metódusban a(z) " + i + ". gombnál.");
            }
            if (nGombok[i].getBackground().equals(Color.GREEN)) {
                nFelkapcsSzam++;
            }
        }
        nNezet.getjTxtFelkapcsLampa().setText(String.valueOf(nFelkapcsSzam));
    }

    private int kezdetiSzamolas() {
        int nSzamlalo = 0;
        for (int i = 0; i < nGombok.length; i++) {
            if (nGombok[i] == null) {
                throw new NullPointerException("Null referencia a kezdetiSzamolas metódusban a(z) " + i + ". gombnál.");
            }
            if (nGombok[i].getBackground().equals(Color.GREEN)) {
                nSzamlalo++;
            }
        }
        return nSzamlalo;
    }

    private void ujraindit() {
        nModel.ujraindit();
        for (int i = 0; i < nGombok.length; i++) {
            nGombok[i].setBackground(nModel.getSzin(i));
        }
        frissitFelkapcsSzam();
    }

    private void ellenorizGyozelem() {
        if (nModel.mindenZold()) {
            JOptionPane.showMessageDialog(null, "Gratulálok! Minden lámpa felkapcsolva!");
        }
    }

    private void kilepesMegerosites() {
        int valasz = JOptionPane.showConfirmDialog(
                nNezet,
                "Biztosan ki szeretnél lépni a programból?",
                "Kilépés megerősítése",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (valasz == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
