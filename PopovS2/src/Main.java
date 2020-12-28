import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        double g, p, a, b, A, B, S1, S2;

        Random random = new Random();
        p = BigInteger.probablePrime(16, random).doubleValue();
        g = getPRoot(p);

        System.out.println("Алиса и Боб выбрали и обменялись числами p = " + p + " и g = " + g);
        System.out.println("Ева подсидела и перехватила p и g " );

        a  = getRand(0, 1000);
        b  = getRand(0, 1000);
        System.out.println("Боб выбирает секретное число: " + a);
        System.out.println("Алиса выбирает секретное число: " + b + "\n");

        A = BigInteger.valueOf((long)g).modPow(BigInteger.valueOf((int)a), BigInteger.valueOf((long)p)).doubleValue();
        B = BigInteger.valueOf((long)g).modPow(BigInteger.valueOf((int)b), BigInteger.valueOf((long)p)).doubleValue();
        System.out.println("Боб вычисляет свой открытый ключ : " + A);
        System.out.println("Алиса вычисляет свой открытый ключ : " + B);

        System.out.println("происходит обмен ключами между Алисой и Бобом.\n Ева перехватывает открытые ключи Алисы и Боба");

        System.out.println("\nЕва перехватила открытые ключи Алисы: " + B + " и Боба: " + A + "\n Ева начала взламывать клюи ");


        S1 = BigInteger.valueOf((long)A).modPow(BigInteger.valueOf((int)b), BigInteger.valueOf((long)p)).doubleValue();
        S2 = BigInteger.valueOf((long)B).modPow(BigInteger.valueOf((int)a), BigInteger.valueOf((long)p)).doubleValue();
        System.out.println("\nБоб вычисляет свой открытый ключ : " + S1);
        System.out.println("Алиса вычисляет свой открытый ключ : " + S2 + "\n");

        System.out.println("Ева все еще взламывает код" );
    }


    private static int getRand( int min, int max){
        Random random = new Random();
        return min + random.nextInt(max - min);
    }

    public static double getPRoot(double p) {
        for (long i = 0; i < p; i++)
            if (IsPRoot(p, i))
                return i;
        return 0;
    }

    public static boolean IsPRoot(double p, double a) {
        if (a == 0 || a == 1)
            return false;
        double last = 1;

        Set<Double> set = new HashSet<>();
        for (double i = 0; i < p - 1; i++) {
            last = (last * a) % p;
            if (set.contains(last)) // Если повтор
                return false;
            set.add(last);
        }
        return true;
    }
}
