import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpService } from '../http.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-productlist',
  templateUrl: './productlist.component.html',
  styleUrls: ['./productlist.component.css']
})
export class ProductlistComponent implements OnInit,OnDestroy{

  products:any[] = [];
  role!:string|null;

  constructor(private service:HttpService,
   private router:Router
  ){}
  ngOnDestroy(): void {
    this.role = null;
  }
  ngOnInit(): void {
    this.getDataFromBackend();
    this.role = sessionStorage.getItem("role");
  }

  getDataFromBackend(){
    this.service.getProductData()
    .subscribe((response:any) =>{
      console.log(response);
      this.products = response;
      
    })
  }


  onUpdate(id:any){
    this.router.navigate(['/updateProduct',id]);
  }
  onDelete(id:any){
    this.service.deleteData(id)
    .subscribe((response)=>{
      console.log(response);
      this.getDataFromBackend()
      
    });
  }

  onAddToCart(item:any){

  }

}
