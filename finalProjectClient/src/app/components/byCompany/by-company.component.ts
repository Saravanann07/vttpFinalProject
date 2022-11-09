import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  userId!: string;
  symbol!: string;


  constructor(private stockSvc: StockService,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.userId = this.activatedRoute.snapshot.params['userId']
    this.symbol = this.activatedRoute.snapshot.queryParams['symbol']
    console.info(this.userId)
    console.info(this.symbol)
    
    this.stockSvc.getStocksBySymbol(this.symbol, this.userId)
      .then(data => {
        this.stockListSymbol = data
        console.info(">>>>>>StockList retreived: ", data)
      }).catch(error =>{
        alert('Error occurred while retreiving stock list!')
      })  
  }

  deleteStock(stock: Stock){
    this.userId = localStorage.getItem('userId')!
    this.stockSvc.deleteStock(stock, stock.userId)
    .then((data: any) => {
      const resp: Response = data;
      alert(resp.message);  
      console.info(stock.symbol)      
      this.router.navigate(['/byCompany', this.userId], {queryParams: {symbol: stock.symbol}})
      this.retrieveStockList()
    }).catch((error: any) => {
      const resp: Response = error;
      alert(resp.message)
    })
  }

  private retrieveStockList(){
    this.symbol = this.activatedRoute.snapshot.queryParams['symbol']
    console.info("^^^^^"+this.symbol)
    this.stockSvc.getStocksBySymbol(this.symbol, this.userId)
      .then(data => {
        this.stockListSymbol = data
        this.router.navigate(['/byCompany', this.userId], {queryParams: {symbol: this.symbol}})
      }).catch(error =>{
        console.info('>>>>> Error!')
      })
  }
  goHomePage(){
    this.router.navigate(['/homepage', localStorage.getItem('userId')])
  }

  userLogout(){
    localStorage.removeItem('token')
    localStorage.removeItem('userId')     
    localStorage.removeItem('username')
    this.router.navigate([''])
}

  

}
