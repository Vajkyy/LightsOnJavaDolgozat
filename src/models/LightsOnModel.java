package models;

import java.awt.Color;

public class LightsOnModel {

    private Color[] nAllapotok;

    public LightsOnModel() {
        nAllapotok = new Color[9];
        ujraindit();
    }

    public Color getSzin(int nIndex) {
        if (nIndex < 0 || nIndex >= nAllapotok.length) {
            throw new IndexOutOfBoundsException("Hibás index a getSzin metódusban: " + nIndex);
        }
        return nAllapotok[nIndex];
    }

    public void kapcsol(int nPozicio) {
           if (nPozicio < 0 || nPozicio >= 9) {
            throw new IndexOutOfBoundsException("Hibás pozíció a kapcsol metódusban: " + nPozicio);
        }
        int[][] nSzomszedok = {
            {0,1,3}, {1,0,2,4}, {2,1,5},
            {3,0,4,6}, {4,1,3,5,7}, {5,2,4,8},
            {6,3,7}, {7,4,6,8}, {8,5,7}
        };
        int[] nLista = nSzomszedok[nPozicio];
        for (int i = 0; i < nLista.length; i++) {
            valtoztat(nLista[i]);
        }
    }

    private void valtoztat(int nIndex) {
        if (nAllapotok[nIndex].equals(Color.YELLOW)) {
            nAllapotok[nIndex] = Color.GREEN;
        } else {
            nAllapotok[nIndex] = Color.YELLOW;
        }
    }

    public boolean mindenSarga() {
        for (int i = 0; i < nAllapotok.length; i++) {
            if (nAllapotok[i] == null) {
                throw new IllegalStateException("A mező színe null a mindenSarga metódusban: " + i);
            }
            if (!nAllapotok[i].equals(Color.YELLOW)) {
                return false;
            }
        }
        return true;
    }

    public void ujraindit() {
        for (int i = 0; i < nAllapotok.length; i++) {
            if (Math.random() < 0.5) {
                nAllapotok[i] = Color.GREEN;
            } else {
                nAllapotok[i] = Color.YELLOW;
            }
        }
    }
}
