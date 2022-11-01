import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { skip, Subject } from 'rxjs';
import { User, Response } from 'src/app/models/model';
import { UserService } from 'src/app/Service/user.service';

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
              private router: Router) { }

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
        alert(resp.message)
        this.router.navigate(['/homepage', user.username])
      }).catch((error: any) => {
        const resp: Response = error;
        alert(resp.message)
      })
      this.onLogin.next(user)
  }


}
