import { Sale } from '../sale';
import { Item } from '../item';
export class SaleLine {
    constructor(
        public id?: number,
        public buyer?: string,
        public quantity?: number,
        public rate?: number,
        public remarks?: string,
        public sale?: Sale,
        public item?: Item,
    ) {
    }
}
