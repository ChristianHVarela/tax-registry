package com.tax.registry.utils;

import java.util.InputMismatchException;

public class ValidationUtils {

    public static boolean isCpfValid(String CPF) {
        CPF = removeSpecialCharacters(CPF);
        if (CPF.equals("11111111111") || CPF.equals("22222222222") || 
            CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") || 
            CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") || 
            CPF.equals("99999999999") || (CPF.length() != 11)) {
            return false;
        }
        char dig10, dig11;
        int sm, i, r, num, weight;
        try {
            sm = 0;
            weight = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);
            }
            sm = 0;
            weight = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return true;
            } else {
                return false;
            }
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public static boolean isCnpjValid(String CNPJ) {
        CNPJ = removeSpecialCharacters(CNPJ);
        if (CNPJ.equals("11111111111111") || CNPJ.equals("22222222222222") || 
            CNPJ.equals("33333333333333") || CNPJ.equals("44444444444444") || 
            CNPJ.equals("55555555555555") || CNPJ.equals("66666666666666") || 
            CNPJ.equals("77777777777777") || CNPJ.equals("88888888888888") || 
            CNPJ.equals("99999999999999") || (CNPJ.length() != 14)) {
            return false;
        }
        char dig13, dig14;
        int sm, i, r, num, weight;
        try {
            sm = 0;
            weight = 2;
            for (i = 11; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10) {
                    weight = 2;
                }
            }
            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig13 = '0';
            } else {
                dig13 = (char) ((11 - r) + 48);
            }
            sm = 0;
            weight = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10) {
                    weight = 2;
                }
            }
            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig14 = '0';
            } else {
                dig14 = (char) ((11 - r) + 48);
            }
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13))) {
                return true;
            } else {
                return false;
            }
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public static String removeSpecialCharacters(String doc) {
        if (doc.contains(".")) {
            doc = doc.replace(".", "");
        }
        if (doc.contains("-")) {
            doc = doc.replace("-", "");
        }
        if (doc.contains("/")) {
            doc = doc.replace("/", "");
        }
        if (doc.contains("(")) {
            doc = doc.replace("(", "");
        }
        if (doc.contains(")")) {
            doc = doc.replace(")", "");
        }
        doc = doc.trim();
        return doc;
    }
    
    public static String addMask(String document) {
        document = removeSpecialCharacters(document); 

        if (document.length() == 11) {
            return document.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        } else if (document.length() == 14) {
            return document.replaceAll("(\\d{2})(\\d{4})(\\d{4})(\\d{2})(\\d{4})", "$1.$2.$3/$4-$5");
        } else {
            return document; 
        }
    }

}