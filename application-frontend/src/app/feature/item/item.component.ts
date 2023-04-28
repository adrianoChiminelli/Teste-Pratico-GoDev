import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Message, MessageService } from 'primeng/api';
import { OverlayPanel } from 'primeng/overlaypanel';
import { Item } from 'src/app/core/item/item';
import { ItemService } from 'src/app/core/item/item.service';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  providers: [MessageService]
})
export class ItemComponent implements OnInit {

  public items: Item[] = [];
  public selectedItem: any;
  public showInputDialog: boolean = false;
  public itemsOnCart: Item[] = [];
  public showCartMenu: boolean = false;

  @Output() itemAdded = new EventEmitter<any>();

  constructor(private itemService: ItemService, private messageService: MessageService) { }

  ngOnInit(): void {
    this.updateGrid();
  }

  public updateGrid() {
    this.items = [];
    this.itemsOnCart = [];
    this.getItems();
  }

  public getItems(): void {
    this.itemService.get()
      .subscribe({
        next: (items) => this.items = items,
        error: (err) => this.showToast({
          severity: 'error',
          detail: this.getErroMessage(err.error.errors),
          summary: 'Erro ao carregar informações'
        })
      })
  }

  selectItem(event: Event, item: Item) {
      this.selectedItem = item;
      event.preventDefault();
  }

  onAddClick() {
    this.showInputDialog = true;
  }

  dialogClosed(event: any) {
    this.showInputDialog = event;
    this.selectedItem = undefined;
    this.updateGrid();
  }

  onEdit(item: any) {
    this.selectedItem = item;
    this.showInputDialog = true;
  }

  public showToast(event: Message) {
    this.messageService.add(event);
  }

  public addToCart(quantity: number, item: Item, op: OverlayPanel) {
    if (!quantity) {
      return;
    }

    this.itemAdded.emit({ itemToAdd: item, quantityToAdd: quantity });
    op.hide();
  }

  private getErroMessage(errors: any[]) {
    let message: string = '';
    errors.forEach(err => {
      message += err.defaultMessage + '\n';
    })

    return message;
  }
}
