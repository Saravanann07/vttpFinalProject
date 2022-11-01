import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, firstValueFrom } from 'rxjs';
import { Stock } from '../models/model';

// IMPT!!!!
// Remember to replace URLs with heroku URLs before deploying to vercel
// FOR CORS ONLY
// For same origin just include the paths and not the whole URL

const URL_HOMEPAGE = 'http://localhost:8080/homepage'
const URL_STOCKLIST_BY_DATE = 'http://localhost:8080'
const URL_STOCKLIST_BY_SYMBOL = 'http://localhost:8080/company'
const URL_ADD_STOCK = 'http://localhost:8080/stockAdded'
const URL_DELETE_STOCK = 'http://localhost:8080/stockDeleted'


@Injectable({
  providedIn: 'root'
})
export class StockService {


  constructor(private http: HttpClient) {
    
   }

  // getHomepage(): Promise<Stock[]>{
  //   return firstValueFrom(
  //     this.http.get<Stock[]>(URL_HOMEPAGE, { withCredentials: true })
  //   )
  // }

    getHomepage(username: string): Promise<Stock[]>{
      const params = new HttpParams()
        .set("username", username)
    return firstValueFrom(
      this.http.get<Stock[]>(URL_HOMEPAGE, { params })
    )
  }

  getStocksByDate(date: string): Promise<Stock[]>{
    return firstValueFrom(
      this.http.get<Stock[]>(`http://localhost:8080/${date}`)
    )
  }

  getStocksBySymbol(symbol: string): Promise<Stock[]>{
    const params = new HttpParams()
    .set("symbol", symbol)

    return firstValueFrom(
      this.http.get<Stock[]>('/company', { params })
    )
  }

  stockAdded(stock: Stock): Promise<Response>{

    const headers = new HttpHeaders()
                        .set('Content-Type', 'application/json')
                        .set('Accept', 'application/json')

    return firstValueFrom(
      this.http.post<Response>(URL_HOMEPAGE, stock, { headers })
    )
  }

  deleteStock(stock: Stock): Promise<Response>{

    const headers = new HttpHeaders()
                        .set('Content-Type', 'application/json')
                        .set('Accept', 'application/json')

    return firstValueFrom(
      this.http.post<Response>(URL_DELETE_STOCK, stock, { headers })
    )
  } 
}



