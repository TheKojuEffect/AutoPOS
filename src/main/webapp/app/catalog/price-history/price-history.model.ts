import { Item } from '../item';

export class PriceHistory {
    constructor(public id?: number,
                public date?: any,
                public markedPrice?: number,
                public remarks?: string,
                public item?: Item) {
    }
}
