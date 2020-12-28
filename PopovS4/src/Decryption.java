import java.math.BigInteger;

public class Decryption {
    public static String decrypt(BigInteger n, BigInteger d, BigInteger[] enc_text){
        String dec_text = "";

        for (BigInteger c : enc_text){
            dec_text += (char)(c.modPow(d, n)).intValue();
        }

        return dec_text;
    }
}

