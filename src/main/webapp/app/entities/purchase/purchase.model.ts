import { Vendor } from '../../party/vendor';
import { PurchaseLine } from '../purchase-line';

export class Purchase {
    constructor(public id?: number,
                public date?: any,
                public invoiceNumber?: string,
                public discount?: number,
                public remarks?: string,
                public vendor?: Vendor,
                public line?: PurchaseLine,) {
    }
}
