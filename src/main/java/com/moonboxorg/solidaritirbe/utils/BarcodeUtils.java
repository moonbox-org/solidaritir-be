package com.moonboxorg.solidaritirbe.utils;

public class BarcodeUtils {

    private BarcodeUtils() {
    }

    /**
     * Validates an EAN-13 barcode.
     *
     * @param ean the EAN-13 barcode to validate
     * @return true if the EAN-13 barcode is valid, false otherwise
     *
     * <p>A valid EAN-13 barcode consists of 13 digits and passes the EAN-13 checksum test.
     * The checksum test is performed by multiplying each digit at an even position by 1 and each digit at an odd position by 3,
     * summing the results, and checking if the remainder of the sum divided by 10 is equal to the last digit of the barcode.
     */
    public static boolean validateEAN13(String ean) {
        if (ean == null || ean.length() != 13 || !ean.matches("\\d+"))
            return false;

        int[] digits = new int[13];
        for (int i = 0; i < 13; i++) {
            digits[i] = Character.getNumericValue(ean.charAt(i));
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                sum += digits[i];
            } else {
                sum += digits[i] * 3;
            }
        }

        int checksumDigit = (10 - (sum % 10)) % 10;

        return checksumDigit == digits[12];
    }
}
