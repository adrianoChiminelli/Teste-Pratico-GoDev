import { Component } from '@angular/core';
import { MenuItem, Message, MessageService } from 'primeng/api';
import { OrderItem } from './core/orderItem/orderItem';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [MessageService]
})
export class AppComponent {
  title = 'api-frontend';

  items: MenuItem[] = [];

  activeItem: MenuItem = {};

  orderItemsOnCart: OrderItem[] = [];

  sidebarVisible: boolean = false;

  constructor(private messageService: MessageService) {}

  ngOnInit() {
      this.items = [
          { label: 'Itens', icon: 'pi pi-fw pi-home' },
          { label: 'Pedidos', icon: 'pi pi-fw pi-calendar' },
      ];

      this.activeItem = this.items[0];
  }

  onActiveItemChange(event: MenuItem) {
    this.activeItem = event;
  }

  sidebarVisibilityChange(event: boolean) {
    this.makeSidebarVisible(event);
  }

  onItemAdded(event: any) {
    const itemToAdd = event.itemToAdd;
    const quantityToAdd = event.quantityToAdd;

    const index = this.orderItemsOnCart.findIndex(orderItem => orderItem.item?.id === itemToAdd.id);

    if (index !== -1) {
      const itemOnList = this.orderItemsOnCart[index];

      itemOnList.quantity += quantityToAdd;
      itemOnList.totalValue = itemOnList.quantity * (itemOnList.item?.value ?? 0);
    } else {
      this.orderItemsOnCart.push({
        item: itemToAdd,
        quantity: quantityToAdd,
        totalValue: itemToAdd.value * quantityToAdd
      });
    }

    this.makeSidebarVisible(true);
  }

  makeSidebarVisible(status: boolean) {
    this.sidebarVisible = status;
  }

  onRemoveFromCart(event: OrderItem) {
    const index = this.orderItemsOnCart.indexOf(event);
    this.orderItemsOnCart.splice(index, 1);

    this.closeSidebarIfEmpty();
  }

  closeSidebarIfEmpty() {
    if (!this.orderItemsOnCart.length) {
      this.sidebarVisible = false;
    }
  }

  public showToast(event: Message) {
    this.messageService.add(event);
  }
}
