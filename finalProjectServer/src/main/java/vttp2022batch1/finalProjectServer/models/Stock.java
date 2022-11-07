package vttp2022batch1.finalProjectServer.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Stock {

    private Integer stockId;
    private Date purchaseDate;
    private String symbol;
    private String companyName;
    private Integer quantity;
    private Double stockPrice;
    private Double totalPrice;
    private Double stockValue;
    private Integer userId;


    public Integer getStockId() {return this.stockId;}
    public void setStockId(Integer stockId) {this.stockId = stockId;}

    public Date getPurchaseDate() {return this.purchaseDate;}
    public void setPurchaseDate(Date purchaseDate) {this.purchaseDate = purchaseDate;}

    public String getSymbol() {return this.symbol;}
    public void setSymbol(String symbol) {this.symbol = symbol;}

    public String getCompanyName() {return this.companyName;}
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public Integer getQuantity() {return this.quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}

    public Double getStockPrice() {return this.stockPrice;}
    public void setStockPrice(Double stockPrice) {this.stockPrice = stockPrice;}

    public Double getTotalPrice() {return this.totalPrice;}
    public void setTotalPrice(Double totalPrice) {this.totalPrice = totalPrice;}

    public Double getStockValue() {return this.stockValue;}
    public void setStockValue(Double stockValue) {this.stockValue = stockValue;}


    public Integer getUserId() {return this.userId;}
    public void setUserId(Integer userId) {this.userId = userId;}


    public static Stock create(JsonObject obj) throws ParseException{

        Stock stock = new Stock();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // stock.setStockId(obj.getInt("stockId"));
        stock.setPurchaseDate(formatter.parse(obj.getString("purchaseDate")));
        stock.setSymbol(obj.getString("symbol"));
        // stock.setCompanyName(obj.getString("companyName"));
        stock.setQuantity(obj.getInt("quantity"));
        stock.setStockPrice(Double.parseDouble(obj.getJsonNumber("stockPrice").toString()));
        stock.setTotalPrice(Double.parseDouble(obj.getJsonNumber("totalPrice").toString()));
        stock.setUserId(Integer.parseInt(obj.getString("userId")));
        return stock;
    }

    public static JsonObject toJson(Stock stock, Double totalStockValue){
        return Json.createObjectBuilder()
            .add("stockId", stock.getStockId())
            .add("purchaseDate", stock.getPurchaseDate().toString())
            .add("symbol", stock.getSymbol())
            .add("companyName", stock.getCompanyName())
            .add("quantity", stock.getQuantity())
            .add("stockPrice", stock.getStockPrice())
            .add("totalPrice", stock.getTotalPrice())
            .add("stockValue", totalStockValue)
            .build();
    }

    public static JsonObject toJsonName(Stock stock, String companyName, Double totalStockValue){
        return Json.createObjectBuilder()
            .add("stockId", stock.getStockId())
            .add("purchaseDate", stock.getPurchaseDate().toString())
            .add("symbol", stock.getSymbol())
            .add("companyName", companyName)
            .add("quantity", stock.getQuantity())
            .add("stockPrice", stock.getStockPrice())
            .add("totalPrice", stock.getTotalPrice())
            .add("stockValue", totalStockValue)
            .build();
    }

    public static Stock createFromRowSet(SqlRowSet rs){
        Stock stock = new Stock();
        stock.setStockId(rs.getInt("stock_id"));
        stock.setPurchaseDate(rs.getDate("purchase_date"));
        stock.setSymbol(rs.getString("symbol"));
        stock.setCompanyName(rs.getString("company_name")); // string;
        stock.setQuantity(rs.getInt("quantity")); // int
        stock.setStockPrice(rs.getDouble("stock_price")); // double
        stock.setTotalPrice(rs.getDouble("total_price")); // double
        return stock;
    } 
    
    //withUserId
    public static Stock createWithUserId(JsonObject obj) throws ParseException{

        Stock stock = new Stock();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        stock.setPurchaseDate(formatter.parse(obj.getString("date")));
        stock.setSymbol(obj.getString("symbol"));
        stock.setCompanyName(obj.getString("companyName"));
        stock.setQuantity(obj.getInt("quantity"));
        stock.setStockPrice(Double.parseDouble(obj.getString("stockPrice")));
        stock.setTotalPrice(Double.parseDouble(obj.getString("totalPrice")));
        stock.setUserId(obj.getInt("userId"));
        return stock;
    }

    public static JsonObject toJsonWitUserId(Stock stock, Double totalStockValue){
        return Json.createObjectBuilder()
            .add("purchaseDate", stock.getPurchaseDate().toString())
            .add("symbol", stock.getSymbol())
            .add("companyName", stock.getCompanyName())
            .add("quantity", stock.getQuantity())
            .add("stockPrice", stock.getStockPrice())
            .add("totalPrice", stock.getTotalPrice())
            .add("stockValue", totalStockValue)
            .add("userId", stock.getUserId())
            .build();
    }

    public static Stock delete(JsonObject obj) throws ParseException{

        Stock stock = new Stock();
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        stock.setStockId(obj.getInt("stockId"));
        // stock.setPurchaseDate(formatter.parse(obj.getString("purchaseDate")));
        // stock.setSymbol(obj.getString("symbol"));
        // stock.setCompanyName(obj.getString("companyName"));
        // stock.setQuantity(obj.getInt("quantity"));
        // stock.setStockPrice(Double.parseDouble(obj.getString("stockPrice")));
        // stock.setTotalPrice(Double.parseDouble(obj.getString("totalPrice")));

        return stock;
    }
}
