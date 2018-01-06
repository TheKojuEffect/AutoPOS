import { BaseEntity } from '../../shared';
import { Item } from '../../catalog/item';

export class StockBookEntry implements BaseEntity {
    constructor(public item?: Item,
                public quantity?: number,
                public costPrice?: number,
                public lastModifiedDate?: Date,
                public remarks?: string,
                public id?: number) {
    }
}
