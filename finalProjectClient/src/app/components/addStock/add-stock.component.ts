import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { Stock, Response } from 'src/app/models/model';
import { StockService } from 'src/app/Service/stock.service';

@Component({
  selector: 'app-add-stock',
  templateUrl: './add-stock.component.html',
  styleUrls: ['./add-stock.component.css']
})
export class AddStockComponent implements OnInit {

  onStockAdded = new Subject<Stock>()

  totalPrice!: number

  form!: FormGroup

  constructor(private fb: FormBuilder,
              private stocKSvc: StockService,
              private router: Router) { }

  ngOnInit(): void {
    this.createForm()
  }

  createForm(){
    this.form = this.fb.group({
      purchaseDate: this.fb.control<string>('', [Validators.required]),
      symbol: this.fb.control<string>('', [Validators.required]),
      companyName: this.fb.control<string>('', [Validators.required]),
      quantity: this.fb.control<number>(0, Validators.required),
      stockPrice: this.fb.control<number>(0, [Validators.required]),
      totalPrice: this.fb.control<number>(0, Validators.required)
    })
  }

  stockAdded(){
    console.info("Add Stock button clicked")
    const stock: Stock = this.form.value as Stock
    this.stocKSvc.stockAdded(stock)
    .then((data: any) => {
      const resp: Response = data;
      alert(resp.message)
      this.router.navigate(['/homepage'])
    }).catch((error: any) => {
      const resp: Response = error;
      alert(resp.message)
    })
  
  this.onStockAdded.next(stock)
  }

}
