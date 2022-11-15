import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { Stock, Response } from 'src/app/models/model';
import { SnackbarService } from 'src/app/Service/snackbar.service';
import { StockService } from 'src/app/Service/stock.service';
import { SnackbarComponent } from '../snackbar/snackbar.component';

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
  username!: string 

  imgSrc!: string 

  constructor(private stockSvc: StockService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private snackBar: MatSnackBar,
              private snackBarSvc: SnackbarService) { }

  ngOnInit(): void {

    this.userId = this.activatedRoute.snapshot.params['userId']

    this.username = localStorage.getItem('username')!
    console.info(this.username)
    

    this.imgSrc = `https://saravttp.sgp1.digitaloceanspaces.com/users/${this.username}`;
    console.log(this.imgSrc)
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
      this.snackBarSvc.displayMessage('DELETE SUCCESS', 'yellowgreen');
      this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'top'})   
      console.info(this.date)      
      this.router.navigate(['/byDate', this.userId], {queryParams: {date: stock.purchaseDate}})
      this.retrieveStockList()
    }).catch((error: any) => {
      const resp: Response = error;
      this.snackBarSvc.displayMessage('DELETE ERROR', 'red');
      this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'top'})
    })
  }

  private retrieveStockList(){
    this.date = this.activatedRoute.snapshot.queryParams['date']
    console.info('*******, '+this.date)
    this.stockSvc.getStocksByDate(this.userId, this.date)
      .then(data => {
        this.stockListDate = data
        this.router.navigate(['/byDate', this.userId], {queryParams: {date: this.date}})
      }).catch(error =>{
        console.info('>>>>>. Error!')
      })
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
