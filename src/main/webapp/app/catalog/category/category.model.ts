import { BaseEntity } from './../../shared';

export class Category implements BaseEntity {
    constructor(
        public id?: number,
        public shortName?: string,
        public name?: string,
    ) {
    }
}
