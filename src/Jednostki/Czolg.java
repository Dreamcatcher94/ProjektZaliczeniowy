package Jednostki;

/**
 * Created by MartaPawlik on 06.03.2016.
 */
public class Czolg extends JednostkaNaziemna {

    public Czolg(int dlugoscJednostki){
        super(dlugoscJednostki);
            maksymalnyRzad = 14;
            minimalnyRzad = 8;
    }
    public double zwrocDlugoscJednostki(){
        return Zdrowie;
    }

    public void zmniejszZdrowie() {
        Zdrowie--;
    }

    public boolean czyZniszczony() {
        return Zdrowie == 0;
    }
}
