import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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

  photo!:File

  fileFormat!: string

  fileError!: boolean


  

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
      password: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      profilePic: [null] // value inserted during userInputPhoto()
    })
  }



  registration(){
    console.info("Registration button clicked")
    const user: User = this.form.value as User
    console.info('User successfully registered!', user)
   

    // for file upload
    const formData = new FormData();
    // iterates through your form control names, and append them to the formdata (K,V)
    Object.keys(this.form.controls).forEach(formControlName => {
      formData.append(formControlName, this.form.get(formControlName)!.value)
    })

    this.userSvc.registration(formData) // change parameter to FormData
    .then((data: any) => {
      console.info('User successfully registered!', formData)
      const resp: Response = data;
      alert(resp.message)
      this.router.navigate([''])
    }).catch((error: any) => {
      const resp: Response = error;
      alert(resp.message)
    })
    // this.userSvc.registration(formData)
  }

  userInputPhoto(event: any) {

    console.log(event.target.files[0])
    this.photo = event.target.files[0]
    console.log(this.photo)
    this.fileFormat = this.photo.type
    console.info(this.fileFormat)

    this.fileError = (this.fileFormat !== 'image/jpeg')
    console.log(this.fileError)

    
    
    // <input type="file" (change)="userInputPhoto($event)">
    // check if file type is image, or filesize < 1mb
    // disable submit button
    // the uploaded image is valid

    const blankFile: File = new File([], '');
    if (this.fileError){
      alert("File is in wrong format!. Please only upload JPEG files!")
      this.form.controls['profilePic'].setValue(this.photo, {emitEvent: false});
    }
    else{
      // this.fileError = false
      console.log(this.fileError)
    console.info('>>>> Correct format')
    this.form.controls['profilePic'].setValue(this.photo) // File itself
    }
  }
}
