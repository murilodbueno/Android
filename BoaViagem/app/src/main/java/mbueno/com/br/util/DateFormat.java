package mbueno.com.br.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mbueno on 13/08/2015.
 */
public class DateFormat {

    public static String formatDate(Date data){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(data);
    }
}
