package io.koju.autopos.catalog.service;

public class ItemCode {

    private final static String alphabetString = "3KMEQPNHABTGCWUVRYZFSXJD";
    private final static char[] alphabet = alphabetString.toCharArray();
    private final static int base = alphabet.length;

    public static String getCode(long id) {
        String code = "";
        long divisor;
        int mod;

        while (id >= base) {
            divisor = id / base;
            mod = (int) (id - (base * divisor));
            code = alphabet[mod] + code;
            id = divisor;
        }
        if (id > 0) {
            code = alphabet[(int) id] + code;
        }
        return code;
    }

    public static long getId(String code) {
        long id = 0;
        long multiplier = 1;
        while (code.length() > 0) {
            String digit = code.substring(code.length() - 1);
            id = id + multiplier * alphabetString.lastIndexOf(digit);
            multiplier = multiplier * base;
            code = code.substring(0, code.length() - 1);
        }
        return id;
    }

}
