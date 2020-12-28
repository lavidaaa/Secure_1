import java.math.BigInteger;
import java.util.Random;

public class Client {

    private BigInteger N, g, k;
    private BigInteger x, a, A, B, u, S, K, M, R;
    private String s;
    private Random random;

    private String myName, myPass;

    public Client(BigInteger n, BigInteger g, BigInteger k, Random r) {
        N = n;
        this.g = g;
        this.k = k;
        random = r;
    }

    public void setLogingData(String myName, String myPass){
        this.myName = myName;
        this.myPass = myPass;
    }

    public BigInteger genA(){
        A = g.modPow(gen_a(), N);
        return A;
    }

    private BigInteger gen_a(){

        a = BigInteger.probablePrime(1024, random);
        return a;
    }

    public BigInteger calculate_x(){

        x = SHA256.hash(s, myPass);
        return x;
    }

    public BigInteger calculate_v(){

        return g.modPow(calculate_x(), N);
    }

    public boolean calculate_u(BigInteger B){
        if(!B.equals(BigInteger.ZERO)){
            this.B = B;
            print("B != 0");
            u = SHA256.hash(A, B);
        } else {
            print("B == 0");
        }
        return !u.equals(BigInteger.ZERO);
    }

    public void calculate_S(){

        //S = (B.subtract(k.multiply(g.modPow(calculate_x(), N)))).modPow(a.add(u.multiply(calculate_x())), N);
        S = (B.subtract(k.multiply(calculate_v()))).modPow(a.add(u.multiply(calculate_x())), N);
        calculate_K();
        //return S;
    }

    private void calculate_K(){

        K = SHA256.hash(S);
        //return K;
    }

    public BigInteger calculate_M(){

        M = SHA256.hash(SHA256.hash(N).xor(SHA256.hash(g)), SHA256.hash(myName), s, A, B, K);
        return M;
    }

    public void checkServerR(BigInteger serverR){
        if(calculate_R().equals(serverR)){
            print("connection established");
        } else {
            print("disconnect");
        }

        clearing();
    }

    private BigInteger calculate_R(){

        R = SHA256.hash(A, M, K);
        return R;
    }

    public String generateSalt(int size){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String result = "";

        for (int i = 0; i < size; i++){

            result += alphabet.charAt( random.nextInt(alphabet.length()));
        }

        s = result;
        return result;
    }

    public void clearing(){
        s = myName = myPass = "";
    }

    public void setSalt(String s){
        this.s = s;
    }

    private void print (String message){
        System.out.println(myName + " : " + message + ".");
    }

    public String getName() {
        return myName;
    }
}
