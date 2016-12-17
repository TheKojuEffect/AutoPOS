import { Item } from '../catalog/item/item';

export class SaleLine {
    public id: number;
    public item: Item;
    public buyer: string;
    public remarks: string;
    public quantity: number;
    public rate: number;

    get amount() {
        return this.quantity * this.rate;
    }
}
