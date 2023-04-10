import { Component,OnInit } from '@angular/core';
import {FormGroup,FormControl,Validators} from '@angular/forms';
import {AuthenticationService} from '../authentication/authentication.service';
import {NotificationService} from '../authentication/notification.service';
import {Router} from '@angular/router';
import {NotificationType} from '../enum/NotificationType.enum';
import {HttpResponse,HttpErrorResponse,HttpClient} from '@angular/common/http';
import {Subscription} from 'rxjs';
import {User} from '../model/user';



@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit
  {
     registerForm:FormGroup=new FormGroup({});
     showLoading:boolean=false;
     private subscription: Subscription[]=[];
     constructor(private router:Router,private authentication:AuthenticationService,private notificationService :NotificationService){}
     ngOnInit():void
       {
         this.registerForm=new FormGroup({
         'username':new FormControl(null,[Validators.required,Validators.pattern('^[A-Za-z][A-Za-z0-9_]{2,}$')]),
         //'phonenumber':new FormControl('',[Validators.required,Validators.pattern('^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$')] ),
         'password':new FormControl(null,[Validators.required])});
         //console.log(this.registerForm.get('username')?.valid);
       }
      public onRegister(response: User)
       {
           console.log(response);
           this.showLoading=true;
           this.subscription.push(
           this.authentication.register(response).subscribe((response:User)=>
            {
                console.log("response: "+response);
                this.showLoading=false; 
                //this.sendNotification(NotificationType.SUCCESS,`A account is created for ${response?.username}`. Please login with this credentials`);
                //console.log("Response: "+${response.body?.username);
            },
           
        (errorResponse:HttpErrorResponse)=>
         {
           console.log("Error: "+errorResponse+" response: "+response.username);
           console.log("response: xcx"+response);
           this.sendNotification(NotificationType.ERROR,errorResponse.error.message  );
           this.showLoading=false;
         }
        )
       );
       this.router.navigateByUrl('login');
       }
      public switchToLogin()
        {
          this.router.navigateByUrl('login');
        }
      private sendNotification(notificationType:NotificationType, message:string)
        {
          if(message)
            {
              //this.notificationService.notify(notificationType,message);
            }
           else
             {
              //this.notificationService.notify(notificationType,'An error occured. Please try again');
             }
        }

  }

