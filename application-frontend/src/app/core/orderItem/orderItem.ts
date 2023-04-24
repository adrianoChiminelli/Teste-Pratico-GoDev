import { Item } from "../item/item";
import { Order } from "../order/order";

export interface OrderItem {
  id?: string;
  order?: Order;
  item?: Item;
  quantity: number;
  totalValue: number;
}
