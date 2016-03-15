package Plansze;


import Jednostki.Jednostka;
import Jednostki.JednostkaNaziemna;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.*;

/**
 * Created by MartaPawlik on 06.03.2016.
 */
public class Plansza {

    protected int liczbaStrzalow = 0;
    protected int pozostaleJednostki = 19;
    protected Random random;
    public ListIterator<Jednostka> jednostkiIterator;
    protected int umiejscowieniePrzyciskuKolumna;
    protected int umiejscowieniePrzyciskuWiersz;
    protected Node wezel;
    protected boolean horyzontalneUstawienie;
    protected static Jednostka jednostka;
    protected static ArrayList<Jednostka> jednostki;
    protected char[][] tablicaJednostek = new char[14][22];


    protected boolean getHoryzontalneUstawienie() {
        return horyzontalneUstawienie;
    }

    public void ustawPodglad(GridPane board, Button button, String colour) {
    }

    public char getWynikTablicyJednostek(int row, int column){
        return tablicaJednostek[row][column];
    }

    public int getLiczbaStrzalow(){return liczbaStrzalow;}

    public int getLiczbaPozostalychJednostek() {return pozostaleJednostki;}

    public void odswierzPlansze(GridPane board){
        for(int i =1; i < 8; i++)
            for(int j =1; j< 23; j++)
                if(!zwrocWezelzPlanszy(board,j,i).isDisable())
                    zwrocWezelzPlanszy(board,j,i).setStyle( "-fx-base: #A16946;");
        for(int i =8; i < 15; i++)
            for(int j =1; j< 23; j++)
                if(!zwrocWezelzPlanszy(board,j,i).isDisable())
                    zwrocWezelzPlanszy(board,j,i).setStyle( "-fx-base: #86c1b9;");
    }



    protected boolean checkForNeighbours(GridPane plansza, int kolumna, int wierszPozycjiPrzycisku) {
        if(jednostka instanceof JednostkaNaziemna) {
            if (horyzontalneUstawienie)
                return sprawdzKolizjeDlaHoryzontalnegoUstawienia(plansza, kolumna, wierszPozycjiPrzycisku);
            else
                return sprawdzKolizjedlaWertykalnegoUstawienia(plansza, kolumna, wierszPozycjiPrzycisku);
        }
        else
            return sprawdzKolizjeDlaHoryzontalnegoUstawienia(plansza, kolumna, wierszPozycjiPrzycisku) && sprawdzKolizjedlaWertykalnegoUstawienia(plansza, kolumna, wierszPozycjiPrzycisku);
    }

    protected boolean sprawdzKolizjedlaWertykalnegoUstawienia(GridPane gridPane, int col, int buttonRow) {
        for (int j = buttonRow - 1; j < (buttonRow + jednostka.zwrocDlugoscJednostki()) + 1; j++) {
            if (j == 0 || j >= 15)
                continue;
            for (int i = col - 1; i < col + 2; i++) {
                if (i == 0 || i >= 23)
                    continue;
                if (zwrocWezelzPlanszy(gridPane, i, j).isDisable())
                    return false;
            }
        }
        return true;
    }

    protected boolean sprawdzCzyJestPozaSiatka(int col, int wierszPozycjiPrzycisku){
        if(getHoryzontalneUstawienie())
            if (col > (23 - jednostka.zwrocDlugoscJednostki()) || wierszPozycjiPrzycisku > (jednostka.getmaksymalnyRzadJednostki()) ||
                    wierszPozycjiPrzycisku < jednostka.getminimalnyRzadJednostki() || col < 1)
                return true;
        if(!getHoryzontalneUstawienie())
            if(wierszPozycjiPrzycisku > (jednostka.getmaksymalnyRzadJednostki() + 1 - jednostka.zwrocDlugoscJednostki()) || wierszPozycjiPrzycisku < jednostka.getminimalnyRzadJednostki())
                return true;
        return false;
    }



    protected boolean sprawdzKolizjeDlaHoryzontalnegoUstawienia(GridPane gridPane, int col, int buttonRow) {
        for (int j = buttonRow - 1; j < buttonRow + 2; j++) {
            if (j == 0 || j == 15)
                continue;
            for (int i = col - 1; i < (col + jednostka.zwrocDlugoscJednostki()) + 1; i++) {
                if (i == 0 || i >= 23)
                    continue;
                if (zwrocWezelzPlanszy(gridPane, i, j).isDisable())
                    return false;
            }
        }
        return true;
    }

    protected Node zwrocWezelzPlanszy(GridPane gridPane, int kolumna, int buttonRow) {
        Node resultNode = null;
        for (Node node : gridPane.getChildren())
            if (GridPane.getColumnIndex(node) == kolumna && GridPane.getRowIndex(node) == buttonRow)
                resultNode = node;
        return resultNode;
    }

    public void ustawJednostke(GridPane board, String colour) {
            if (jednostka instanceof JednostkaNaziemna) {
                if (checkForNeighbours(board, umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz) && !sprawdzCzyJestPozaSiatka(umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz)) {
                    if (getHoryzontalneUstawienie())
                        ustawHoryzontalnieJednostkeLadowa(board, colour);
                    else
                        ustawWertykalnieJednostkeLadowa(board, colour);
                    if (jednostkiIterator.hasNext())
                        jednostka = jednostkiIterator.next();
                }
            } else
                ustawSamolot(board, colour);
            zapiszUstawieniaJdnostek(board);
    }

    protected void ustawSamolot(GridPane board, String colour) {
        horyzontalneUstawienie = true;

        if(!sprawdzCzyJestPozaSiatka(umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz)){
            horyzontalneUstawienie = false;
            if(!sprawdzCzyJestPozaSiatka(umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz)) {
                if ((sprawdzKolizjeDlaHoryzontalnegoUstawienia(board, umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz - jednostka.getWertykalnePrzesuniecieSamolotu())) &&
                        sprawdzKolizjedlaWertykalnegoUstawienia(board, umiejscowieniePrzyciskuKolumna - jednostka.getHoryzontalnePrzesuniecieSamolotu() + 1, umiejscowieniePrzyciskuWiersz)) {
                    umiejscowieniePrzyciskuKolumna++;
                    ustawWertykalnieJednostkeLadowa(board, colour);
                    umiejscowieniePrzyciskuKolumna--;
                    ustawHoryzontalnieJednostkeLadowa(board, colour);
                    if (jednostkiIterator.hasNext())
                        jednostka = jednostkiIterator.next();
                }
            }
        }
    }

    protected void ustawHoryzontalnieJednostkeLadowa(GridPane board, String colour) {
        if(jednostkiIterator.hasNext())
            for (int i = umiejscowieniePrzyciskuKolumna; i < umiejscowieniePrzyciskuKolumna + jednostka.zwrocDlugoscJednostki(); i++) {
                wezel = zwrocWezelzPlanszy(board, i, umiejscowieniePrzyciskuWiersz - jednostka.getWertykalnePrzesuniecieSamolotu());
                jednostka.zapiszUstawieniaJednostek(i, umiejscowieniePrzyciskuWiersz - jednostka.getWertykalnePrzesuniecieSamolotu());
                wezel.setStyle(colour);
                wezel.setDisable(true);
            }
    }

    protected void ustawWertykalnieJednostkeLadowa(GridPane board, String colour) {
        if(jednostkiIterator.hasNext())
            for (int i = umiejscowieniePrzyciskuWiersz; i < umiejscowieniePrzyciskuWiersz + jednostka.zwrocDlugoscJednostki(); i++) {
                wezel = zwrocWezelzPlanszy(board, umiejscowieniePrzyciskuKolumna - jednostka.getHoryzontalnePrzesuniecieSamolotu(), i);
                jednostka.zapiszUstawieniaJednostek(umiejscowieniePrzyciskuKolumna - jednostka.getHoryzontalnePrzesuniecieSamolotu(), i);
                wezel.setStyle(colour);
                wezel.setDisable(true);
            }
    }


    protected void przygotujJednostki() {
    }

    public void ustawJednostkiLosowo(GridPane plansza, String kolor) {
    }

    protected void zapiszUstawieniaJdnostek(GridPane plansza) {
        for(int i =1; i < 15; i++)
            for(int j =1; j< 23; j++)
                if(zwrocWezelzPlanszy(plansza,j,i).getStyle() == "-fx-text-fill: green" || zwrocWezelzPlanszy(plansza,j,i).isDisable())
                    tablicaJednostek[i-1][j-1] = 'X';
    }

    protected void pomalujZniszczonaJednostke(GridPane board, Jednostka jednostka){
        for (Point2D point : jednostka.zwrocKoordynaty())
            zwrocWezelzPlanszy(board, (int)point.getY(),(int) point.getX()).setStyle("-fx-base: #AB4642;");
    }

    public void setTuraGracza() {
    }
}
