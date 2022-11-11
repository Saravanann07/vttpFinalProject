import { NgModule } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { UserService } from './Service/user.service';
import { StockService } from './Service/stock.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'
import { RouterModule, Routes } from '@angular/router';
import { AddStockComponent } from './components/addStock/add-stock.component';
import { ByCompanyComponent } from './components/byCompany/by-company.component';
import { ByDateComponent } from './components/byDate/by-date.component';
import { HomePageComponent } from './components/homepage/home-page.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { SnackbarComponent } from './components/snackbar/snackbar.component';
import { SnackbarService } from './Service/snackbar.service';



const appRoutes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'register',  component: RegisterComponent },
  { path: 'login',  component: LoginComponent },
  { path: 'homepage/:userId', component: HomePageComponent },
  { path: 'addStock/:userId', component: AddStockComponent},
  { path: 'byDate/:userId', component: ByDateComponent },
  { path: 'byCompany/:userId', component: ByCompanyComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' }

]

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    HomePageComponent,
    ByDateComponent,
    ByCompanyComponent,
    WelcomeComponent,
    AddStockComponent,
    SnackbarComponent
  ],
  imports: [
    BrowserModule, FormsModule, ReactiveFormsModule, 
    HttpClientModule, RouterModule.forRoot(appRoutes, {useHash: true}), 
    MaterialModule, BrowserAnimationsModule
    
  ],
  providers: [UserService, StockService, SnackbarService],
  bootstrap: [AppComponent]
})
export class AppModule { }
