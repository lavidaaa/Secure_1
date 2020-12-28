import java.math.BigInteger;

public class Encryption {

    public static BigInteger[] encrypt (BigInteger n, BigInteger e, String text){

        char[] mass = text.toCharArray();
        BigInteger[] enc_text = new BigInteger[mass.length];

        for (int i = 0; i < mass.length; i ++) {
            enc_text[i] = BigInteger.valueOf(mass[i]).modPow(e, n);
        }
        return enc_text;
    }
}
