package Plansze;

import Jednostki.FabrykaJednostek;
import Jednostki.Jednostka;
import Jednostki.JednostkaNaziemna;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.util.Random;

/**
 * Created by MartaPawlik on 06.03.2016.
 */
public class PlanszaGracza extends Plansza {


    private boolean czyTuraGracza = true;

    public boolean getCzyTuraGracza() { return czyTuraGracza;}
    public void setTuraGracza() {
        czyTuraGracza = !czyTuraGracza;}

    public void setHorizontalOrientation() {
        if (jednostka instanceof JednostkaNaziemna)
            horyzontalneUstawienie = !horyzontalneUstawienie;
        else
            jednostka.obrocSamolot();
    }

    public void ustawJednostkiLosowo(GridPane plansza, String kolor) {

        random = new Random();
        while(jednostkiIterator.hasNext()){
            umiejscowieniePrzyciskuKolumna = random.nextInt(22) + 1;
            umiejscowieniePrzyciskuWiersz = random.nextInt(14) + 1;
            horyzontalneUstawienie = random.nextBoolean();
            ustawJednostke(plansza, kolor);
        }
        zapiszUstawieniaJdnostek(plansza);
        odswierzPlansze(plansza);
    }

    public void ustawPodglad(GridPane plansza, Button przycisk, String kolor) {
        if (jednostkiIterator.hasNext()) {
            umiejscowieniePrzyciskuKolumna = plansza.getColumnIndex(przycisk);
            umiejscowieniePrzyciskuWiersz = plansza.getRowIndex(przycisk);

            if (jednostka instanceof JednostkaNaziemna) {
                if (!sprawdzCzyJestPozaSiatka(umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz) && checkForNeighbours(plansza, umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz))
                    if (getHoryzontalneUstawienie())
                        pokolorujHoryzontalnaJednostke(plansza, kolor);
                    else
                        pokolorujWertykalnaJednostke(plansza, kolor);
            } else
                ustawPodgladUstawieniaSamolotu(plansza, kolor);
        }
    }

    public void zestrzelJednostke(GridPane plansza, Button button, Plansza przeciwnik) {
        umiejscowieniePrzyciskuKolumna = plansza.getColumnIndex(button);
        umiejscowieniePrzyciskuWiersz = plansza.getRowIndex(button);
        if (przeciwnik.getWynikTablicyJednostek(umiejscowieniePrzyciskuWiersz - 1, umiejscowieniePrzyciskuKolumna - 1) == 'O')
            return;

        else if (przeciwnik.getWynikTablicyJednostek(umiejscowieniePrzyciskuWiersz - 1, umiejscowieniePrzyciskuKolumna - 1) == 'X') {
            liczbaStrzalow++;
            przeciwnik.tablicaJednostek[umiejscowieniePrzyciskuWiersz - 1][umiejscowieniePrzyciskuKolumna - 1] = 'O';
            zwrocWezelzPlanszy(plansza, umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz).setStyle("-fx-base: #F7CA88;");
            for (Jednostka jednostka : przeciwnik.jednostki) {
                for (Point2D point : jednostka.zwrocKoordynaty())
                    if (point.equals(new Point2D(umiejscowieniePrzyciskuWiersz, umiejscowieniePrzyciskuKolumna)) && jednostki.indexOf(jednostka) <19) {
                        jednostka.zmniejszZdrowie();
                        if (jednostka.czyZniszczony()) {
                            pomalujZniszczonaJednostke(plansza, jednostka);
                            pozostaleJednostki--;
                        }
                    }
            }
        }
        else {
            zwrocWezelzPlanszy(plansza, umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz).setStyle("-fx-base: #585858;");
            przeciwnik.tablicaJednostek[umiejscowieniePrzyciskuWiersz - 1][umiejscowieniePrzyciskuKolumna - 1] = 'O';
            setTuraGracza();
            liczbaStrzalow++;
        }

    }

    private void ustawPodgladUstawieniaSamolotu(GridPane plansza, String kolor) {
        horyzontalneUstawienie = true;
        umiejscowieniePrzyciskuKolumna = umiejscowieniePrzyciskuKolumna + jednostka.getHoryzontalnePrzesuniecieSamolotu();
        umiejscowieniePrzyciskuWiersz = umiejscowieniePrzyciskuWiersz + jednostka.getWertykalnePrzesuniecieSamolotu();
        if (!sprawdzCzyJestPozaSiatka(umiejscowieniePrzyciskuKolumna - 1, umiejscowieniePrzyciskuWiersz - jednostka.getWertykalnePrzesuniecieSamolotu())) {
            horyzontalneUstawienie = false;
            if (!sprawdzCzyJestPozaSiatka(umiejscowieniePrzyciskuKolumna - jednostka.getHoryzontalnePrzesuniecieSamolotu(), umiejscowieniePrzyciskuWiersz))
                if ((sprawdzKolizjeDlaHoryzontalnegoUstawienia(plansza, umiejscowieniePrzyciskuKolumna - 1, umiejscowieniePrzyciskuWiersz - jednostka.getWertykalnePrzesuniecieSamolotu())) &&
                        sprawdzKolizjedlaWertykalnegoUstawienia(plansza, umiejscowieniePrzyciskuKolumna - jednostka.getHoryzontalnePrzesuniecieSamolotu(), umiejscowieniePrzyciskuWiersz)) {
                    pokolorujWertykalnaJednostke(plansza, kolor);
                    umiejscowieniePrzyciskuKolumna--;
                    pokolorujHoryzontalnaJednostke(plansza, kolor);
                    horyzontalneUstawienie = true;
                }
        }
    }

    private void pokolorujHoryzontalnaJednostke(GridPane plansza, String kolor) {

        for (int i = umiejscowieniePrzyciskuKolumna; i < umiejscowieniePrzyciskuKolumna + jednostka.zwrocDlugoscJednostki(); i++) {
            wezel = zwrocWezelzPlanszy(plansza, i, umiejscowieniePrzyciskuWiersz - jednostka.getWertykalnePrzesuniecieSamolotu());
            if (!wezel.isDisable())
                wezel.setStyle(kolor);
        }
    }

    private void pokolorujWertykalnaJednostke(GridPane board, String kolor) {
        for (int i = umiejscowieniePrzyciskuWiersz; i < umiejscowieniePrzyciskuWiersz + jednostka.zwrocDlugoscJednostki(); i++) {
            wezel = zwrocWezelzPlanszy(board, umiejscowieniePrzyciskuKolumna - jednostka.getHoryzontalnePrzesuniecieSamolotu(), i);
            if (!wezel.isDisable())
                wezel.setStyle(kolor);
        }
    }



    public void przygotujJednostki() {
        for (int i = 0; i < 14; i++)
            for (int j = 0; j < 22; j++)
                tablicaJednostek[i][j] = '-';

        jednostki.add(FabrykaJednostek.stworzJednostke("Tank", 4));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank", 4));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank", 3));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank", 3));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank", 2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank", 2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank", 2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 4));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 3));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 3));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 1));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 1));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 1));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship", 1));
        jednostki.add(FabrykaJednostek.stworzJednostke("Plane"));
        jednostki.add(FabrykaJednostek.stworzJednostke("Plane"));
        jednostki.add(FabrykaJednostek.stworzJednostke("Plane"));

        jednostkiIterator = jednostki.listIterator();
        for(int i = 0; i < 20 ; i++)
            jednostkiIterator.next();

        jednostka = jednostkiIterator.next();
    }

}