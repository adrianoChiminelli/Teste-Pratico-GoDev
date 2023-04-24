import { Component } from '@angular/core';
import { Order } from 'src/app/core/order/order';
import { OrderService } from 'src/app/core/order/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
})
export class OrderComponent {
  public orders: Order[] = [];

  constructor(private orderService: OrderService) {}


}
