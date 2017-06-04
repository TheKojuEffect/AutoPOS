import { Purchase } from './';
import { Item } from '../catalog/item';

export class PurchaseLine {
    constructor(public id?: number,
                public quantity?: number,
                public rate?: number,
                public remarks?: string,
                public purchase?: Purchase,
                public item?: Item) {
    }

    get amount() {
        return (this.rate || 0) * (this.quantity || 0);
    }
}
