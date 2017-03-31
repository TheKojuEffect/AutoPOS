import { Phone } from '../phone';

export class Vendor {
    constructor(public id?: number,
                public name?: string,
                public remarks?: string,
                public phone?: Phone) {
    }
}
