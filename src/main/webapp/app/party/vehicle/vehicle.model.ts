import { Customer } from '../customer';

export class Vehicle {
    constructor(public id?: number,
                public number?: string,
                public remarks?: string,
                public owner?: Customer) {
    }
}
