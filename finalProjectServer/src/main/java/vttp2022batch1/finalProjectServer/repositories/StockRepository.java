package vttp2022batch1.finalProjectServer.repositories;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022batch1.finalProjectServer.models.Stock;

@Repository
public class StockRepository {

    public static final String SQL_INSERT_TRANSACTION = 
        "INSERT INTO transactions (purchase_date, symbol, company_name, quantity, stock_price, total_price, user_id) values (?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_SELECT_USER_TRANSACTIONS = "select * from transactions where user_id = ?";

    public static final String SQL_CHECK_IF_USER_HAS_MADE_PURCHASE = "select count(*) as purchase_added from transactions WHERE symbol = ? and stock_price = ? and user_id = ?";

    public static final String SQL_GET_USER_COMPANY_TRANSACTIONS = "select * from transactions where symbol = ? and user_id = ? order by purchase_date desc";

    public static final String SQL_SORT_TRANSACTIONS_BY_DATE = "select * from transactions where user_id = ? order by purchase_date desc";

    public static final String SQL_GET_USER_TRANSACTIONS_BY_DATE = "select * from transactions where purchase_date = ? and user_id = ?";
    
    public static final String SQL_DELETE_STOCK_PURCHASE = "delete from transactions where transaction_id = ?";


    @Autowired
    private JdbcTemplate template;

    public int addStockPurchase(Stock stock, Integer userId){

        int count = template.update(SQL_INSERT_TRANSACTION, stock.getPurchaseDate(), stock.getSymbol(), stock.getCompanyName(), stock.getQuantity(), stock.getStockPrice(), stock.getTotalPrice(), userId);
        return count;
    }

    public List<Stock> getUserStockList(Integer userId){

        List<Stock> stockList = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_SORT_TRANSACTIONS_BY_DATE, userId);

        while (rs.next()){
            Stock stock = new Stock();
            stock.setStockId(rs.getInt("transaction_id"));
            stock.setPurchaseDate(rs.getDate("purchase_date"));
            stock.setSymbol(rs.getString("symbol"));
            stock.setCompanyName(rs.getString("company_name")); // string;
            stock.setQuantity(rs.getInt("quantity")); // int
            stock.setStockPrice(rs.getDouble("stock_price")); // double
            stock.setTotalPrice(rs.getDouble("total_price")); // double
            stockList.add(stock);
        }
        return stockList;
    }

    public List<Stock> getCompanyStockList(String symbol, Integer userId){

        List<Stock> stockListByCompany = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_GET_USER_COMPANY_TRANSACTIONS, symbol, userId);

        while (rs.next()){
            Stock stock = new Stock();
            stock.setStockId(rs.getInt("transaction_id"));
            stock.setPurchaseDate(rs.getDate("purchase_date"));
            stock.setSymbol(rs.getString("symbol"));
            stock.setCompanyName(rs.getString("company_name")); // string;
            stock.setQuantity(rs.getInt("quantity")); // int
            stock.setStockPrice(rs.getDouble("stock_price")); // double
            stock.setTotalPrice(rs.getDouble("total_price")); // double
            stockListByCompany.add(stock);
        }
        return stockListByCompany;
    }

    public List<Stock> getUserStocksByDate(Date date, Integer userId){

        List<Stock> stockListByDate = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_GET_USER_TRANSACTIONS_BY_DATE, date, userId);

        while (rs.next()){
            Stock stock = new Stock();
            stock.setStockId(rs.getInt("transaction_id"));
            stock.setPurchaseDate(rs.getDate("purchase_date"));
            stock.setSymbol(rs.getString("symbol"));
            stock.setCompanyName(rs.getString("company_name")); // string;
            stock.setQuantity(rs.getInt("quantity")); // int
            stock.setStockPrice(rs.getDouble("stock_price")); // double
            stock.setTotalPrice(rs.getDouble("total_price")); // double
            stockListByDate.add(stock);
        }
        return stockListByDate;
    }

    public int deleteStock(Stock stock){
        int deleteStock = template.update(SQL_DELETE_STOCK_PURCHASE, stock.getStockId());
        return deleteStock;
    }
}
