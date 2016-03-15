package Jednostki;

import java.security.SecureRandom;

/**
 * Created by MartaPawlik on 06.03.2016.
 */
public class FabrykaJednostek {
    public static Jednostka stworzJednostke(String typJednostki, int rozmiar) {
        switch (typJednostki) {
            case "Ship":
                return new Statek(rozmiar);
            case "Tank":
                return new Czolg(rozmiar);
        }
        return null;
    }

    public static Jednostka stworzJednostke(String type) {
        switch (type) {
            case "Plane":
                return new Samolot(randomEnum(PolozenieSamolotu.class));
        }
        return null;
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> PolozenieSamolotu){
        final SecureRandom random = new SecureRandom();
        int x = random.nextInt(PolozenieSamolotu.getEnumConstants().length);
        return PolozenieSamolotu.getEnumConstants()[x];
    }

}
