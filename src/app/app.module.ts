import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ProductlistComponent } from './productlist/productlist.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ImagecarouselComponent } from './imagecarousel/imagecarousel.component';
import { ContactComponent } from './contact/contact.component';
import { CartComponent } from './cart/cart.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { SignupComponent } from './signup/signup.component';
import { AdmindashboardComponent } from './admindashboard/admindashboard.component';

import {HttpClientModule} from "@angular/common/http";
import { AddproductComponent } from './addproduct/addproduct.component';
import { UpdateComponent } from './update/update.component';
import { FrontpageComponent } from './frontpage/frontpage.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SidebarComponent,
    ProductlistComponent,
    FooterComponent,
    HomeComponent,
    NotFoundComponent,
    ImagecarouselComponent,
    ContactComponent,
    CartComponent,
    LoginComponent,
    SignupComponent,
    AdmindashboardComponent,
    AddproductComponent,
    UpdateComponent,
    FrontpageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
