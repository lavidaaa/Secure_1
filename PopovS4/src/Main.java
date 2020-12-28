import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String [] args){
        BigInteger p, q;//secret key
        BigInteger n, e;//open key
        BigInteger fi, d;

        Random random = new Random();

        p = BigInteger.probablePrime(1024, random);
        q = BigInteger.probablePrime(1024, random);

        n = p.multiply(q);
        System.out.println("n = " + n);
        fi = f(p, q);
        e = findE(fi);
        System.out.println("e = " + e);

        d = e.modInverse(fi);
        System.out.println("d = " + d);


        String text = "Ааа ббб ввв";
        BigInteger[] decription_text = Encryption.encrypt(n, e, text);

        for (BigInteger e_char : decription_text) {
            System.out.println(e_char);
        }

        String finish = Decryption.decrypt(n, d, decription_text);

        System.out.println(finish);

    }
    private static BigInteger f(BigInteger p, BigInteger q){
        BigInteger a = p.subtract(BigInteger.ONE);
        BigInteger b = q.subtract(BigInteger.ONE);
        return a.multiply(b);
    }

    private static BigInteger findE(BigInteger f){
        BigInteger e = BigInteger.TWO;
        while((e.compareTo(f) == -1) & (e.gcd(f).compareTo(BigInteger.ONE) != 0)){
            e = e.add(BigInteger.ONE);
        }

        return e;
    }
}
