import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'
import { DataViewModule } from 'primeng/dataview';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ItemComponent } from './feature/item/item.component';
import { DropdownModule } from 'primeng/dropdown';
import { PanelModule } from 'primeng/panel';
import { DialogModule } from 'primeng/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TabMenuModule } from 'primeng/tabmenu';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ItemFormComponent } from './feature/item/form/item-form/item-form.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { FileUploadModule } from 'primeng/fileupload';
import { ToastModule } from 'primeng/toast';
import { ConfirmPopupModule } from 'primeng/confirmpopup';

@NgModule({
  declarations: [
    AppComponent,
    ItemComponent,
    ItemFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    DataViewModule,
    DropdownModule,
    PanelModule,
    DialogModule,
    FormsModule,
    TabMenuModule,
    CardModule,
    ButtonModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    InputTextModule,
    InputNumberModule,
    FileUploadModule,
    ToastModule,
    ConfirmPopupModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
