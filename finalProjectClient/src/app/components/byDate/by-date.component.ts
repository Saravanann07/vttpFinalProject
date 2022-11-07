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
  userId!: string;

  constructor(private stockSvc: StockService,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.date = this.activatedRoute.snapshot.queryParams['date']
    this.userId = this.activatedRoute.snapshot.params['userId']

    this.stockSvc.getStocksByDate(this.userId, this.date)
      .then(result => {
        console.info('>>>>>>Stock List by Date', result)
        this.stockListDate = result
      })
      .catch(error => {
        console.error('>>>>> error: ', error)
      })   
  }

  deleteStock(stock: Stock){
    this.userId = localStorage.getItem('userId')!
    this.stockSvc.deleteStock(stock, stock.userId)
    .then((data: any) => {
      const resp: Response = data;
      alert(resp.message);  
      console.info(this.date)      
      this.router.navigate(['/homepage', this.userId])
      this.retrieveStockList()
    }).catch((error: any) => {
      const resp: Response = error;
      alert(resp.message)
    })
  }

  private retrieveStockList(){
    this.stockSvc.getStocksByDate(this.stock.purchaseDate, this.userId)
      .then(data => {
        this.stockListDate = data
        this.router.navigate(['/homepage', this.userId])
      }).catch(error =>{
        console.info('>>>>>. Error!')
      })
  }

  userLogout(){
    localStorage.removeItem('token')
    localStorage.removeItem('userId')     
    localStorage.removeItem('username')
    this.router.navigate([''])
}

}
