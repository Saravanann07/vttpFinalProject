import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Stock, Response } from 'src/app/models/model';
import { StockService } from 'src/app/Service/stock.service';

@Component({
  selector: 'app-by-company',
  templateUrl: './by-company.component.html',
  styleUrls: ['./by-company.component.css']
})
export class ByCompanyComponent implements OnInit {

  stockListSymbol!: Stock[];
  stock!: Stock

  constructor(private stockSvc: StockService,
              private router: Router) { }

  ngOnInit(): void {
    this.stockSvc.getStocksBySymbol(this.stock.symbol)
      .then(data => {
        this.stockListSymbol = data
        console.info(">>>>>>StockList retreived: ", this.stockListSymbol)
      }).catch(error =>{
        alert('Error occurred while retreiving stock list!')
      })  
  }

  deleteStock(stock: Stock){
    this.stockSvc.deleteStock(stock)
    .then((data: any) => {
      const resp: Response = data;
      alert(resp.message);        
      this.router.navigate(['/byCompany'])
      this.retrieveStockList()
    }).catch((error: any) => {
      const resp: Response = error;
      alert(resp.message)
    })
  }

  private retrieveStockList(){
    this.stockSvc.getStocksBySymbol(this.stock.symbol)
      .then(data => {
        this.stockListSymbol = data
        this.router.navigate(['/byCompany'])
      }).catch(error =>{
        console.info('>>>>> Error!')
      })
  }

}
