import { Component,OnInit} from '@angular/core';
import {environment} from '../../environments/environment';
import { Observable} from 'rxjs';
import{HttpClient,HttpErrorResponse,HttpResponse} from '@angular/common/http';
import{MenuItem} from '../model/MenuItem';
import {CartService} from '../cart.service';
import {EditService} from '../edit.service';
@Component({
  selector: 'app-menu-items',
  templateUrl: './menu-items.component.html',
  styleUrls: ['./menu-items.component.css']
})
export class MenuItemsComponent implements OnInit
  {
    //host:string='';
    constructor(private http:HttpClient,private cartService:CartService,private editService:EditService){}
    public host=environment.apiUrl;
    public menuItemList: MenuItem[]=[];
    public  data1:string='tempDate';
    ngOnInit():void
       {
        this.host=environment.apiUrl;
        this.http.get<string>(`${this.host}/MenuItems`,{observe:'response'}).subscribe
         (
           (menuItems:HttpResponse<string>)=>
              {
                 var data = JSON.stringify(menuItems.body);
                 this.data1=data;
                 var menuItemArray=JSON.parse(data);
                 let index=0;
                 menuItemArray.forEach((item:MenuItem)=>
                   {
                     //console.log(item);
                     this.menuItemList[index++]=item;
                   })


                   /*
                  console.log("menuItemList: "+this.menuItemList);
                  this.menuItemList.forEach((item:MenuItem)=>
                   {
                     console.log("list item's name: "+item.name);
                   })
                  */
                   
               /*console.log("response : "+menuItems);
                 const result= [...[menuItems.body]];
                 console.log("unpacking response: "+result);
                 console.log("respone length: "+result.length);
                 console.log("first element: "+result[0]);
                 console.log("typeof result: "+typeof result);
                 console.log("typeof result[0]: "+typeof result[0]);
                 var data = JSON.stringify(menuItems.body);
                 console.log("Stringified the data: "+data);
                 var obj=JSON.parse(data);
                 console.log("parsed data: "+obj);
                 console.log("parsed data[0]: "+obj[0].name); 
               */

              },
           //console.log(typeof menuItems.body);
           /*for(let mi in menuItems.body)
                  {
                    console.log(menuItems.body.getName());
                    //console.log(menuItems.body.getId());
                  }
             */  
                 
               
           (err:HttpErrorResponse)=>
              {
                console.log(err);
              }
          )
 
       }
    //get menuitems from the db.

    
    public showMenu()
      {
        //Observable <HttpResponse<string>> observer=
        this.http.get<string>(`${this.host}/MenuItems`,{observe:'response'}).subscribe
          (
            (menuItems:HttpResponse<string>)=>
             {
               console.log(menuItems);
             },
            (err:HttpErrorResponse)=>
             {
               console.log(err);
             } 
          )

      }
    public addToCart(menuItem:MenuItem)
      {
        this.cartService.addToCart(menuItem);
        console.log("menu-item : "+menuItem);
        window.alert('Your product has been added to the cart'+menuItem);
      }
    public onSaveItem(item:MenuItem)
      {
        this.editService.setItem(item);
      }
    
  }
