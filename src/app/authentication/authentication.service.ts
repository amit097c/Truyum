import { Injectable,ViewChild,Inject,ElementRef,Input,OnInit } from '@angular/core';
import {HttpClient,HttpResponse,HttpErrorResponse,HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment'; 
import {User} from '../model/user';
import {throwError,catchError,Subject,tap} from 'rxjs';
import {JwtHelperService} from "@auth0/angular-jwt";
import {UserToken} from '../model/userToken.model';
import {HeaderType} from '../enum/header-type.enum';
import {Router} from '@angular/router';
import {TopNavigationBarComponent} from '../top-navigation-bar/top-navigation-bar.component';


@Injectable({
  providedIn: 'root'
})


export class AuthenticationService 
{
  userAuth=new Subject();
  private tokenExpirationTimer:any=null;
  httpHeader =
   {
     headers: new HttpHeaders({ 'Content-Type': 'application/json' })
   };
  private jwtHelper=new JwtHelperService();
  private token:string='';
  private loggedInUsername:string='';
  public host=environment.apiUrl;//C:\Users\848486\OneDrive - Cognizant\Desktop\truYum\angular\truYum1\client\src\environments\environment.ts
  constructor(private http:HttpClient,private router:Router) { }
  public register(user:User):Observable<User>
    {
      return this.http.post<User>(`${this.host}/register`,user)
      .pipe(tap(resData=>
        {
          console.log("resData: "+JSON.stringify(resData));
          //const userAuth=new UserToken()
         }));
    }
  public login(user :User):Observable<HttpResponse<string>>
    {
      
      const httpOptions=
       {
        headers:new HttpHeaders
         ({
           'Content-type':'application/json',
            Authorization: 'Authorization'
         })
       };
        return (this.http.post<string>(`${this.host}/login`,user,{observe:'response'})
       .pipe(catchError((err)=>this.handleAndThrowError(err)),tap(resData=>
          {
            const userInfoStr=JSON.stringify(resData);
            console.log(JSON.parse(userInfoStr));
            const userInfo=JSON.parse(userInfoStr);

            this.handleAuthentication(Number(userInfo.body.UserId),userInfo.body.Username,
            userInfo.body.JwtToken,Number(userInfo.body.Expiry));
          }
       ))
       );
    }

   private handleAuthentication(userId:number,username:string,token:string,expiresIn:number)
     {
     
        console.log("------User Info------");

        const expirationDate=new Date(new Date().getTime()+expiresIn)
        const userDetails=new UserToken
         (
          userId,
          username,
          token,
          expirationDate
         )
         console.log("userDetails: "+userDetails);
         this.autoLogout(expiresIn);
         this.userAuth.next(userDetails);
     }
   private handleAndThrowError(err:HttpErrorResponse )
   {
    console.log(err.error);
    return throwError(err);
   }
  public loadToken()
    {
      this.token=sessionStorage.getItem("Authorization") as string;
    }
  public getToken()
    {
      return this.token;
    }
  public isLoggedIn()
    {
     this.loadToken();
     if(this.token!=null&& this.token!=='')
       {
        // console.log("decode: "+this.jwtHelper.decodeToken(this.token).sub);
         if(this.jwtHelper.decodeToken(this.token).sub!=null)
           {
              if(!this.jwtHelper.isTokenExpired(this.token))
                {
                  this.loggedInUsername=this.jwtHelper.decodeToken(this.token).sub;
                  console.log(this.loggedInUsername+" logged in");
                  return true;
                }
           }
       }
      else
        {
         console.log("token is null");
         return false;
        }
        return false;
    }
  public isAuthenticated()
    {
      const promise:Promise<boolean>=new Promise(
      (resolve,reject)=>
        {
          if(this.isLoggedIn()){
          resolve(this.isLoggedIn());}
          else{reject(this.isLoggedIn());}
        }
      )
      return promise;
    }
   public getUsername()
     {
       return this.loggedInUsername;
     }
  public logout()
    {
      this.userAuth.next(null);
      this.token='';
      window.sessionStorage.removeItem(HeaderType.AUTHORIZATION);
      if(this.tokenExpirationTimer)
       {
         clearTimeout(this.tokenExpirationTimer);
       }
       this.tokenExpirationTimer=null;
       this.router.navigateByUrl('login');
    }
  public autoLogout(expirationDuration:number)
    {
        this.tokenExpirationTimer=setTimeout(()=>{
        this.logout();
        },expirationDuration);
        
    }
}
