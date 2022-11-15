package vttp2022batch1.finalProjectServer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022batch1.finalProjectServer.models.Stock;
import vttp2022batch1.finalProjectServer.repositories.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepo;


    public boolean addStockPurchase(Stock stock){

        Integer addStock = stockRepo.addStockPurchase(stock);

        return addStock == 1;
    }

    public List<Stock> getUserStockList(Integer userId){
        return stockRepo.getUserStockList(userId);
    }

    public List<Stock> getUserStockListByCompany(String symbol, Integer userId){
        return stockRepo.getCompanyStockList(symbol, userId);
    }


    public List<Stock> getUserStockListByDate(String date, Integer userId){
        return stockRepo.getUserStocksByDate(date, userId);
    }
    

    public boolean deleteStock (Stock stock){
        int deleteStock = stockRepo.deleteStock(stock);
        return deleteStock == 1;
    }
    
}
