import { Customer } from '../../party/customer';

export class Receipt {
    constructor(public id?: number,
                public date?: any,
                public amount?: number,
                public receiptNumber?: string,
                public receivedBy?: string,
                public remarks?: string,
                public receivedFrom?: Customer,) {
    }
}
