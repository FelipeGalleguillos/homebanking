package com.mindhub.homebanking.Utils;

public final class CardUtils {

    private CardUtils(){};

    public static String getCardNumber(){
        return (int) ((Math.random() * (9999 - 1000)) + 1000)+"-"+
                (int) ((Math.random() * (9999 - 1000)) + 1000)+"-"+
                (int) ((Math.random() * (9999 - 1000)) + 1000)+"-"+
                (int) ((Math.random() * (9999 - 1000)) + 1000);
    }

    public static int getCardCvv() {
        return (int)((Math.random() * (999 - 100)) + 100);
    }
}
