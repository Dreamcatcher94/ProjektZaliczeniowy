package Jednostki;


/**
 * Created by MartaPawlik on 06.03.2016.
 */
public class Samolot extends Jednostka {


    private int horyzontalnePrzesuniecieSamolotu;
    private int wertykalnePrzesuniecieSamolotu;
    private PolozenieSamolotu polozenieSamolotu;

    public Samolot(PolozenieSamolotu polozenieSamolotu){
        this.polozenieSamolotu = polozenieSamolotu;
        horyzontalnePrzesuniecieSamolotu = 0;
        wertykalnePrzesuniecieSamolotu = 0;
        maksymalnyRzad = 14;
        minimalnyRzad = 1;
        zdrowie = 3;
        obrocSamolot();
    }

    @Override
    public double zwrocDlugoscJednostki(){
        return zdrowie;
    }

    public void zmniejszZdrowie() {
        this.zdrowie -= 0.5;
    }

    public boolean czyZniszczony() {
        return zdrowie == 0;
    }

    public int getHoryzontalnePrzesuniecieSamolotu(){
        return horyzontalnePrzesuniecieSamolotu;
    }

    public int getWertykalnePrzesuniecieSamolotu(){
        return wertykalnePrzesuniecieSamolotu;
    }

    public void obrocSamolot(){
        switch (polozenieSamolotu){
            case POLUDNIE:{
                this.horyzontalnePrzesuniecieSamolotu = -1;
                this.wertykalnePrzesuniecieSamolotu = -1;
                polozenieSamolotu = PolozenieSamolotu.ZACHOD;
                break;
            }
            case ZACHOD:{
                this.horyzontalnePrzesuniecieSamolotu = 0;
                this.wertykalnePrzesuniecieSamolotu = -2;
                polozenieSamolotu = PolozenieSamolotu.POLNOC;
                break;

            }
            case POLNOC:{
                this.horyzontalnePrzesuniecieSamolotu = 1;
                this.wertykalnePrzesuniecieSamolotu = -1;
                polozenieSamolotu = PolozenieSamolotu.WSCHOD;
                break;
            }
            case WSCHOD:{
                this.horyzontalnePrzesuniecieSamolotu = 0;
                this.wertykalnePrzesuniecieSamolotu = 0;
                polozenieSamolotu = PolozenieSamolotu.POLUDNIE;
                break;
            }
        }
    }



}
