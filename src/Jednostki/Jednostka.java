package Jednostki;

import javafx.geometry.Point2D;

import java.util.ArrayList;

/**
 * Created by MartaPawlik on 06.03.2016.
 */
public class Jednostka {

    private ArrayList<Point2D> koordynaty = new ArrayList<>();
    protected double zdrowie;
    int maksymalnyRzad;
    protected int minimalnyRzad;
    public double zwrocDlugoscJednostki(){
        return zdrowie;
    }

    public int getmaksymalnyRzadJednostki(){
        return maksymalnyRzad;
    }
    public int getminimalnyRzadJednostki(){
        return minimalnyRzad;
    }

    public void zapiszUstawieniaJednostek(int kolumna, int rzad) {
        koordynaty.add(new Point2D(rzad,kolumna));
    }

    public ArrayList<Point2D> zwrocKoordynaty(){
        return koordynaty;
    }


    public boolean czyZniszczony() {
        return false;
    }

    public void zmniejszZdrowie() {
    }

    public int getHoryzontalnePrzesuniecieSamolotu(){
        return 0;
    }

    public int getWertykalnePrzesuniecieSamolotu(){
        return 0;
    }

    public void obrocSamolot() {
    }
}
