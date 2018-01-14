import { Customer } from '../party/customer';
import { SaleLine } from './';
import { Vehicle } from '../party/vehicle';

export enum SaleStatus {
    PENDING = <any>'PENDING',
    COMPLETED = <any>'COMPLETED'
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
                public vat?: boolean,
                public lines: SaleLine[] = [],
                public vehicle?: Vehicle) {
    }
}
