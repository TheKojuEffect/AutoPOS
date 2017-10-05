import { BaseEntity } from './../../shared';

export class Receipt implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public amount?: number,
        public receiptNumber?: string,
        public receivedBy?: string,
        public remarks?: string,
        public receivedFrom?: BaseEntity,
    ) {
    }
}
