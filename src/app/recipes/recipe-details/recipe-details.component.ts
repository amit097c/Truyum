import { Component,OnInit,ViewChild,ElementRef } from '@angular/core';
import {FormGroup,FormControl,NgForm,NgControl} from '@angular/forms';
import {EditService} from '../../edit.service';
import {MenuItem} from '../../model/MenuItem';
@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})
export class RecipeDetailsComponent implements OnInit
  {
    menuDetailsForm:FormGroup=new FormGroup({
        itemName:new FormControl('itemName'),
        itemPrice:new FormControl('itemPrice'),
        dateOfLaunch:new FormControl('dateOfLaunch'),
        category:new FormControl('category'),
        isActive:new FormControl('isActive')
        });
    @ViewChild('recipeDetail',{static:false}) menu:any;
    constructor(private editService:EditService){}
    ngOnInit():void
      {
        this.menuDetailsForm=new FormGroup({
        itemName:new FormControl('itemName'),
        itemPrice:new FormControl('itemPrice'),
        dateOfLaunch:new FormControl('dateOfLaunch'),
        category:new FormControl('category'),
        isActive:new FormControl('isActive')
        });
        
      }
    public editChanges()
      {
        console.log("Edit changes called");
        
      }
    public onSaveItem(item:MenuItem)
      {
        this.editService.setItem(item);
        console.log("menu element: "+document.getElementById("itemName"));
      }
    public getItem()
      {
        return this.editService.getItem();
      }

  }
