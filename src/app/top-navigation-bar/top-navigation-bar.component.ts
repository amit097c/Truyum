import { Component,OnInit,OnDestroy,Input,Injectable,Output} from '@angular/core';
import {AuthenticationService} from '../authentication/authentication.service';
import{Subscription,Subject,BehaviorSubject,Observable} from 'rxjs';

@Component({
  selector: 'app-top-navigation-bar',
  templateUrl: './top-navigation-bar.component.html',
  styleUrls: ['./top-navigation-bar.component.css']
})


export class TopNavigationBarComponent implements OnInit,OnDestroy
  {
    tokenExpirationTimer:any=null;
    isAuthenticated: boolean=false;

    private userSub:Subscription=new Subscription();
    constructor(private authenticationService:AuthenticationService){}
    ngOnInit()
      {
        console.log("TopNavigationBarComponent  called");
        this.userSub=this.authenticationService.userAuth.subscribe(user=>
         {
           console.log("user: "+user);                            //!user?false:true;
           this.isAuthenticated=!!user;//!user->true(user not present)---->!!user->!(true)=false  
          })
        /*this.tokenExpirationTimer=setTimeout(()=>{
        this.isAuthenticated=false;
        console.log("isAuthenticated: "+this.isAuthenticated);
        
        },expirationDuration);*/
        this.onLogout();
      }
    ngOnDestroy()
      {
        this.userSub.unsubscribe();
      }
    onLogout()
      {
        this.isAuthenticated=false;
        console.log("logout method called: "+ this.isAuthenticated);
        this.authenticationService.logout();
      }

  }
