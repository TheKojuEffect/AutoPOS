import { PurchaseLine } from './';
import { Vendor } from '../party/vendor/vendor.model';

export class Purchase {
    constructor(public id?: number,
                public date?: any,
                public invoiceNumber?: string,
                public discount?: number,
                public remarks?: string,
                public vendor?: Vendor,
                public lines: PurchaseLine[] = []) {
    }
}
