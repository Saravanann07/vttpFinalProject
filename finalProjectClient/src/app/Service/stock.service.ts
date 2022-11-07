import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, firstValueFrom } from 'rxjs';
import { Stock } from '../models/model';

// IMPT!!!!
// Remember to replace URLs with heroku URLs before deploying to vercel
// FOR CORS ONLY
// For same origin just include the paths and not the whole URL

// const URL_HOMEPAGE = 'http://localhost:8080/homepage'
const URL_HOMEPAGE = '/homepage'

// const URL_STOCKLIST_BY_DATE = 'http://localhost:8080'
const URL_STOCKLIST_BY_DATE = ''

// const URL_STOCKLIST_BY_SYMBOL = 'http://localhost:8080/company'
const URL_STOCKLIST_BY_SYMBOL = '/company'

// const URL_ADD_STOCK = 'http://localhost:8080/stockAdded'
const URL_ADD_STOCK = '/stockAdded'

// const URL_DELETE_STOCK = 'http://localhost:8080/stockDeleted'
const URL_DELETE_STOCK = '/stockDeleted'



@Injectable({
  providedIn: 'root'
})
export class StockService {

  token!: string 
  headers!: HttpHeaders



  constructor(private http: HttpClient) {

    this.token = localStorage.getItem('token')!
    this.headers =  new HttpHeaders()
                      .set('Authorization', this.token)

    
    
   }

  // getHomepage(): Promise<Stock[]>{
  //   return firstValueFrom(
  //     this.http.get<Stock[]>(URL_HOMEPAGE, { withCredentials: true })
  //   )
  // }

    getHomepage(userId: string): Promise<Stock[]>{

      
      const params = new HttpParams()
        .set("userId", userId)
    return firstValueFrom(
      this.http.get<Stock[]>(URL_HOMEPAGE, { params, headers:this.headers })
    )
  }

  getStocksByDate(userId: string, date: string): Promise<Stock[]>{
    const params = new HttpParams()
    .set("userId", userId)
    return firstValueFrom(
      // this.http.get<Stock[]>(`http://localhost:8080/${date}`, {params, headers:this.headers})
      this.http.get<Stock[]>(`/api/${date}`, {params, headers:this.headers})

    )
  }

  getStocksBySymbol(symbol: string, userId: string): Promise<Stock[]>{
    const params = new HttpParams()
    .set("symbol", symbol)
    .set("userId", userId)


    return firstValueFrom(
      this.http.get<Stock[]>(URL_STOCKLIST_BY_SYMBOL,{ params, headers:this.headers})
    )
  }

  stockAdded(stock: Stock, userId: number): Promise<Response>{

    this.token = localStorage.getItem('token')!

    const params = new HttpParams()
    .set("userId", userId)

    const headers = new HttpHeaders()
                        .set('Content-Type', 'application/json')
                        .set('Accept', 'application/json')
                        .set('Authorization', this.token)
                       

    return firstValueFrom(
      this.http.post<Response>(URL_ADD_STOCK, stock, { params, headers })
    )
  }

  deleteStock(stock: Stock, userId: number): Promise<Response>{

    this.token = localStorage.getItem('token')!

    const params = new HttpParams()
      .set("userId", userId)

    const headers = new HttpHeaders()
                        .set('Content-Type', 'application/json')
                        .set('Accept', 'application/json')
                        .set('Authorization', this.token)

    return firstValueFrom(
      this.http.post<Response>(URL_DELETE_STOCK, stock, { params, headers })
    )
  } 
}



