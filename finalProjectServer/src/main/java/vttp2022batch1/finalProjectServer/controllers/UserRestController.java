package vttp2022batch1.finalProjectServer.controllers;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.xdevapi.JsonArray;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022batch1.finalProjectServer.models.Response;
import vttp2022batch1.finalProjectServer.models.Stock;
import vttp2022batch1.finalProjectServer.models.User;
import vttp2022batch1.finalProjectServer.repositories.UserRepository;
import vttp2022batch1.finalProjectServer.services.APIService;
import vttp2022batch1.finalProjectServer.services.EmailServiceImpl;
import vttp2022batch1.finalProjectServer.services.StockService;
import vttp2022batch1.finalProjectServer.services.UserService;

@RestController
@RequestMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    @Autowired
    private APIService apiSvc;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userSvc;

    @Autowired
    private EmailServiceImpl emailImplSvc;

    @Autowired
    private StockService stockSvc;

    @PostMapping(path="registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@RequestBody String payload){

        JsonReader reader = Json.createReader(new ByteArrayInputStream(payload.getBytes()));

        JsonObject obj;

        Response resp = new Response();

        try{
            obj = reader.readObject();
        } catch (Exception e){
            resp.setCode(400);
            resp.setMessage("error with payload");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Response.toJson(resp).toString());
        }

        User user = User.create(obj);

        boolean addUser = userSvc.addUser(user);

        if (addUser){
            resp.setCode(HttpStatus.OK.value());
            resp.setMessage("User has been successfully registered");
            emailImplSvc.sendSimpleEmail(user.getEmail(), user.getUsername());
        }
        else {
            resp.setCode(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Error registering user");
        }
        return ResponseEntity
                .status(resp.getCode())
                .body(Response.toJson(resp).toString());
    }

    // @PostMapping(path="authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> postLogin(@RequestBody String payload, HttpSession sess){

    //     JsonReader reader = Json.createReader(new ByteArrayInputStream(payload.getBytes()));

    //     JsonObject obj;

    //     Response resp = new Response();

    //     try{
    //         obj = reader.readObject();
    //     } catch (Exception e){
    //         resp.setCode(400);
    //         resp.setMessage("error with payload");
    //         return ResponseEntity
    //                 .status(HttpStatus.BAD_REQUEST)
    //                 .body(Response.toJson(resp).toString());
    //     }

    //     User user = User.createLogin(obj);

    //     boolean userExist = userSvc.authenticate(user);
    //     User user1 = userRepo.getUser(user);

    //     if (!userExist){
    //         resp.setCode(HttpStatus.UNAUTHORIZED.value());
    //         resp.setMessage("Invalid credentials. Please try again");
    //     }
    //     else{
    //         resp.setCode(HttpStatus.OK.value());
    //         resp.setMessage("Login Successful");   
    //     }
    //     List<Stock> stockList = stockSvc.getUserStockList(user1.getUserId());
    //         List<JsonObject> objectList =new LinkedList<>();

    //         for (Stock stock : stockList){

    //             Double sharePrice = apiSvc.getQuote(stock.getSymbol());
    //             Double marketValue = sharePrice*stock.getQuantity();
    //             objectList.add(Stock.toJson(stock, marketValue)); 
    //         }

    //         sess.setAttribute("username", user.getUsername());
    //         sess.setAttribute("password", user.getPassword());
    //     return ResponseEntity.ok().body(objectList.toString());
                    

    // }

    @PostMapping(path="authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postLogin(@RequestBody String payload, HttpSession sess){

        JsonReader reader = Json.createReader(new ByteArrayInputStream(payload.getBytes()));

        JsonObject obj;

        Response resp = new Response();

        try{
            obj = reader.readObject();
        } catch (Exception e){
            resp.setCode(400);
            resp.setMessage("error with payload");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Response.toJson(resp).toString());
        }

        User user = User.createLogin(obj);

        boolean userExist = userSvc.authenticate(user);
        User user1 = userRepo.getUser(user);

        if (!userExist){
            resp.setCode(HttpStatus.UNAUTHORIZED.value());
            resp.setMessage("Invalid credentials. Please try again");
        }
        else{
            resp.setCode(HttpStatus.OK.value());
            resp.setMessage("Login Successful");   
        }

            sess.setAttribute("username", user1.getUsername());
            sess.setAttribute("password", user1.getPassword());
        // return ResponseEntity.ok().body(objectList.toString());
        return ResponseEntity
        .status(resp.getCode())
        .body(Response.toJson(resp).toString());
    }

    @PostMapping(path="stockAdded", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAdded(@RequestBody String payload, HttpSession sess) throws ParseException{

        String username = (String) sess.getAttribute("username");
        String password = (String) sess.getAttribute("password");

        User user = userRepo.getUser1(username, password);

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
        boolean addStock = stockSvc.addStockPurchase(stock, user.getUserId());

        if (addStock){
            resp.setCode(HttpStatus.OK.value());
            resp.setMessage("Stock purchase has been successfully added to list");
        } else {
            resp.setCode(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Error adding contact to list");
        }

        return ResponseEntity
                    .status(resp.getCode())
                    .body(Response.toJson(resp).toString());


    }

    @GetMapping(path="homepage")
    public ResponseEntity<String> getUserStockList(@RequestParam String username){
       
        // String username1 = (String) sess.getAttribute("username");
        System.out.println("Username:>>>>"+ username);
        // String password = (String) sess.getAttribute("password");

        // User user = userRepo.getUser1(username, password);
        Integer userId = userRepo.getUserId(username);
        System.out.println(">>>>>>>UserId " + userId);


        List<Stock> stockList = stockSvc.getUserStockList(userId);
        List<JsonObject> objectList = new LinkedList<>();
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (Stock stock : stockList){
            System.out.println(stock.getCompanyName());
            Double sharePrice = apiSvc.getQuote(stock.getSymbol());
            Double marketValue = sharePrice*stock.getQuantity();
            objectList.add(Stock.toJson(stock, marketValue)); 

            builder.add(Stock.toJson(stock, marketValue));
        }
        return ResponseEntity.ok().body(builder.build().toString());
    }

    @GetMapping(path="/{date}")
    public ResponseEntity<String> getUserStockListByDate(@PathVariable String date, HttpSession sess) throws ParseException{

        String username = (String) sess.getAttribute("username");
        String password = (String) sess.getAttribute("password");

        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        User user = userRepo.getUser1(username, password);
        System.out.println(">>>>>>> " + username);

        List<Stock> stockList = stockSvc.getUserStockListByDate(date1, user.getUserId());
        List<JsonObject> objectList = new LinkedList<>();

        for (Stock stock : stockList){
            Double sharePrice = apiSvc.getQuote(stock.getSymbol());
            Double marketValue = sharePrice*stock.getQuantity();
            objectList.add(Stock.toJson(stock, marketValue)); 
        }
        return ResponseEntity.ok().body(objectList.toString());
    }

    @GetMapping(path="company")
    public ResponseEntity<String> getUserStockListBySymbol(@RequestParam String symbol, HttpSession sess){

        String username = (String) sess.getAttribute("username");
        String password = (String) sess.getAttribute("password");

        User user = userRepo.getUser1(username, password);

        List<Stock> stockList = stockSvc.getUserStockListByCompany(symbol, user.getUserId());
        List<JsonObject> objectList = new LinkedList<>();

        for (Stock stock : stockList){
            Double sharePrice = apiSvc.getQuote(stock.getSymbol());
            Double marketValue = sharePrice*stock.getQuantity();
            objectList.add(Stock.toJson(stock, marketValue)); 
            System.out.println(">>>>> " + objectList);
        }
        return ResponseEntity.ok().body(objectList.toString());
    }

    @PostMapping(path = "stockDeleted", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteContact(@RequestBody String payload, HttpSession sess) throws ParseException{

        String username = (String) sess.getAttribute("username");
        String password = (String) sess.getAttribute("password");

        User user = userRepo.getUser1(username, password);

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

        List<Stock> stockList = stockSvc.getUserStockList(user.getUserId());
        List<JsonObject> objectList =new LinkedList<>();

        for (Stock stock1 : stockList){

            Double sharePrice = apiSvc.getQuote(stock1.getSymbol());
            Double marketValue = sharePrice*stock1.getQuantity();
            objectList.add(Stock.toJson(stock1, marketValue)); 
        }
        return ResponseEntity.ok().body(objectList.toString());
    }


    

    @GetMapping(path="logout")
    public ResponseEntity<String> logout (HttpSession sess){
        sess.invalidate();
        return null;
    }
   
}
