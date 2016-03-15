package Jednostki;

/**
 * Created by MartaPawlik on 06.03.2016.
 */
public class Statek extends JednostkaNaziemna {

    public Statek(int unitLength){
        super(unitLength);
        maksymalnyRzad = 7;
        minimalnyRzad = 1;
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
