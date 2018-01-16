import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';


import { JhiDateUtils } from 'ng-jhipster';

import { Receipt } from './receipt.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReceiptService {

    private resourceUrl = 'api/receipts';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(receipt: Receipt): Observable<Receipt> {
        const copy = this.convert(receipt);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(receipt: Receipt): Observable<Receipt> {
        const copy = this.convert(receipt);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Receipt> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Receipt.
     */
    private convertItemFromServer(json: any): Receipt {
        const entity: Receipt = Object.assign(new Receipt(), json);
        entity.date = this.dateUtils
            .convertLocalDateFromServer(json.date);
        return entity;
    }

    /**
     * Convert a Receipt to a JSON which can be sent to the server.
     */
    private convert(receipt: Receipt): Receipt {
        const copy: Receipt = Object.assign({}, receipt);
        copy.date = this.dateUtils
            .convertLocalDateToServer(receipt.date);
        return copy;
    }
}
