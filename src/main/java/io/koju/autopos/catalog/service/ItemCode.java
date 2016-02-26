package io.koju.autopos.catalog.service;

public class ItemCode {

    private final static String alphabetString = "3KMEQPNHABTGCWUVRYZFSXJD";
    private final static char[] alphabet = alphabetString.toCharArray();
    private final static int base = alphabet.length;

    public static String encode(long id) {
        String result = "";
        long div;
        int mod;

        while (id >= base) {
            div = id / base;
            mod = (int) (id - (base * div));
            result = alphabet[mod] + result;
            id = div;
        }
        if (id > 0) {
            result = alphabet[(int) id] + result;
        }
        return result;
    }

    public static long decode(String code) {
        long result = 0;
        long multi = 1;
        while (code.length() > 0) {
            String digit = code.substring(code.length() - 1);
            result = result + multi * alphabetString.lastIndexOf(digit);
            multi = multi * base;
            code = code.substring(0, code.length() - 1);
        }
        return result;
    }

}
