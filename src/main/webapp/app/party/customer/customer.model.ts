import { Phone } from '../phone';

export class Customer {
    constructor(public id?: number,
                public name?: string,
                public remarks?: string,
                public phone?: Phone) {
    }
}
