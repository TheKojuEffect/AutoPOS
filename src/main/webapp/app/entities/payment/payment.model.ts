import { Vendor } from '../vendor';
export class Payment {
    constructor(
        public id?: number,
        public date?: any,
        public amount?: number,
        public receiptNumber?: string,
        public paidBy?: string,
        public remarks?: string,
        public paidTo?: Vendor,
    ) {
    }
}
