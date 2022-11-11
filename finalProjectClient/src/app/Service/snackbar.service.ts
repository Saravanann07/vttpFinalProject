import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {
  // to display message in the popup
  private _snackbarMsg!: string;
  public get snackbarMsg() {
    return this._snackbarMsg;
  }
  public set snackbarMsg(message: string) {
    this._snackbarMsg = message;
  }
  private _fontColor!: string;
  public get fontColor() {
    return this._fontColor;
  }
  public set fontColor(fc: string) {
    this._fontColor = fc;
  }
  displayMessage(msg: string, fc: string) {
    this.snackbarMsg = msg;
    this.fontColor = fc;
  }

  constructor() { }
}
