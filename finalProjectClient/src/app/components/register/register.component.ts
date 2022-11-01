import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { User, Response } from 'src/app/models/model';
import { UserService } from 'src/app/Service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  onRegistraton = new Subject<User>()

  form!: FormGroup

  constructor(private fb: FormBuilder,
              private userSvc: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.createForm()
  }

  createForm(){
    this.form = this.fb.group({
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      username: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      password: this.fb.control<string>('', [Validators.required, Validators.minLength(3)])
    })
  }

  registration(){
    console.info("Registration button clicked")
    const user: User = this.form.value as User
    console.info('User successfully registered!', user)
    this.userSvc.registration(user)
      .then((data: any) => {
        const resp: Response = data;
        alert(resp.message)
        this.router.navigate([''])
      }).catch((error: any) => {
        const resp: Response = error;
        alert(resp.message)
      })
    
    this.onRegistraton.next(user)
  }

}
