import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

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

const appRoutes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'homepage/:username', component: HomePageComponent },
  { path: 'addStock', component: AddStockComponent},
  { path: 'byDate', component: ByDateComponent },
  { path: 'byCompany', component: ByCompanyComponent },
  { path: '', redirectTo: '', pathMatch: 'full' }

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
    AddStockComponent
  ],
  imports: [
    BrowserModule, FormsModule, ReactiveFormsModule, HttpClientModule, RouterModule.forRoot(appRoutes, {useHash: true})
  ],
  providers: [UserService, StockService],
  bootstrap: [AppComponent]
})
export class AppModule { }
