import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { Stock, Response } from 'src/app/models/model';
import { SnackbarService } from 'src/app/Service/snackbar.service';
import { StockService } from 'src/app/Service/stock.service';
import { SnackbarComponent } from '../snackbar/snackbar.component';

@Component({
  selector: 'app-add-stock',
  templateUrl: './add-stock.component.html',
  styleUrls: ['./add-stock.component.css']
})
export class AddStockComponent implements OnInit {

  onStockAdded = new Subject<Stock>()

  totalPrice!: number

  userId!: string;

  stockUserId!: number

  username!: string 

  imgSrc!: string 

  logo: string = 'https://saravttp.sgp1.digitaloceanspaces.com/logo/stock-trading-app%20%281%29.png'


 


  form!: FormGroup

  constructor(private fb: FormBuilder,
              private stocKSvc: StockService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private snackBar: MatSnackBar,
              private snackBarSvc: SnackbarService
              ) { }

  ngOnInit(): void {

    this.username = localStorage.getItem('username')!
    console.info(this.username)
    this.imgSrc = `https://saravttp.sgp1.digitaloceanspaces.com/users/${this.username}`;
    this.createForm()
    this.userId = localStorage.getItem('userId')!;
  
  }

  createForm(){
    this.form = this.fb.group({
      purchaseDate: this.fb.control<string>('', [Validators.required]),
      symbol: this.fb.control<string>('', [Validators.required]),
      // companyName: this.fb.control<string>('', [Validators.required]),
      quantity: this.fb.control<number>(0, Validators.required),
      stockPrice: this.fb.control<number>(0, [Validators.required]),
      totalPrice: this.fb.control<number>(0, Validators.required),
      userId: this.fb.control<string>(localStorage.getItem('userId')!)
    })
  }

  stockAdded(){
    console.log(this.userId)
    const totalPrice = this.form.controls['quantity'].value * this.form.controls['stockPrice'].value
    this.form.controls['totalPrice'].setValue(totalPrice);
    console.info("Add Stock button clicked", this.form.controls['totalPrice'].value)
    const stock: Stock = this.form.value as Stock
    console.info(stock)
    console.info(stock.userId)
    this.stocKSvc.stockAdded(stock, stock.userId)
    .then((data: any) => {
      const resp: Response = data;
      this.snackBarSvc.displayMessage('SUCCESSFULLY ADDED', 'yellowgreen');
      this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'top'})      
      this.createForm()
    }).catch((error: any) => {
      const resp: Response = error;
      this.snackBarSvc.displayMessage('STOCK CANNOT BE ADDED', 'red');
      this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'top'})
    })
  
  this.onStockAdded.next(stock)
  }

  goHomePage(userId: string){
    this.router.navigate(['/homepage', this.userId])
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
