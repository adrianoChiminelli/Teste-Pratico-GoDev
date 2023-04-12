import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderItem } from './orderItem';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class OrderItemService {

  private apiUrl: string = environment.apiBaseUrl;
  private resource: string = 'items';

  constructor(private http: HttpClient) { }

  public getOrderItem(orderId: string): Observable<OrderItem[]> {
    return this.http.get<OrderItem[]>(`${this.apiUrl}/${orderId}/${this.resource}`);
  }

  public getOrderItemById(orderId: string, orderItemId: string): Observable<OrderItem> {
    return this.http.get<OrderItem>(`${this.apiUrl}/${orderId}/${this.resource}/${orderItemId}`);
  }

  public addOrderItem(orderId: string, orderItem: OrderItem): Observable<OrderItem> {
    return this.http.post<OrderItem>(`${this.apiUrl}/${orderId}/${this.resource}`, orderItem);
  }

  public update(orderId: string, orderItemId: number, orderItem: OrderItem): Observable<OrderItem> {
    return this.http.put<OrderItem>(`${this.apiUrl}/${orderId}/${this.resource}/${orderItemId}`, orderItem);
  }

  public delete(orderId: number, orderItemId: string) {
    this.http.delete<OrderItem>(`${this.apiUrl}/${orderId}/${this.resource}/${orderItemId}`);
  }
}
