import { Component,OnInit } from '@angular/core';
import{CartService} from '../cart.service';
import {Subscription} from 'rxjs';
import {HttpResponse} from '@angular/common/http';
import {AuthenticationService} from '../authentication/authentication.service';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit
  {
      items=this.cartService.getItems();
      username:string='';
      quantity:Map<String,number>=new Map();
      
      constructor(private cartService:CartService,private authenticationService:AuthenticationService ){}
      ngOnInit():void
        {
          console.log(this.cartService.getItems());
          this.username=this.authenticationService.getUsername();
          for(let item of this.items )
            {
              this.quantity.set(item.name,1);
            }
        }
      getValue(key:string)
        {
          let value=this.quantity.get(key);
          console.log("getValue : "+value);
          return value;
        }
      confirmOrder()
        {
          this.cartService.storeOrderDetails().subscribe(
            (response:HttpResponse<any>=>
             {
               //console.log("cartService Response:"+response+" "+JSON.stringify(response));
             }
            )
        }
  }
