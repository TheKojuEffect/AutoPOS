import { BaseEntity } from './../../shared';

export class DayBookEntry implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public incomingAmount?: number,
        public outgoingAmount?: number,
        public miscExpenses?: number,
        public remarks?: string,
    ) {
    }
}
