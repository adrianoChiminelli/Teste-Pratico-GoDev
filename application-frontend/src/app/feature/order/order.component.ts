import { Component, OnInit } from '@angular/core';
import { Message, MessageService } from 'primeng/api';
import { ItemType } from 'src/app/core/item/enum/ItemType';
import { Order } from 'src/app/core/order/order';
import { OrderService } from 'src/app/core/order/order.service';
import { OrderItemService } from 'src/app/core/orderItem/order-item-service.service';
import { OrderItem } from 'src/app/core/orderItem/orderItem';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  providers: [MessageService]
})
export class OrderComponent implements OnInit {

  public orders: Order[] = [];
  public orderItems: OrderItem[] = [];
  public itemType: typeof ItemType = ItemType;

  constructor(private orderService: OrderService, private orderItemService: OrderItemService, private messageService: MessageService) {}

  ngOnInit(): void {
    this.orderService.get()
      .subscribe({
        next: (order) => this.orders = order,
        error: (err) => this.showToast({
          severity: 'error',
          detail: this.getErroMessage(err.error.errors),
          summary: 'Erro ao carregar informações'
        })
      })
  }

  public showToast(event: Message) {
    this.messageService.add(event);
  }

  public rowExpanded(event: Order) {
    if (event.id) {
      this.orderItemService.getOrderItem(event.id)
        .subscribe({
          next: orderItemList => this.orderItems = orderItemList,
          error: (err) => this.showToast({
            severity: 'error',
            detail: this.getErroMessage(err.error.errors),
            summary: 'Erro ao carregar informações'
          })
        });
    }
  }

  private getErroMessage(errors: any[]) {
    let message: string = '';
    errors.forEach(err => {
      message += err.defaultMessage + '\n';
    })

    return message;
  }
}
