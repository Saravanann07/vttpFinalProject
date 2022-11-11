import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { skip, Subject } from 'rxjs';
import { User, Response } from 'src/app/models/model';
import { SnackbarService } from 'src/app/Service/snackbar.service';
import { UserService } from 'src/app/Service/user.service';
import { SnackbarComponent } from '../snackbar/snackbar.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  onLogin = new Subject<User>()


  form!: FormGroup

  constructor(private fb: FormBuilder,
              private userSvc: UserService,
              private router: Router,
              private snackBar: MatSnackBar,
              private snackBarSvc: SnackbarService,
              private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {

    this.createForm()
  }

  createForm(){
    this.form = this.fb.group({
      username: this.fb.control<string>('', [ Validators.required]),
      password: this.fb.control<string>('', [Validators.required])
    })
  }

  login(){
    console.info("Login button clicked")
    const user: User = this.form.value as User
    this.userSvc.login(user)
      .then ((data: any) => {
        const resp: Response = data;
        this.snackBarSvc.displayMessage('LOGIN_SUCCESSFUL', 'green');
        this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'bottom'})
        .afterDismissed()
        // alert('Login Successful!')
        this.router.navigate(['/homepage', localStorage.getItem('userId')])
      }).catch((error: any) => {
        const resp: Response = error;
        this.snackBarSvc.displayMessage('INVALID_CREDENTIALS', 'red')
        this.snackBar.openFromComponent(SnackbarComponent, {duration: 3000, verticalPosition: 'bottom'});
        // alert('Error Logging in. Please try again')
      })
     
      this.onLogin.next(user)
  }


}
