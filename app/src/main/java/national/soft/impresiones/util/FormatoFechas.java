package national.soft.impresiones.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormatoFechas {

    public static String obtenerhora() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    private static String obtenerFecha(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date now = new Date();
        String strDate = format.format(now);
        return strDate;
    }

    public static boolean esPrimerDiadelMes(Calendar calendar){
        if (calendar == null)
            return false;

        int diaDelMes = calendar.get(Calendar.DAY_OF_MONTH);
        return (diaDelMes == 1);
        //return (diaDelMes == Calendar.DAY_OF_MONTH);
    }

    public boolean comparaDates(String inicio, String fin, int aplicamismodia) {
        boolean res = false;
        Date date = new Date();
        Date dateuno;
        Date datedos;
        String today = hoy().trim();
        String in = "";
        String fi = "";
        //1 es para el mismo dia
        //2 es para cuando aplica de el dia anterior para mañana
        //3 es para cuando aplica del dia de hoy para mañana
        switch (aplicamismodia) {
            case 1:
                in = today + "T" + inicio;
                fi = today + "T" + fin;
                break;
            case 2:
                in = DiaAnterior() + "T" + inicio;
                fi = today + "T" + fin;
                break;
            case 3:
                in = today + "T" + inicio;
                fi = DiaSiguiente() + "T" + fin;
                break;
        }

        dateuno = ConvierteFecha(in);
        datedos = ConvierteFecha(fi);
        if (datedos.after(date) && dateuno.before(date)) {
            res = true;
        }

        return res;
    }

    public boolean comparaHorasMismoDia(String horainicio, String horafinal) {
        boolean res = false;
        Date horaactual = new Date();
        String hoy = hoy();
        String in = hoy + "T" + horainicio;
        String fi = hoy + "T" + horafinal;
        Date inicio = ConvierteFecha(in);
        Date fin = ConvierteFecha(fi);
        if (inicio.before(horaactual) && fin.after(horaactual)) {
            res = true;
        }

        return res;
    }

    public boolean comparaFechaunaHora(String horacomparar){
        //METODO REGRESA TRUE SI LA FECHA ENVIADA ENTRA DEL RANGO DEL AHORA HASTA HACE UNA HORA
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.setTime(now);
        cal.add(Calendar.HOUR, -1);
        Date oneHourBack = cal.getTime();
        //SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy HH:mm:ss", Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        try{
            Date horaComparar = format.parse(horacomparar);
            if(horaComparar.after(oneHourBack) && horaComparar.before(now)){
                return true;
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

    private static Date ConvierteFecha(String fecha) {
        Date res = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss aa", Locale.US);

        try {
            res = format.parse(fecha);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return res;
    }

    public static String ConvierteHoraDeFecha(String fecha) {
        String res = "";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm aa", Locale.US);
        try {
            Date date = formato.parse(fecha);
            res = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String diffTime(String dateStart){
        String diferencia = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date now =  null;
        Date since = null;
        now = ConvierteFecha(obtenerhora());
        try {
            since = format.parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (now != null && since != null) {
            long diff = now.getTime() - since.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 60;

            if (diffSeconds < 1) {
                return "Hace menos de un segundo";
            } else if (diffMinutes < 1) {
                return "Hace " + diffSeconds + " segundos";
            } else if (diffHours < 1) {
                return "Hace " + diffMinutes + " minutos";
            }
        }

        return diferencia;
    }

    @SuppressLint("SimpleDateFormat")
    private String hoy() {
        String res = "";
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String datetime = dateformat.format(date);
        res = datetime;
        return res;
    }

    @SuppressLint("SimpleDateFormat")
    private String DiaSiguiente() {
        String manana = "";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = dateformat.format(tomorrow);
        manana = datetime;
        return manana;
    }

    @SuppressLint("SimpleDateFormat")
    private String DiaAnterior() {
        String manana = "";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date tomorrow = calendar.getTime();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = dateformat.format(tomorrow);
        manana = datetime;
        return manana;
    }

    public boolean validaDiaAplicacion(boolean aplicaDiaAnterior, int finalizaDiaAnterior, String horaInicioAnterior, String horaFinAnterior,
                                       boolean aplicaHoy, int finalizaHoy, String horaInicioHoy, String horaFinHoy) {
        //PRIMERO SE VERIFICA SI APLICo EL DIA ANTERIOR Y TODAVIA APLICA HASTA HOY
        //DESPUES SE TOMAN LAS HORAS DEL INICIO Y LA HORA FIN, LA CUAL PERTENECE AL DIA ACTUAL
        FormatoFechas f = new FormatoFechas();
        if (aplicaDiaAnterior&& finalizaDiaAnterior == 2
                && f.comparaDates(horaInicioAnterior, horaFinAnterior, 2)) {
            return true;
        } else {
            //SI NO APLICA EL DIA ANTERIOR, ENTONCES APLICA EL DIA DE HOY
            //APLICA 1 = TERMINA HOY, APLICA 3 = TERMINA MAÑANA
            if (aplicaHoy) {
                int aplica = 1;
                if (finalizaHoy == 2) {
                    aplica = 3;
                }
                if (f.comparaDates(horaInicioHoy, horaFinHoy, aplica)) {
                    return true;
                }
            }
        }
        return false;
    }
}
