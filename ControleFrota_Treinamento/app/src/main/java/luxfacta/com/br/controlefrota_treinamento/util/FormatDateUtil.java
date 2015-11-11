package luxfacta.com.br.controlefrota_treinamento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Luxfacta on 21/08/2015.
 */
public class FormatDateUtil {

private static final String padrao = "dd/MM/yyyy HH:mm:ss";

    public static Date formatDate(String data) {
        SimpleDateFormat format = new SimpleDateFormat(padrao);
        Date dataAt = null;
        try {
            dataAt = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataAt;
    }

    public static String formateDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat(padrao);
        return format.format(date);
    }
}
