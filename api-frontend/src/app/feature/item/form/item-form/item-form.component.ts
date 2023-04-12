import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, Message } from 'primeng/api';
import { Item } from 'src/app/core/item/item';
import { ItemService } from 'src/app/core/item/item.service';

@Component({
  selector: 'app-item-form',
  templateUrl: './item-form.component.html',
  providers: [ConfirmationService]
})
export class ItemFormComponent implements OnInit {

  public formGroup: FormGroup;
  public itemType = [
    { name: 'Produto', value: 'P' },
    { name: 'Serviço', value: 'S' }
  ]

  @Input() selectedItem: any;
  @Input() showInputDialog?: boolean;

  @Output() dialogCloseEvent = new EventEmitter<boolean>();
  @Output() showToast = new EventEmitter<Message>();

  constructor(
    private itemService: ItemService,
    private formBuilder: FormBuilder,
    private confirmationService: ConfirmationService
  ) {
    this.formGroup = this.getForm();
  }

  ngOnInit(): void {
    if (this.selectedItem) {
      this.formGroup.patchValue({
        id: this.selectedItem.id,
        description: this.selectedItem.description,
        type: this.selectedItem.type,
        value: this.selectedItem.value
      }, { emitEvent: false, onlySelf: true })
    }
  }

  getForm() {
    return this.formBuilder.group(
      {
        id: [undefined],
        description: [undefined, Validators.required],
        type: [undefined, Validators.required],
        value: [undefined, Validators.required]
      }
    );
  }

  onSave() {
    const toSave: Item = this.formGroup.value

    this.getSaveObservable(toSave)
      .subscribe({
        next: (value) => {
          this.emitToastMessage({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Item salvo com sucesso!'
          });
          this.closeDialog();
        },
        error: (err) => {
          this.emitToastMessage({
            severity: 'error',
            summary: 'Erro',
            detail: this.getErroMessage(err.error.errors)
          });
        }
      });
  }

  confirmDelete(event: Event) {
    this.confirmationService
      .confirm({
        target: event.target as EventTarget,
        message: 'Deseja deletar este item?',
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: 'Excluir',
        rejectLabel: 'Não',
        accept: () => this.delete()
      })
  }

  onCancel() {
    this.closeDialog();
  }

  delete() {
    if(this.isNew()) {
      return;
    }

    const itemId = this.formGroup.get('id')?.value;

    this.itemService
      .delete(itemId)
      .subscribe({
        next: (value) => {
          this.emitToastMessage({
            severity: 'info',
            summary: 'Atenção',
            detail: 'Item excluído com sucesso!'
          });
          this.closeDialog();
        },
        error: (err) => {
          this.emitToastMessage({
            severity: 'error',
            summary: 'Erro',
            detail: this.getErroMessage(err.error.errors)
          });
        }
      });
  }

  public emitToastMessage(message: Message) {
    this.showToast.emit(message);
  }

  closeDialog() {
    this.dialogCloseEvent.emit(false);
    this.selectedItem = null;
    this.showInputDialog = false;
  }

  private getErroMessage(errors: any[]) {
    let message: string = '';
    errors.forEach(err => {
      message += err.defaultMessage + '\n';
    })

    return message;
  }

  private getSaveObservable(item: Item) {
    console.log(this.isNew())
    if (this.isNew()) {
      return this.itemService.add(item);
    } else {
      return this.itemService.update(item.id, item)
    }
  }

  public isNew() {
    return this.selectedItem === null || this.selectedItem === undefined;
  }
}
