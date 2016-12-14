import { Category } from '../category/category';
import { Brand } from '../brand/brand';

export class Item {
    id: number;
    code: string;
    description: string;
    location: string;
    quantity: number;
    remarks: string;
    markedPrice: number;
    category: Category;
    brand: Brand;
}
