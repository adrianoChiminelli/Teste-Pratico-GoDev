import { Component, OnInit } from '@angular/core';
import { Message, MessageService } from 'primeng/api';
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

  constructor(private itemService: ItemService, private messageService: MessageService) { }

  ngOnInit(): void {
    this.updateGrid();
  }

  public updateGrid() {
    this.items = [];
    this.getItems();
  }

  public getItems(): void {
    this.itemService.get()
      .subscribe({
        next: (items) => this.items = items,
        error: (err) => alert(err.message)
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
}
