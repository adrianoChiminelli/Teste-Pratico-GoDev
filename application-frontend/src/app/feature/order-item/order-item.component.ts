import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { ConfirmationService, Message } from 'primeng/api';
import { Order } from 'src/app/core/order/order';
import { OrderService } from 'src/app/core/order/order.service';
import { OrderItemService } from 'src/app/core/orderItem/order-item-service.service';
import { OrderItem } from 'src/app/core/orderItem/orderItem';
import * as moment from 'moment';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  providers: [ConfirmationService]
})
export class OrderItemComponent implements OnInit, OnDestroy {

  @Input() itemsOnCart: OrderItem[] = [];

  @Output() onVisibilityChange = new EventEmitter<boolean>();
  @Output() onRemoveFromCart = new EventEmitter<OrderItem>();
  @Output() showToast = new EventEmitter<Message>();

  sidebarVisible: boolean = false;

  constructor(
    private orderService: OrderService,
    private orderItemService: OrderItemService,
    private confirmationService: ConfirmationService
    ) { }

  ngOnInit(): void {
    this.sidebarVisible = true;
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

  private makeOrder() {
    let orderValue = 0;
    this.itemsOnCart.forEach(orderItem => orderValue += orderItem.totalValue);


    const order: Order = {
      date: moment().format("DD/MM/YYYY"),
      number: Math.floor(1000 + Math.random() * 9000),
      totalValue: orderValue,
      percentualDiscount: 10
    }

    this.createOrder(order, this.itemsOnCart);
  }

  private saveOrderItens(order: any, orderItemList: OrderItem[]): OrderItem[] | null {
    orderItemList.forEach(orderItem => {
      orderItem.order = {
        id: order.id,
        date: order.date,
        number: order.number,
        percentualDiscount: order.percentualDiscount,
        totalValue: order.totalValue
      }
    });

    this.orderItemService.addOrderItemList(order.id, orderItemList)
      .subscribe({
        next: value => {
          return value;
        },
        error: err => {
          this.emitToastMessage({
            severity: 'error',
            summary: 'Erro',
            detail: this.getErroMessage(err.error.errors)
          });
        }
      })

      return null;
  }

  private createOrder(order: Order, orderItemList: OrderItem[]) {
    this.orderService.add(order)
      .subscribe({
        next: value => {
          this.saveOrderItens(value, orderItemList);

          this.emitToastMessage({
            severity: 'success',
            summary: 'Sucesso',
            detail: `Pedido de nº: ${value.number}, salvo com sucesso.`
          });
        },
        error: err => {
          this.emitToastMessage({
            severity: 'error',
            summary: 'Erro',
            detail: this.getErroMessage(err.error.errors)
          });
        }
      })
  }

  public emitToastMessage(message: Message) {
    this.showToast.emit(message);
  }

  private getErroMessage(errors: any[]) {
    let message: string = '';
    errors.forEach(err => {
      message += err.defaultMessage + '\n';
    })

    return message;
  }

  public confirmBeforeCreate(event: Event) {
    this.confirmationService
      .confirm({
        target: event.target as EventTarget,
        message: 'Confirmar criação do pedido?',
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: 'Sim',
        rejectLabel: 'Não',
        accept: () => this.makeOrder()
      })
  }
}
