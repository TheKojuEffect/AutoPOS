import { Item } from '../catalog/item/item';

export class PurchaseLine {
    public id: number;
    public item: Item;
    public remarks: string;
    public quantity: number;
    public rate: number;

    get amount() {
        return this.quantity * this.rate;
    }
}
