package Plansze;

import Jednostki.FabrykaJednostek;
import Jednostki.Jednostka;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by MartaPawlik on 06.03.2016.
 */
public class PlanszaPrzeciwnika extends Plansza {

    public void ustawJednostkiLosowo(GridPane plansza, String kolor) {

        random = new Random();
        while(jednostkiIterator.hasNext()){
            umiejscowieniePrzyciskuKolumna = random.nextInt(22) + 1;
            umiejscowieniePrzyciskuWiersz = random.nextInt(14) + 1;
            horyzontalneUstawienie = random.nextBoolean();
            ustawJednostke(plansza, kolor);
        }

        zapiszUstawieniaJdnostek(plansza);
        ukryjJednostki(plansza);
        odswierzPlansze(plansza);

    }
    public void shootUnit(GridPane plansza, Plansza gracz){

            umiejscowieniePrzyciskuKolumna = random.nextInt(22) + 1;
            umiejscowieniePrzyciskuWiersz = random.nextInt(14) + 1;

            if(gracz.getWynikTablicyJednostek(umiejscowieniePrzyciskuWiersz -1, umiejscowieniePrzyciskuKolumna -1) == 'O')
                this.shootUnit(plansza, gracz);

            else if(gracz.getWynikTablicyJednostek(umiejscowieniePrzyciskuWiersz -1, umiejscowieniePrzyciskuKolumna -1) == 'X'){
                gracz.tablicaJednostek[umiejscowieniePrzyciskuWiersz -1][umiejscowieniePrzyciskuKolumna -1] = 'O';
                zwrocWezelzPlanszy(plansza, umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz).setDisable(false);
                zwrocWezelzPlanszy(plansza, umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz).setStyle("-fx-base: #F7CA88;");
                for (Jednostka jednostka : gracz.jednostki)
                    for (Point2D point : jednostka.zwrocKoordynaty())
                        if(point.equals(new Point2D(umiejscowieniePrzyciskuWiersz, umiejscowieniePrzyciskuKolumna)) && jednostki.indexOf(jednostka) > 19){
                            jednostka.zmniejszZdrowie();
                            if (jednostka.czyZniszczony()) {
                                pomalujZniszczonaJednostke(plansza, jednostka);
                                pozostaleJednostki--;
                            }
                        }
                liczbaStrzalow++;
                this.shootUnit(plansza, gracz);
            }
            else{
                gracz.tablicaJednostek[umiejscowieniePrzyciskuWiersz -1][umiejscowieniePrzyciskuKolumna -1] = 'O';
                zwrocWezelzPlanszy(plansza, umiejscowieniePrzyciskuKolumna, umiejscowieniePrzyciskuWiersz).setStyle("-fx-base: #585858;");
                gracz.setTuraGracza();
                liczbaStrzalow++;
            }

    }


    private void ukryjJednostki(GridPane board){
        for (Node node : board.getChildren())
            node.setDisable(false);
    }

    public void przygotujJednostki() {
        for(int i =0; i < 14; i++)
            for (int j = 0; j < 22; j++)
                tablicaJednostek[i][j] = '-';

        jednostki = new ArrayList<>();

        jednostki.add(FabrykaJednostek.stworzJednostke("Tank",4));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank",4));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank",3));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank",3));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank",2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank",2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Tank",2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",4));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",3));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",3));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",2));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",1));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",1));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",1));
        jednostki.add(FabrykaJednostek.stworzJednostke("Ship",1));
        jednostki.add(FabrykaJednostek.stworzJednostke("Plane"));
        jednostki.add(FabrykaJednostek.stworzJednostke("Plane"));
        jednostki.add(FabrykaJednostek.stworzJednostke("Plane"));

        jednostkiIterator = jednostki.listIterator();
        jednostka = jednostkiIterator.next();
    }

}
