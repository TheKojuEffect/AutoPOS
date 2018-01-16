import { BaseEntity } from './../../shared';

export class Item implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public partNumber?: string,
        public markedPrice?: number,
        public quantity?: number,
        public description?: string,
        public location?: string,
        public remarks?: string,
        public category?: BaseEntity,
        public brand?: BaseEntity,
        public tags?: BaseEntity[],
    ) {
    }
}
