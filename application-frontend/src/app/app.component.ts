import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'api-frontend';

  items: MenuItem[] = [];

  activeItem: MenuItem = {};

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
}
