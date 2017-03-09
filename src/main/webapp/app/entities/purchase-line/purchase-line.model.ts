import { Purchase } from '../purchase';
import { Item } from '../item';
export class PurchaseLine {
    constructor(
        public id?: number,
        public quantity?: number,
        public rate?: number,
        public remarks?: string,
        public purchase?: Purchase,
        public item?: Item,
    ) {
    }
}
