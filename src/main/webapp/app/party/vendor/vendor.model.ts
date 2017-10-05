import { BaseEntity } from './../../shared';

export class Vendor implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public remarks?: string,
    ) {
    }
}
