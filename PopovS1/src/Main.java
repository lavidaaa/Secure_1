import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        Map<Character, Double> table1;
        Map<Character, Double> table2;

        int sdvig = 2;
        writeAndRead("E:\\fdsfds\\4 course\\secure\\PopovS1\\src\\glava1.txt", "E:\\fdsfds\\4 course\\secure\\PopovS1\\src\\glava1_encrypt.txt", sdvig);
        table1 = getMapCharacters("E:\\fdsfds\\4 course\\secure\\PopovS1\\src\\voyna-i-mir-tom-1.txt");
        table2 = getMapCharacters("E:\\fdsfds\\4 course\\secure\\PopovS1\\src\\glava1_encrypt.txt");
        writeAndRead("E:\\fdsfds\\4 course\\secure\\PopovS1\\src\\glava1_encrypt.txt", "E:\\fdsfds\\4 course\\secure\\PopovS1\\src\\glava1_decrypt.txt", getSdvig(table1, table2));
    }

    private static void writeAndRead(String readName, String writeName, int shift) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(readName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(writeName));

        String line;

        while ((line = reader.readLine()) != null) {
            writer.write(encrypt(line, shift) + "\n");
        }

        reader.close();
        writer.close();
    }

    private static Map<Character, Double> getMapCharacters(String name) throws IOException {//создаем частотную таблицу
        Map<Character, Double> map = new HashMap<Character, Double>();

        BufferedReader reader = new BufferedReader(new FileReader(name));
        String line;
        double count = 0;

        while ((line = reader.readLine()) != null) {
            analization(line, map);
        }

        for (Character key : map.keySet()) {//считаем сколько всего символов в таблице
            count = count + map.get(key);
        }
        for (Character key : map.keySet()) {//получаем частот  появления символов
            map.put(key, map.get(key) / count);
        }

        reader.close();
        return map;
    }

    private static void analization(String string, Map<Character, Double> map) {//анализируем строку из файла и считаем сколько в ней каких символов
        for (int i = 0; i < string.length(); i++) {
            if ((int) string.charAt(i) >= 1040 && (int) string.charAt(i) <= 1102) {
                if (!map.containsKey(string.charAt(i))) {
                    map.put(string.charAt(i), 1.0);
                } else {
                    map.put(string.charAt(i), map.get(string.charAt(i)) + 1);
                }
            }
        }
    }

    private static int getSdvig(Map<Character, Double> table1, Map<Character, Double> table2) {//получаем обратный сдвиг
        List<Map.Entry> a1 = getSortMap(new ArrayList<Map.Entry>(table1.entrySet()));
        List<Map.Entry> a2 = getSortMap(new ArrayList<Map.Entry>(table2.entrySet()));

        Character name1 = (Character) a1.get(a1.size() - 1).getKey();
        Character name2 = (Character) a2.get(a2.size() - 1).getKey();

        int reverseShift =  name1 - name2;
        System.out.println("reverseShift = " + reverseShift + "\n");

        return reverseShift;
    }

    private static List<Map.Entry> getSortMap(List<Map.Entry> a){

        Collections.sort(a,
                new Comparator() {
                    public int compare(Object o1, Object o2) {
                        Map.Entry e1 = (Map.Entry) o1;
                        Map.Entry e2 = (Map.Entry) o2;
                        return ((Comparable) e1.getValue()).compareTo(e2.getValue());
                    }
                });

        return  a;
    }

    public static String encrypt(String string, int shift){//шифруем строку
        String text = "";

        for(int i = 0; i < string.length(); i++){
            if((int)string.charAt(i) >= 1040 && (int) string.charAt(i) <= 1102 ){
                text = text + shift(string.charAt(i), shift);
            } else {
                text = text + string.charAt(i);
            }
        }

        return text;
    }

    public static char shift(char c, int shift){//сдвигаем символ
        int code = (int)c;
        if(shift > 32 || shift < -32){
            shift = shift % 32;
        }

        code = code + shift;
        //System.out.println("char c = '" + c + "' - '");
        c = (char) code;

        return  c;
    }
}
