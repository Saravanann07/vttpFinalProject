import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { Response, Stock } from 'src/app/models/model';
import { SnackbarService } from 'src/app/Service/snackbar.service';
import { StockService } from 'src/app/Service/stock.service';
import { UserService } from 'src/app/Service/user.service';
import { SnackbarComponent } from '../snackbar/snackbar.component';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  
  onSearch = new Subject<Stock>()

  stockList!: Stock[];

  userId!: string;

  username!: string 

  imgSrc!: string 
  

  form!: FormGroup

  dateForm!:FormGroup

  constructor(private stockSvc: StockService, 
              private userSvc: UserService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private fb: FormBuilder,
              private snackBar: MatSnackBar,
              private snackBarSvc: SnackbarService
              ) { }

  ngOnInit(): void {


    this.userId = this.activatedRoute.snapshot.params['userId']

    this.username = localStorage.getItem('username')!
    console.info(this.username)
    

    this.imgSrc = `https://saravttp.sgp1.digitaloceanspaces.com/users/${this.username}`;
    console.log(this.imgSrc)
   
    
    this.stockSvc.getHomepage(this.userId)
      .then(data => {
        this.stockList = data
        console.info(">>>>>>StockList retreived: ", data)
      }).catch(error =>{
        alert('Error occurred while retreiving stock list!')
      })
      this.createForm()
      this.byDateSearch()
  }

  userLogout(){
    localStorage.removeItem('token')
    localStorage.removeItem('userId')     
    localStorage.removeItem('username')
    this.router.navigate([''])
    this.snackBarSvc.displayMessage('LOGOUT_SUCCESSFUL', 'greenyellow');
    this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'bottom'})
}

  createForm(){
    this.form = this.fb.group({
      symbol: this.fb.control<string>('', [Validators.required])
    })
  }

  bySymbol(){
    console.info("Search button clicked")
    const stock: Stock = this.form.value as Stock
    this.router.navigate(['/byCompany', this.userId], {queryParams: {symbol: stock.symbol}})
  }

  byDateSearch(){
    this.dateForm = this.fb.group({
      purchaseDate: this.fb.control<string>('', [Validators.required])
    })
    
  }

  byDate(){
    console.info("Date button clicked")
    const stock: Stock = this.dateForm.value as Stock
    this.router.navigate(['/byDate', this.userId], {queryParams: {date: stock.purchaseDate}})
  }



  deleteStock(stock: Stock){
    this.userId = localStorage.getItem('userId')!
    console.info(stock.stockId)
    this.stockSvc.deleteStock(stock, stock.userId)
    .then((data: any) => {
      const resp: Response = data;
      alert(resp.message);        
      this.router.navigate(['/homepage', this.userId])
      this.retrieveStockList()
    }).catch((error: any) => {
      const resp: Response = error;
      alert(resp.message)
    })
  }

  private retrieveStockList(){
    this.stockSvc.getHomepage(this.userId)
      .then(data => {
        this.stockList = data
        this.router.navigate(['/homepage', this.userId])
      }).catch(error =>{
        console.info('>>>>> Error!')
      })
  }

}
