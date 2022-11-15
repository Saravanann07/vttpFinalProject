package vttp2022batch1.finalProjectServer.controllers;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022batch1.finalProjectServer.models.Response;
import vttp2022batch1.finalProjectServer.models.Stock;
import vttp2022batch1.finalProjectServer.repositories.UserRepository;
import vttp2022batch1.finalProjectServer.models.Registration;
import vttp2022batch1.finalProjectServer.services.APIService;
import vttp2022batch1.finalProjectServer.services.DOService;
import vttp2022batch1.finalProjectServer.services.EmailServiceImpl;
import vttp2022batch1.finalProjectServer.services.StockService;
import vttp2022batch1.finalProjectServer.services.UserService;

@RestController
@RequestMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    @Autowired
    private APIService apiSvc;

    @Autowired
    private DOService doSvc;



    @Autowired
    private UserService userSvc;

    @Autowired
    private EmailServiceImpl emailImplSvc;

    @Autowired
    private StockService stockSvc;

    @Autowired
    private UserRepository userRepo;


    @PostMapping(path="/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerUser(@ModelAttribute Registration reg){

        System.out.println(reg.getUsername());

        boolean userExists = userSvc.checkUserExists(reg.getUsername());

        System.out.println(">>>>>"+ !userExists);
        Number userExist = userRepo.checkUserExists(reg.getUsername());

        Response resp = new Response();

        if (userExists){
            resp.setCode(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Error registering user");
        }
        
        else{
        boolean addUser = userSvc.addUser(reg);
        System.out.println(addUser);
        boolean photoUploaded = doSvc.postS3upload(reg);
        System.out.println(photoUploaded);
    

        

        if (addUser && photoUploaded){

            resp.setCode(HttpStatus.OK.value());
            resp.setMessage("Registration successful");

            emailImplSvc.sendSimpleEmail(reg.getEmail(), reg.getUsername());
        }
        else{
            resp.setCode(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Error registering user");
        }
    }

        return ResponseEntity
                    .status(resp.getCode())
                    .body(Response.toJson(resp).toString());
        
    }

    @PostMapping(path="/stockAdded", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAdded(@RequestBody String payload) throws ParseException{


        System.out.println("Testtttttttt");

        JsonReader reader = Json.createReader(new ByteArrayInputStream(payload.getBytes()));

        JsonObject obj;

        Response resp = new Response();
        try {
            obj = reader.readObject();
        } catch (Exception e) {
            resp.setCode(400);
            resp.setMessage("Error occurred while reading json payload in adding a new contact.");
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.toJson(resp).toString());
        }

      
        Stock stock = Stock.create(obj);
        if (apiSvc.getCompanyName(stock.getSymbol()) == null){
            resp.setCode(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Error adding stck purchase. Please try again");
        }
        boolean addStock = stockSvc.addStockPurchase(stock);

        if (addStock){
            resp.setCode(HttpStatus.OK.value());
            resp.setMessage("Stock purchase has been successfully added to list");
        } else {
            resp.setCode(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Error adding stock purchase");
        }

        return ResponseEntity
                    .status(resp.getCode())
                    .body(Response.toJson(resp).toString());


    }

    @GetMapping(path="/homepage")
    public ResponseEntity<String> getUserStockList(@RequestParam String userId){

        Integer userIdInt = Integer.parseInt(userId);
       
        System.out.println("Username:>>>>"+ userId);

        List<Stock> stockList = stockSvc.getUserStockList(userIdInt);
        List<JsonObject> objectList = new LinkedList<>();
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (Stock stock : stockList){
            System.out.println(stock.getCompanyName());
            Double sharePrice = apiSvc.getQuote(stock.getSymbol());
            Double marketValue = sharePrice*stock.getQuantity();
            String companyName = apiSvc.getCompanyName(stock.getSymbol());
            // objectList.add(Stock.toJson(stock, marketValue)); 
            objectList.add(Stock.toJsonName(stock, companyName, marketValue));
            System.out.println(objectList);

            // builder.add(Stock.toJson(stock, marketValue));
            builder.add(Stock.toJsonName(stock, companyName, marketValue));
        }
        return ResponseEntity.ok().body(builder.build().toString());
    }

    @GetMapping(path="api/{date}")
    public ResponseEntity<String> getUserStockListByDate(@RequestParam String userId, @PathVariable String date) throws ParseException{

        Integer userIdInt = Integer.parseInt(userId);

        System.out.println(date);

        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        System.out.println(date1);

        List<Stock> stockList = stockSvc.getUserStockListByDate(date, userIdInt);

        List<JsonObject> objectList = new LinkedList<>();
        System.out.println(objectList);
        JsonArrayBuilder builder = Json.createArrayBuilder();


        for (Stock stock : stockList){
            Double sharePrice = apiSvc.getQuote(stock.getSymbol());
            Double marketValue = sharePrice*stock.getQuantity();
            String companyName = apiSvc.getCompanyName(stock.getSymbol());

            objectList.add(Stock.toJsonName(stock, companyName, marketValue)); 
            System.out.println(objectList);
            builder.add(Stock.toJsonName(stock, companyName, marketValue));

        }
        return ResponseEntity.ok().body(builder.build().toString());
    }

    @GetMapping(path="/company")
    public ResponseEntity<String> getUserStockListBySymbol(@RequestParam String symbol, @RequestParam String userId){

        JsonArrayBuilder builder = Json.createArrayBuilder();

        Integer userIdInt = Integer.parseInt(userId);

        List<Stock> stockList = stockSvc.getUserStockListByCompany(symbol, userIdInt);
        List<JsonObject> objectList = new LinkedList<>();

        for (Stock stock : stockList){
            Double sharePrice = apiSvc.getQuote(stock.getSymbol());
            Double marketValue = sharePrice*stock.getQuantity();
            String companyName = apiSvc.getCompanyName(stock.getSymbol());

            objectList.add(Stock.toJsonName(stock, companyName, marketValue)); 
            System.out.println(">>>>> " + objectList);
            // builder.add(Stock.toJson(stock, marketValue));
            builder.add(Stock.toJsonName(stock, companyName, marketValue));

        }
        return ResponseEntity.ok().body(builder.build().toString());
    }

    @PostMapping(path = "/stockDeleted", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteContact(@RequestBody String payload) throws ParseException{

        JsonReader reader = Json.createReader(new ByteArrayInputStream(payload.getBytes()));
        JsonObject obj;
        Response resp = new Response();

        try {
            obj = reader.readObject();
        } catch (Exception e) {
            resp.setCode(400);
            resp.setMessage("Error occurred while reading json payload in deleting a contact.");
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.toJson(resp).toString());
        }

        Stock stock = Stock.delete(obj);
        boolean stockDeleted = stockSvc.deleteStock(stock);

        if (!stockDeleted){
            resp.setCode(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Error! Stock purchase cannot be deleted");
        } else {
            resp.setCode(HttpStatus.OK.value());
            resp.setMessage("Stock purchase successfully deleted");
        }

        return ResponseEntity
                    .status(resp.getCode())
                    .body(Response.toJson(resp).toString());

    }

    @GetMapping(path="/logout")
    public ResponseEntity<String> logout(){

        System.out.println(">>>>>Logging out!!!!");
        Response resp = new Response();
        resp.setCode(HttpStatus.OK.value());
        resp.setMessage("Logged out successfully");

        return ResponseEntity
        .status(resp.getCode())
        .body(Response.toJson(resp).toString());

    }


    


   
}
