import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from '../http.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private router: Router,
    private service:HttpService
  ){}

  EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
  + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


  // ngOnInit(): void {
  //   document.body.style.backgroundColor="white";
  // }

   onSubmit(form: NgForm) {
      if (form.valid) {
        console.log('Login Submitted:', form.value);
        // Proceed with backend API call here
        this.service.login(form.value)
        .subscribe((response:any)=>{
          console.log(response);

          if(response != null){

            sessionStorage.setItem("uid",response.uid)
            sessionStorage.setItem("role",response.role)
            sessionStorage.setItem("username",response.username)
              

            if(response.role === 'SELLER'){
              // navigate to admin dashboard

              this.router.navigate(['/admin-dashboard'])
            }else if(response.role === 'BUYER'){
              // navigate to home dashboard
              this.router.navigate(["/home"])
            }
          }else{
            alert("Invalid user Please check your username and password")
          }

          
          
        })
      } else {
        console.log('Form is invalid!');
      }
    }

}
