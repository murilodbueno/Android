package com.mbueno;

import com.mbueno.vo.Content;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Enviando POST para servidor GCM" );
        
        //Chave do servidor Google
        String apiKey = "AIzaSyBH0I_WER44VjYC-BkuyJCRIog1C6L7xLs";
        
        Content content = createContent();
        
        POST2GCM.post(apiKey, content);
    }
    
    public static Content createContent(){
		
		Content c = new Content();
		
		//ID gerado por aparelho
		c.addRegId("APA91bFxWNB0xtlLp7al1fE2CLyYlUSJ13J04BI9KLiAqm5AFd0_BzvZcFQoU_SvGeywQWbBmBK7b9k1UksvJsOv3VavuW5ELqfFIEYe3Cip6GZbp_PmXHYEZ9bGNp3falDoTkmpLgBw");
		c.createData("Test Title", "Test Message");
		
		return c;
	}
}
