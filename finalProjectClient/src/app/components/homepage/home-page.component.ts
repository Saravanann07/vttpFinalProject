import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { skip, Subject } from 'rxjs';
import { Stock, Response } from 'src/app/models/model';
import { StockService } from 'src/app/Service/stock.service';
import { UserService } from 'src/app/Service/user.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  
  onSearch = new Subject<Stock>()

  stockList!: Stock[];

  username!: string;

  form!: FormGroup

  constructor(private stockSvc: StockService, 
              private userSvc: UserService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private fb: FormBuilder) { }

  ngOnInit(): void {

    this.username = this.activatedRoute.snapshot.params['username']
   
    
    this.stockSvc.getHomepage(this.username)
      .then(data => {
        this.stockList = data
        console.info(">>>>>>StockList retreived: ", data)
      }).catch(error =>{
        alert('Error occurred while retreiving stock list!')
      })
      this.createForm()
  }

  createForm(){
    this.fb.group({
      symbol: this.fb.control<string>('', [Validators.required])
    })
  }

  bySymbol(){
    console.info("Search button clicked")
    const stock: Stock = this.form.value as Stock
    this.stockSvc.getStocksBySymbol(stock.symbol)
      .then(result => {
        console.info('>>>>> stockList: ', this.stockList)
        this.stockList = result
      }).catch( error => {
        console.error('>>>>> error ', error)
      })
      this.onSearch.next(stock)
  }

  deleteStock(stock: Stock){
    this.stockSvc.deleteStock(stock)
    .then((data: any) => {
      const resp: Response = data;
      alert(resp.message);        
      this.router.navigate(['/homepage'])
      this.retrieveStockList()
    }).catch((error: any) => {
      const resp: Response = error;
      alert(resp.message)
    })
  }

  private retrieveStockList(){
    this.stockSvc.getHomepage(this.username)
      .then(data => {
        this.stockList = data
        this.router.navigate(['/homepage'])
      }).catch(error =>{
        console.info('>>>>> Error!')
      })
  }

}
