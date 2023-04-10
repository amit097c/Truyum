import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {RecipeDetailsComponent} from './recipe-details/recipe-details.component';

@NgModule({
  declarations:[
      RecipeDetailsComponent
    ],
  imports:
    [
     BrowserModule,
     CommonModule,
     ReactiveFormsModule,
    ],
  exports:
    [
     RecipeDetailsComponent
    ]

    
})
export class RecipeModule
  {

  }
