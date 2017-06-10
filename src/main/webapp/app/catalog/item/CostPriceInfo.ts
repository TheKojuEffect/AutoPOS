import { Purchase } from '../../purchases/purchase.model';
import { Vendor } from '../../party/vendor/vendor.model';

export class CostPriceInfo {
    constructor(purchase: Purchase,
                price: number,
                vendor?: Vendor) {
    }
}
