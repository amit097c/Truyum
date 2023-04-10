export class UserToken
  {
    constructor(
    public username:number,
    public id:string,
    private _token:string,
    private _tokenExpirationDate:Date
    ){}
    get token():string
      {
        if(!this._tokenExpirationDate||new Date()>this._tokenExpirationDate)
          {
            return '';
          }
        return this.token;
      }
  }
