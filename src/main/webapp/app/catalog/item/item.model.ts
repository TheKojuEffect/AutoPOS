import { Category } from '../category';
import { Brand } from '../brand';
import { Tag } from '../tag';
export class Item {
    constructor(public id?: number,
                public code?: string,
                public name?: string,
                public partNumber?: string,
                public markedPrice?: number,
                public quantity?: number,
                public description?: string,
                public location?: string,
                public remarks?: string,
                public category?: Category,
                public brand?: Brand,
                public tag?: Tag) {
    }
}
