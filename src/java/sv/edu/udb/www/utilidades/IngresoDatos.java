package sv.edu.udb.www.utilidades;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IngresoDatos {

    private static int entero;
    private static double decimal;
    private static String cadena;

    public static boolean esEntero(String cadena) {
        try {
            entero = Integer.parseInt(cadena.trim());
            return true;
        } catch (Exception a) {
            return false;
        }
    }

    public static boolean esEnteroPositivo(String cadena) {
        try {
            entero = Integer.parseInt(cadena.trim());
            if (entero <= 0) {
                return false;
            }
            return true;
        } catch (Exception a) {
            return false;
        }
    }

    public static boolean esCadena(String mensaje) {
        if (!mensaje.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean esDecimal(String cadena) {
        try {
            decimal = Double.parseDouble(cadena.trim());
            return true;
        } catch (Exception a) {
            return false;
        }
    }

    public static boolean esDecimalPositivo(String cadena) {
        try {
            decimal = Double.parseDouble(cadena.trim());
            if (decimal <= 0) {
                return false;
            }
            return true;
        } catch (Exception a) {
            return false;
        }
    }
    
    public static boolean esCodigoLibro(String cadena) {
        Pattern pat = Pattern.compile("LIB[0-9]{6}");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }

}
