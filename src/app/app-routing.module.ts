import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import{LoginComponent}from './login/login.component';
import{RegisterComponent}from './register/register.component';
import{HomeComponent}from './home/home.component';
import{MenuItemsComponent} from'./menu-items/menu-items.component';
import{CartComponent} from './cart/cart.component';
import{AuthGuard} from './authentication/Auth-Guard.service';
import{RecipeDetailsComponent} from './recipes/recipe-details/recipe-details.component';
const routes: Routes = [
{path:'login',component:LoginComponent},
{path:'register',component:RegisterComponent},
{path:'home',component:HomeComponent},
{path:'menuItems',component:MenuItemsComponent},
{path:'cart',canActivate :[AuthGuard], component:CartComponent},
{path:'recipeDetails',component:RecipeDetailsComponent},
{path:'',redirectTo:'/login',pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
