import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Stock, Response } from 'src/app/models/model';
import { SnackbarService } from 'src/app/Service/snackbar.service';
import { StockService } from 'src/app/Service/stock.service';
import { SnackbarComponent } from '../snackbar/snackbar.component';

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
  imgSrc!: string 
  username!: string 




  constructor(private stockSvc: StockService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private snackBar: MatSnackBar,
              private snackBarSvc: SnackbarService) { }

  ngOnInit(): void {
    this.userId = this.activatedRoute.snapshot.params['userId']
    this.symbol = this.activatedRoute.snapshot.queryParams['symbol']
    console.info(this.userId)
    console.info(this.symbol)
    this.username = localStorage.getItem('username')!
    this.imgSrc = `https://saravttp.sgp1.digitaloceanspaces.com/users/${this.username}`;

    
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
      this.snackBarSvc.displayMessage('DELETE SUCCESS', 'yellowgreen');
      this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'top'})       
      console.info(stock.symbol)      
      this.router.navigate(['/byCompany', this.userId], {queryParams: {symbol: stock.symbol}})
      this.retrieveStockList()
    }).catch((error: any) => {
      const resp: Response = error;
      this.snackBarSvc.displayMessage('DELETE ERROR', 'red');
      this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'top'})
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
    this.snackBarSvc.displayMessage('LOGOUT SUCCESSFUL', 'yellowgreen');
    this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'top'})  
}

  

}
