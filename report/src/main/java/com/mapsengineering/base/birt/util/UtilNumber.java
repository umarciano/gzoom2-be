package com.mapsengineering.base.birt.util;


public class UtilNumber {

	private static int concatRomanToken(StringBuffer roman, int number, int tokenValue, String romanToken){
		int n = number;		
		while(n >= tokenValue){
            roman.append(romanToken);
            n -= tokenValue;
        }
		return n;
	}
	
	public static String convertNumberToRomanNumeral(int num){
		StringBuffer roman = new StringBuffer();
		int number = num;
		
		if(num <= 0 || num >=3999){
			return "";
		}
        
		number = concatRomanToken(roman, number, 1000, "M");
		number = concatRomanToken(roman, number, 900, "CM");
        number = concatRomanToken(roman, number, 500, "D");
        number = concatRomanToken(roman, number, 400, "CD");
        number = concatRomanToken(roman, number, 100, "C");
        number = concatRomanToken(roman, number, 90, "XC");
        number = concatRomanToken(roman, number, 50, "L");
        number = concatRomanToken(roman, number, 40, "XL");
        number = concatRomanToken(roman, number, 10, "X");
        number = concatRomanToken(roman, number, 9, "IX");
        number = concatRomanToken(roman, number, 5, "V");
        number = concatRomanToken(roman, number, 4, "IV");
        number = concatRomanToken(roman, number, 1, "I");
        
        return roman.toString();
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static Float sumAssocWeightNumber(Float a, Float b, Float c) {			
		return a + b - c;
	}
	
	public static String getFormatPattern(int decimalScale, String um){

        String pattern = "#,##0";
        if (decimalScale > 0) {
            pattern = pattern + ".";
            
            for(int i=0; i < decimalScale; i++ ){
                pattern = pattern.concat("0");
            }
        }
        if ("%".equals(um) || (um != null && um.indexOf("Perc") != -1)) {
            pattern = pattern + "%";
        }
        return pattern;
        
    }
        	
}
