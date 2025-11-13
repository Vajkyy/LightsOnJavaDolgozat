package models;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LightsOnModel {

    private Color[] nAllapotok;

    public LightsOnModel() {
        nAllapotok = new Color[9];
        ujraindit();
    }

    public Color getSzin(int nIndex) {
        if (nAllapotok == null) {
            throw new NullPointerException("A nAllapotok tömb null a getSzin metódusban.");
        }
        if (nIndex < 0 || nIndex >= nAllapotok.length) {
            throw new IndexOutOfBoundsException("Hibás index a getSzin metódusban: " + nIndex);
        }
        if (nAllapotok[nIndex] == null) {
            throw new NullPointerException("A mező színe null a getSzin metódusban: " + nIndex);
        }
        return nAllapotok[nIndex];
    }

    public void kapcsol(int nPozicio) {
        if (nPozicio < 0 || nPozicio >= nAllapotok.length) {
            throw new IndexOutOfBoundsException("Hibás pozíció a kapcsol metódusban: " + nPozicio);
        }
        int[][] nSzomszedok = {
            {0, 1, 3}, {1, 0, 2, 4}, {2, 1, 5},
            {3, 0, 4, 6}, {4, 1, 3, 5, 7}, {5, 2, 4, 8},
            {6, 3, 7}, {7, 4, 6, 8}, {8, 5, 7}
        };
        int[] nLista = nSzomszedok[nPozicio];
        for (int i = 0; i < nLista.length; i++) {
            valtoztat(nLista[i]);
        }
    }

    private void valtoztat(int nIndex) {
        if (nIndex < 0 || nIndex >= nAllapotok.length) {
            throw new IndexOutOfBoundsException("Hibás index a valtoztat metódusban: " + nIndex);
        }
        if (nAllapotok[nIndex] == null) {
            throw new NullPointerException("A mező színe null a valtoztat metódusban: " + nIndex);
        }
        if (nAllapotok[nIndex].equals(Color.YELLOW)) {
            nAllapotok[nIndex] = Color.GREEN;
        } else {
            nAllapotok[nIndex] = Color.YELLOW;
        }
    }

    public boolean mindenZold() {
        if (nAllapotok == null) {
            throw new NullPointerException("A nAllapotok tömb null a mindenZold metódusban.");
        }
        for (int i = 0; i < nAllapotok.length; i++) {
            if (nAllapotok[i] == null) {
                throw new NullPointerException("A mező színe null a mindenZold metódusban: " + i);
            }
            if (!nAllapotok[i].equals(Color.GREEN)) {
                return false;
            }
        }
        return true;
    }

    public void ujraindit() {
        if (nAllapotok == null) {
            throw new NullPointerException("A nAllapotok tömb null az ujraindit metódusban.");
        }
        for (int i = 0; i < nAllapotok.length; i++) {
            nAllapotok[i] = Math.random() < 0.5 ? Color.GREEN : Color.YELLOW;
        }
    }

    public void ment(String fajlNev) throws IOException {
        if (fajlNev == null || fajlNev.isEmpty()) {
            throw new IllegalArgumentException("A fájlnév null vagy üres a ment metódusban.");
        }
        if (nAllapotok == null) {
            throw new NullPointerException("A nAllapotok tömb null a ment metódusban.");
        }

        Path utvonal = Path.of(fajlNev);
        StringBuilder tartalom = new StringBuilder();

        for (int i = 0; i < nAllapotok.length; i++) {
            if (nAllapotok[i] == null) {
                throw new NullPointerException("A mező színe null a ment metódusban: " + i);
            }
            tartalom.append(nAllapotok[i].equals(Color.GREEN) ? "GREEN" : "YELLOW");
            if (i < nAllapotok.length - 1) {
                tartalom.append(System.lineSeparator());
            }
        }

        Files.writeString(utvonal, tartalom.toString());
    }

    public void betolt(String fajlNev) throws IOException {
        if (fajlNev == null || fajlNev.isEmpty()) {
            throw new IllegalArgumentException("A fájlnév null vagy üres a betolt metódusban.");
        }
        Path utvonal = Path.of(fajlNev);
        if (!Files.exists(utvonal)) {
            throw new IOException("A fájl nem létezik: " + fajlNev);
        }

        List<String> sorok = Files.readAllLines(utvonal);
        for (int i = 0; i < nAllapotok.length && i < sorok.size(); i++) {
            String sor = sorok.get(i).trim();
            if (!sor.equalsIgnoreCase("GREEN") && !sor.equalsIgnoreCase("YELLOW")) {
                throw new IllegalArgumentException("Érvénytelen állapot a fájlban: " + sor);
            }
            nAllapotok[i] = sor.equalsIgnoreCase("GREEN") ? Color.GREEN : Color.YELLOW;
        }
    }
}
