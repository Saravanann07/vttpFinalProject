import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, firstValueFrom, Subject } from 'rxjs';
import { Stock, User } from '../models/model';

// IMPT!!!!
// Remember to replace URLs with heroku URLs before deploying to vercel
// FOR CORS ONLY
// For same origin just include the paths and not the whole URL

const URL_REGISTRATION = 'http://localhost:8080/registration';

const URL_LOGIN = "http://localhost:8080/authenticate"

@Injectable({
  providedIn: 'root'
})
export class UserService {

  onRegistration = new Subject<User>();
  // onLogin = new Subject<Stock[]>();
  // onLogin = new Subject<string>();
  onLogin = new Subject<User>();



  constructor(private httpClient: HttpClient) { }

  registration(user: User): Promise<Response>{

    const headers = new HttpHeaders()
                      .set('Content-Type', 'application/json')
                      .set('Accept', 'application/json')                  
                      
    return firstValueFrom(
      this.httpClient.post<Response>(URL_REGISTRATION, user, { headers })
    )
  }

  // login(user: User): Promise<Stock[]>{

  //   const headers = new HttpHeaders()
  //   .set('Content-Type', 'application/json')
  //   .set('Accept', 'application/json') 

  //   return firstValueFrom(
  //     this.httpClient.post<Stock[]>(URL_LOGIN, user, { headers })
  //   )


  // }

  // login(user: User): Promise<Response>{

  //   const headers = new HttpHeaders()
  //                         .set('Content-Type', 'application/json')
  //                         .set('Accept', 'application/json') 
  //   return firstValueFrom(
  //       this.httpClient.post<Response>(URL_LOGIN, user, { headers })
  //   )
  // }

  login(user: User): Promise<User>{

    const headers = new HttpHeaders()
                          .set('Content-Type', 'application/json')
                          .set('Accept', 'application/json') 
    return firstValueFrom(
        this.httpClient.post<User>(URL_LOGIN, user, { headers } )
    )
  }

  
}
