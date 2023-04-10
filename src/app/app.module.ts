import { NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {ReactiveFormsModule} from '@angular/forms';
import { RegisterComponent } from './register/register.component';
import { TopNavigationBarComponent } from './top-navigation-bar/top-navigation-bar.component';
import { HttpClientModule,HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthenticationService} from './authentication/authentication.service';
import {NotificationService} from './authentication/notification.service';
import {NotifierService,NotifierModule } from 'angular-notifier';
import { HomeComponent } from './home/home.component';
import { MenuItemsComponent } from './menu-items/menu-items.component';
import { StorageService } from './authentication/storage.service';
import {AuthenticationInterceptor} from './authentication-interceptor/authentication-interceptor';
import { CartComponent } from './cart/cart.component';
import { TopbarComponent } from './topbar/topbar.component';
import { AuthGuard} from './authentication/Auth-Guard.service';
import {NotificationModule} from './notification/notification.module';
import { AlertComponent } from './alert/alert.component';
import { PlaceholderDirective } from './placeholder.directive';
import { RecipeModule} from './recipes/recipe.module';
import {EditService} from './edit.service';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    TopNavigationBarComponent,
    HomeComponent,
    MenuItemsComponent,
    CartComponent,
    TopbarComponent,
    AlertComponent,
    PlaceholderDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NotificationModule,
    RecipeModule
  ],
  providers: [
  AuthenticationService,
  NotificationService,
  StorageService,
    {
      provide:HTTP_INTERCEPTORS,
      useClass:AuthenticationInterceptor,
      multi:true
    },
    AuthGuard,
    EditService
  ],
  bootstrap: [AppComponent],
  entryComponents:[AlertComponent]
})
export class AppModule { }
