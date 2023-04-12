import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from '../entity.service';
import { Order } from './order';

@Injectable({
  providedIn: 'root'
})
export class OrderService extends EntityService<Order> {

  constructor(http: HttpClient) {
    super(http);
    this.resource = 'order';
   }
}
