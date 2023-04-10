import { Injectable } from '@angular/core';
import {HttpInterceptor,HttpRequest,HttpHandler} from '@angular/common/http';
import {HeaderType} from '../enum/header-type.enum';
@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor
  {
    intercept(req:HttpRequest<any>,next:HttpHandler)
      {
        const token=window.sessionStorage.getItem(HeaderType.AUTHORIZATION);
        if(token)
          {
           const cloned =req.clone({headers:req.headers.set("Authorization","Bearer "+token)});
           return next.handle(cloned);
          }
        else
          {
           return next.handle(req);
          }
      }
  }
