import { BaseEntity } from './../../shared';

export class PriceHistory implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public markedPrice?: number,
        public remarks?: string,
        public item?: BaseEntity,
    ) {
    }
}
