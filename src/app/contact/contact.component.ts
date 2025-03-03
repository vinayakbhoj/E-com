import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {

  constructor(private router: Router){}
  OnLink(){
    // this.router.navigate(['/'], { queryParams: { url: 'https://google.com' } });
    window.location.href = 'http://www.linkedin.com/in/vinayak-bhoj31';
  }

  OnGit(){
    window.location.href = 'https://github.com/vinayakbhoj';
  }
  OnInsta(){
    window.location.href = 'https://www.instagram.com/vinayak_bhoj_31/';
  }
  OnTele(){
    window.location.href = 'https://t.me/StrawHatPiiirates';
  }
}
