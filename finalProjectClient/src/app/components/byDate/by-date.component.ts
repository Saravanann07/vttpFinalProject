import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Stock, Response } from 'src/app/models/model';
import { StockService } from 'src/app/Service/stock.service';

@Component({
  selector: 'app-by-date',
  templateUrl: './by-date.component.html',
  styleUrls: ['./by-date.component.css']
})
export class ByDateComponent implements OnInit {

  stockListDate!: Stock[];
  stock!: Stock
  date!: string

  constructor(private stockSvc: StockService,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.date = this.activatedRoute.snapshot.params['date']
    this.stockSvc.getStocksByDate(this.date)
      .then(result => {
        console.info('>>>>>>Stock List by Date', result)
        this.stockListDate = result
      })
      .catch(error => {
        console.error('>>>>> error: ', error)
      })   
  }

  deleteStock(stock: Stock){
    this.stockSvc.deleteStock(stock)
    .then((data: any) => {
      const resp: Response = data;
      alert(resp.message);        
      this.router.navigate(['/byDate'])
      this.retrieveStockList()
    }).catch((error: any) => {
      const resp: Response = error;
      alert(resp.message)
    })
  }

  private retrieveStockList(){
    this.stockSvc.getStocksByDate(this.stock.purchaseDate)
      .then(data => {
        this.stockListDate = data
        this.router.navigate(['/byDate'])
      }).catch(error =>{
        console.info('>>>>>. Error!')
      })
  }

}
