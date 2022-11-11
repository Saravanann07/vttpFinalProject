import { Component, OnInit } from '@angular/core';
import { SnackbarService } from 'src/app/Service/snackbar.service';

@Component({
  selector: 'app-snackbar',
  templateUrl: './snackbar.component.html',
  styleUrls: ['./snackbar.component.css']
})
export class SnackbarComponent implements OnInit {

  private _message = '';
  set message(msg: string) {
    this._message = msg;
  }
  get message() {
    return this._message;
  }

  private _fontColor = '';
  set fontColor(fc: string) {
    this._fontColor = fc;
  }
  get fontColor() {
    return this._fontColor;
  }

  constructor(private snackBarSvc: SnackbarService) {
    this.message = snackBarSvc.snackbarMsg
    this.fontColor = snackBarSvc.fontColor
   }

  ngOnInit(): void {
  }

}
