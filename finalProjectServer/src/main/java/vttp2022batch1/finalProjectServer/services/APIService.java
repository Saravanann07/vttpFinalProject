package vttp2022batch1.finalProjectServer.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class APIService {

    //1
    private static final String QUOTE_URL = "https://finnhub.io/api/v1/quote";

    private static final String COMPANY_NAME_URL = "https://finnhub.io/api/v1/stock/profile2";


    @Value("${api.finnhub.key}")
    private String finnhubKey;
 
     public Double getQuote(String symbol){
 
         String quote = UriComponentsBuilder.fromUriString(QUOTE_URL)
             .queryParam("symbol", symbol)
             .queryParam("token", finnhubKey)
             .toUriString();
         System.out.println(">>>>>>>>" + quote);
 
 //          //3
         RequestEntity<Void> req = RequestEntity
             .get(quote)
             .accept(MediaType.APPLICATION_JSON)
             .build();
 
 //         //4
         RestTemplate template = new RestTemplate();
 
 //          //5 
         ResponseEntity<String> resp = template.exchange(req, String.class);
 
 //         //6
         InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
         JsonReader reader = Json.createReader(is);
         JsonObject obj = reader.readObject();
 
         Double stockPrice = obj.getJsonNumber("c").doubleValue();
         System.out.println(">>>>>>>>" + stockPrice);
         return stockPrice;
         
     }

     public String getCompanyName(String symbol){

        String name = UriComponentsBuilder.fromUriString(COMPANY_NAME_URL)
             .queryParam("symbol", symbol)
             .queryParam("token", finnhubKey)
             .toUriString();
         System.out.println(">>>>>>>>" + name);

         RequestEntity<Void> req = RequestEntity
             .get(name)
             .accept(MediaType.APPLICATION_JSON)
             .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject obj = reader.readObject();

        String companyName = obj.getString("name");

        return companyName;
     }
     
 }
    

