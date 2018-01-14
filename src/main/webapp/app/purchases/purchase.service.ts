import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Purchase } from './purchase.model';
import { createRequestOption, ResponseWrapper } from '../shared';

@Injectable()
export class PurchaseService {

    private resourceUrl = 'api/purchases';

    constructor(private http: Http, private dateUtils: JhiDateUtils) {
    }

    create(vat: boolean): Observable<Purchase> {
        return this.http.post(this.resourceUrl, {vat}).map((res: Response) => {
            return res.json();
        });
    }

    update(purchase: Purchase): Observable<Purchase> {
        const copy = this.convert(purchase);
        return this.http.put(`${this.resourceUrl}/${purchase.id}`, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Purchase> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        options.params.set('vat', req.vat);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.date = this.dateUtils
            .convertDateTimeFromServer(entity.date);
    }

    private convert(purchase: Purchase): Purchase {
        const copy: Purchase = Object.assign({}, purchase);

        // copy.date = this.dateUtils.toDate(purchase.date);
        return copy;
    }
}
