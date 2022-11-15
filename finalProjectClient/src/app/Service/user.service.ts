import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { firstValueFrom, map, Subject } from 'rxjs';
import { SnackbarComponent } from '../components/snackbar/snackbar.component';
import { jwtResponse, Response, User } from '../models/model';
import { SnackbarService } from './snackbar.service';

// IMPT!!!!
// Remember to replace URLs with heroku URLs before deploying to vercel
// FOR CORS ONLY
// For same origin just include the paths and not the whole URL

// const URL_REGISTRATION = 'http://localhost:8080/registration';
const URL_REGISTRATION = '/registration';


// const URL_LOGIN = "http://localhost:8080/authenticate";
const URL_LOGIN = "/authenticate";


// const URL_LOGOUT = "http://localhost:8080/logout";
const URL_LOGOUT = "/logout";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  onRegistration = new Subject<User>();
  // onLogin = new Subject<Stock[]>();
  // onLogin = new Subject<string>();
  onLogin = new Subject<User>();

  token!: string
  headers!: HttpHeaders



  constructor(private httpClient: HttpClient,
              private snackBar: MatSnackBar,
              private snackBarSvc: SnackbarService ) { }

  registration(formData: FormData): Promise<Response>{

             
                      
    return firstValueFrom(
      this.httpClient.post<Response>(URL_REGISTRATION, formData)
    )
  }

 
  login(user: User): Promise<jwtResponse>{

    const headers = new HttpHeaders()
                          .set('Content-Type', 'application/json')
                          .set('Accept', 'application/json') 
    return firstValueFrom(
        this.httpClient.post<jwtResponse>(URL_LOGIN, user, { headers } ).pipe(map(result => {
          console.log(result)
          localStorage.setItem('token', result.jwt)
          localStorage.setItem('userId', result.userId.toString())
          localStorage.setItem('username', result.username)
          return result
        })
        )
    )
  }

  logout(): Promise<Response>{

    
    return firstValueFrom(
      this.httpClient.get<Response>(URL_LOGOUT)
    )
  }



  
}
