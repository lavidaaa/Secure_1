import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String [] args){
        BigInteger q, N, g, k;
        String name, s, pass;

        Random random = new Random();

        q = BigInteger.probablePrime(1024, random);
        //N = 2 * q + 1;
        N = q.multiply(BigInteger.TWO).add(BigInteger.ONE);//модуль

        g = BigInteger.TWO;// В оригинале должен был быть первородный корень по модулю N
        k = SHA256.hash(N, g);//А вот тут к - хэш от N и g, функция хэширования была взята


        Server server = new Server(N, g, k, random);

        server.addUser("Denis", "rat ta");
        server.addUser("Masha", "Ulitka");

        server.login("Deni", "rat ta");
        server.login("Mash", "Ulitka");
    }
}
