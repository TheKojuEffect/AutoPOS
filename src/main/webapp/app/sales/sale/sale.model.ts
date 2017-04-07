import { Customer } from '../../party/customer';
import { SaleLine } from '../sale-line';
import { Vehicle } from '../../party/vehicle';

const enum SaleStatus {
    'PENDING',
    'COMPLETED'

}

export class Sale {
    constructor(public id?: number,
                public date?: any,
                public invoiceNumber?: string,
                public discount?: number,
                public remarks?: string,
                public buyer?: string,
                public status?: SaleStatus,
                public customer?: Customer,
                public line?: SaleLine,
                public vehicle?: Vehicle) {
    }
}
