import { Component,OnInit,Inject,ViewChild,ComponentFactoryResolver } from '@angular/core';
import {FormGroup,FormControl,Validators,NgForm} from '@angular/forms';
import { Router} from '@angular/router';
import { Subscription} from 'rxjs';
import { HttpClient,HttpResponse,HttpErrorResponse} from '@angular/common/http'; 
import {AuthenticationService} from '../authentication/authentication.service';
import {User} from '../model/user';
import {HeaderType} from '../enum/header-type.enum';
import {StorageService} from '../authentication/storage.service';
import {NotificationService} from '../authentication/notification.service';
import {NotificationType} from '../enum/NotificationType.enum';
import {AlertComponent} from '../alert/alert.component';
import {PlaceholderDirective} from '../placeholder.directive';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit
{
  //StorageService storageService=new StorageService();
 
  public showLoading:boolean= false;
  public error:string='';
  closeSub:Subscription=new Subscription();
  private subscription:Subscription[]=[];
  loginForm:FormGroup=new FormGroup({
             username:new FormControl('',[Validators.required,Validators.pattern('^[A-Za-z][A-Za-z0-9_]{2,}$')]),
            password:new FormControl('', [Validators.required]),
  });
  @ViewChild(PlaceholderDirective,{static: false}) alertHost:any;
  constructor(private router:Router,private authentication:AuthenticationService,
  private storageService: StorageService,private notificationService:NotificationService,
  private componentFactoryResolver:ComponentFactoryResolver){}

 

  ngOnInit():void
    {
      this.loginForm=new FormGroup
        ({
            username:new FormControl('',[Validators.required,Validators.pattern('^[A-Za-z][A-Za-z0-9_]{2,}$')]),
            password:new FormControl('', [Validators.required]),
            
        });

      if(this.authentication.isLoggedIn())
        {
          this.router.navigateByUrl('home');
        }
   
    }
  public onLogin(credentials:User)
    {
       this.showLoading=true;
       this.subscription.push(
       this.authentication.login(credentials).subscribe(
        (response:HttpResponse<any>)=>
        {
          console.log("Response: "+JSON.stringify(response));
          console.log("Headers: "+response.headers.get(HeaderType.JWT_TOKEN));
          var jwttoken=response.headers.get(HeaderType.JWT_TOKEN);

          if (jwttoken)
           {
             console.log("jwt Token: "+jwttoken);
             this.storageService.saveUser(jwttoken);
             console.log("user stored is : "+this.storageService.getUser());
             this.notificationService.notify(NotificationType.SUCCESS,"ACCESS GRANTED");
             this.router.navigateByUrl('home');
           }
          this.showLoading=false;
        },
          (errorResponse:HttpErrorResponse)=>
         {
           console.log("Error: "+errorResponse.error);

           this.error="Error: "+errorResponse.error;
           this.showErrorAlert();
           this.notificationService.notify(NotificationType.ERROR,"Error: "+errorResponse.error);
           this.showLoading=false;
         }
        )
       );
       console.log("-"+credentials.username);
       //console.log(this.loginForm.get('username'));
       //console.log(credentials.username);
       //console.log(credentials.password );
       

   }
  public onClickRegister()
  {
     this.router.navigateByUrl('register');
  }
  public onHandleError()
    {
        this.error='';
    }
  public showErrorAlert()
    {
      const alertCompFactory=this.componentFactoryResolver.resolveComponentFactory(AlertComponent);
      const hostViewContainerRef=this.alertHost.viewContainerRef;
      hostViewContainerRef.clear();
      const componentRef=hostViewContainerRef.createComponent(alertCompFactory);
      componentRef.instance.message=this.error;
      this.closeSub=componentRef.instance.close.subscribe(()=>{
        this.closeSub.unsubscribe();
        hostViewContainerRef.clear();
      });
    }
    
}
interface MyObj {
    username: string;
    password: string;
}
