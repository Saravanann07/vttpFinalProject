import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  logo: string = 'https://saravttp.sgp1.digitaloceanspaces.com/logo/stock-trading-app.png'

  constructor() { }

  ngOnInit(): void {
  }

}
