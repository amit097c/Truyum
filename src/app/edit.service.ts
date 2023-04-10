import { Injectable } from '@angular/core';
import {MenuItem} from './model/MenuItem';
@Injectable({
  providedIn: 'root'
})
export class EditService {
   item:MenuItem=new MenuItem();
  constructor() { }
  setItem(menuItem:MenuItem)
    {
      this.item=menuItem;
    }
  getItem()
    {
      return this.item;
    }
}
