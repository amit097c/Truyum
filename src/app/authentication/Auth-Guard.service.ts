import{CanActivate,ActivatedRouteSnapshot,RouterStateSnapshot,Router}from '@angular/router';
import{Observable} from 'rxjs'; 
import{AuthenticationService} from './authentication.service';
import{Injectable} from '@angular/core';
@Injectable()
export class AuthGuard implements CanActivate
  {
   constructor(private authenticationService:AuthenticationService,private router:Router){}
    canActivate(route:ActivatedRouteSnapshot,
    state:RouterStateSnapshot):Observable<boolean>|Promise<boolean>|boolean
      {
        return this.authenticationService.isAuthenticated().then(
        (authenticated:boolean)=>
          {
            if(authenticated)
              {
                return true;
              }
            else
              {
                this.router.navigate(['/']);
                return false;
              }
          }
        )
        .catch((err) => {
        console.log(err+" user not logged in");
        return false;})
    
      }
  }
