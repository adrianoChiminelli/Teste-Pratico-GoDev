import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from '../entity.service';
import { Item } from './item';


@Injectable({
  providedIn: 'root'
})
export class ItemService extends EntityService<Item> {

  constructor(http: HttpClient) {
    super(http);
    this.resource = 'items';
  }
}
