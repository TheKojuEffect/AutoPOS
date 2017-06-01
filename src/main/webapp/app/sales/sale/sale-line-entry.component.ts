import { Component } from '@angular/core';
import {SaleLine} from '../sale-line/sale-line.model';

@Component({
    selector: 'apos-sale-line-entry',
    templateUrl: './sale-line-entry.component.html'
})
export class SaleLineEntryComponent {

    line = new SaleLine();
}
