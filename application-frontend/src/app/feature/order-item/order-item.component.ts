import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { Order } from 'src/app/core/order/order';
import { OrderService } from 'src/app/core/order/order.service';
import { OrderItemService } from 'src/app/core/orderItem/order-item-service.service';
import { OrderItem } from 'src/app/core/orderItem/orderItem';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html'
})
export class OrderItemComponent implements OnInit, OnChanges, OnDestroy {

  @Input() itemsOnCart: OrderItem[] = [];

  @Output() onVisibilityChange = new EventEmitter<boolean>();
  @Output() onRemoveFromCart = new EventEmitter<OrderItem>();

  sidebarVisible: boolean = false;

  constructor(private orderService: OrderService, private orderItemService: OrderItemService) { }

  ngOnInit(): void {
    this.sidebarVisible = true;
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes)
  }

  ngOnDestroy(): void {
    this.sidebarVisible = false;
  }

  onPanelHide() {
    this.sidebarVisible = false;
    this.onVisibilityChange.emit(false);
  }

  onRemove(orderItem: OrderItem) {
    this.onRemoveFromCart.emit(orderItem);
  }

  makeOrder() {
    let orderValue = 0;
    this.itemsOnCart.forEach(orderItem => orderValue += orderItem.totalValue);


    const order: Order = {
      date: new Date(),
      number: Math.floor(1000 + Math.random() * 9000),
      totalValue: orderValue,
      percentualDiscount: 10
    }

    //createOrder()

    alert('Todo')
  }

  private createOrder(order: Order) {
    this.orderService.add(order)
      .subscribe({
        next(value) {

        },
        error(err) {

        },
      })
  }
}
