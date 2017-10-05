import { BaseEntity } from './../../shared';

export class Vehicle implements BaseEntity {
    constructor(
        public id?: number,
        public number?: string,
        public remarks?: string,
        public owner?: BaseEntity,
    ) {
    }
}
