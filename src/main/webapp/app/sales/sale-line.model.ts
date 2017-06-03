import { Sale } from './';
import { Item } from '../catalog/item';

export class SaleLine {
    constructor(public id?: number,
                public buyer?: string,
                public quantity?: number,
                public rate?: number,
                public remarks?: string,
                public sale?: Sale,
                public item?: Item) {
    }

    get amount() {
        return (this.rate || 0) * (this.quantity || 0);
    }
}
