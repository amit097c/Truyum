import { Injectable } from '@angular/core';
import {MenuItem} from './model/MenuItem';
import {HttpClient,HttpResponse,HttpHeaders} from '@angular/common/http';
import {environment} from '../environments/environment';
import{Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService
  {
    constructor(private http:HttpClient) { }
    private host=environment.apiUrl;
    items:MenuItem[]=[];
    addToCart(menuItem:MenuItem)
       {

         this.items.push(menuItem);
         console.log('added: '+menuItem);
       }
     getItems()
       {
         return this.items;
       }
     clearCart()
       {
         this.items=[];
         return this.items;
       }
     storeOrderDetails():Observable<HttpResponse<number>>
     {
       console.log("sending order details: "+this.items);
       const headers = new HttpHeaders().set('Content-Type', 'application/json');
       return this.http.post<number>(`${this.host}/order`,JSON.stringify(this.items),{observe:'response'});
     }
  }
