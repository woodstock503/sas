/* SOLAR IT CONSULTANT CO.,LTD.
 * http://www.solar.co.th
 * (C) 2008, Solar it consultant co.,ltd.
 *
 * ETA - SOBRR Project. 
 *  
 */
package sas.saccplus.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * TODO Purpose of
 * <p>
 * </p>
 * 
 * @author Vitthaya
 * @since 1.0.0
 */

public class StringUtil {

    public static String concat( String str1, int str2 ) {
        return str1 + str2;
    }

    public static String concat( String str1, String str2 ) {
        return str1 + str2;
    }

    public static double round( double a, int i ) {
        for( int ii = 0; ii < i; ii++ ) {
            a *= 10; // Shift left 1 position
        }

        if (a % 5 == 0) {
            a = StrictMath.ceil(a);
        } else {
            a = StrictMath.round(a);
        }

        for( int ii = 0; ii < i; ii++ ) {
            a /= 10; // Shift right 1 position
        }
        return a;
    }

    public static String n2b( String in ) {
        if (in == null) {
            return "";
        } else {
            return in;
        }
    }

    public static Integer n2b( Integer in ) {
        if (in == null) {
            return 0;
        } else {
            return in;
        }
    }
    
    public static BigInteger n2b( BigInteger in ) {
        if (in == null) {
            return new BigInteger("0");
        } else {
            return in;
        }
    }

    public static Short n2b( Short in ) {
        if (in == null) {
            return 0;
        } else {
            return in;
        }
    }

    public static Long n2b( Long in ) {
        if (in == null) {
            return new Long(0);
        } else {
            return in;
        }
    }

    public static Double n2b( Double in ) {
        if (in == null) {
            return new Double(0);
        } else {
            return in;
        }
    }

    public static BigDecimal n2b( BigDecimal in ) {
        if (in == null) {
            return new BigDecimal(0);
        } else {
            return in;
        }
    }

    public static String n2b( Character in ) {
        if (in == null) {
            return "";
        } else {
            return String.valueOf(in);
        }
    }

    public static String unicodeToMS874( String str ) {
        StringBuffer strTemp = new StringBuffer(str);
        int code;
        for( int i = 0; i < str.length(); i++ ) {
            code = strTemp.charAt(i);
            if ((0xE01 <= code) && (code <= 0xE5B)) {
                strTemp.setCharAt(i, (char) (code - 0xD60));
            }
        }
        return strTemp.toString();
    }

    public static String MS874ToUnicode( String str ) {
        StringBuffer strTemp = new StringBuffer(str);
        int code;
        for( int i = 0; i < str.length(); i++ ) {
            code = strTemp.charAt(i);
            if ((0xA1 <= code) && (code <= 0xFB)) {
                strTemp.setCharAt(i, (char) (code + 0xD60));
            }
        }
        return strTemp.toString();
    }

}
