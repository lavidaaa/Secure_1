import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class Server {

    private BigInteger N, g, k;
    private Random random;
    private BigInteger A, b, B, u, S, K, clientM, myM, R;
    private data data;
    private Client myClient;
    private HashMap<String, data> baseData= new HashMap<>();

    public Server(BigInteger n, BigInteger g, BigInteger k, Random r) {
        N = n;
        this.g = g;
        this.k = k;
        random = r;

        myClient = new Client(n, g, k, r);
    }

    public void addUser(String name, String password){//String salt, BigInteger v){
        myClient.setLogingData(name, password);
        baseData.put(name, new data(myClient.generateSalt(12), myClient.calculate_v()));
        myClient.clearing();
    }

    public void login(String name, String password) {
        if (baseData.containsKey(name)) {
            myClient.setLogingData(name, password);
            data = baseData.get(name);

            A = myClient.genA();
            if (!A.equals(BigInteger.ZERO)) {
                print("A != 0");
                myClient.setSalt(data.getS());
                if(myClient.calculate_u(calculate_B())){
                    calculate_u();
                    myClient.calculate_S();
                    calculate_S();

                    clientM = myClient.calculate_M();
                    calculate_M();

                    if(clientM.equals(myM)){
                        myClient.checkServerR(calculate_R());
                    } else {
                        print("invalid password");
                        return;
                    }

                } else {
                    print("u == 0");
                    return;
                }
            } else {
                print("A == 0");
                return;
            }
        } else {
            print("user(" + name + ") is not found");
            return;
        }
    }

    private BigInteger gen_b(){

        b = BigInteger.probablePrime(1024, random);
        return b;
    }

    public BigInteger calculate_B(){

        B = (k.multiply(data.getV()).add(g.modPow(gen_b(), N))).mod(N);
        return B;
    }

    public BigInteger calculate_u(){

        u = SHA256.hash(A, B);
        return u;
    }

    public void calculate_S(){

        S = A.multiply(data.getV().modPow(u, N)).modPow(b, N);
        calculate_K();
    }

    private void calculate_K(){

        K = SHA256.hash(S);
    }

    private void calculate_M(){

        myM = SHA256.hash(SHA256.hash(N).xor(SHA256.hash(g)), SHA256.hash(myClient.getName()), data.getS(), A, B, K);
    }

    private BigInteger calculate_R(){

        R = SHA256.hash(A, myM, K );
        return R;
    }

    private void print (String message){
        System.out.println("Server : " + message + ".");
    }

    private class data {
        private BigInteger v;
        private String s;

        data(String s, BigInteger v) {
            this.v = v;
            this.s = s;
        }

        public BigInteger getV() {
            return v;
        }

        public String getS() {
            return s;
        }
    }
}
