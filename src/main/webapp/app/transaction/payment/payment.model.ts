import { BaseEntity } from './../../shared';

export class Payment implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public amount?: number,
        public receiptNumber?: string,
        public paidBy?: string,
        public remarks?: string,
        public paidTo?: BaseEntity,
    ) {
    }
}
