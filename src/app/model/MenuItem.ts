export class MenuItem
  {
    id:number=0;
    name:string='xxxxx';
    price:number=0;
    active:boolean=false;
    dateOfLaunch:string='';
    category:string='';
    freeDelivery:string='no';
    constructor()//id:number,name:string,price:number,active:boolean,dateOfLaunch:string ,category:string,freeDelivery:string)
     {
      this.id=0;//id;
      this.name='';//name;
      this.price=0;//price;
      this.active=false;//active;
      this.dateOfLaunch='';//dateOfLauch;
      this.category='';//category;
      this.freeDelivery='no';//freeDelivery;
     }
    public getId()
      {
        return this.id;
      }
    public getName()
      {
        return this.name;
      }
    
 }
