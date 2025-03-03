import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from '../http.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  // constructor(private router:Router){}

  // ngOnInit(): void {
  //   document.body.style.backgroundColor="white";
  // }

  EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
  + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  constructor(private service:HttpService,
    private router:Router
  ){}

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Form Submitted:', form.value);

      // Proceed with backend API call here
      this.service.signupUser(form.value)
      .subscribe((response) => {
        console.log(response);
        this.router.navigate(['/login'])
      })
    } else {
      console.log('Form is invalid!');
    }
  }
}
