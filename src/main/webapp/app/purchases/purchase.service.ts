import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { JhiDateUtils } from 'ng-jhipster';

import { Purchase } from './purchase.model';
import { createRequestOption } from '../shared';

export type EntityResponseType = HttpResponse<Purchase>;

@Injectable()
export class PurchaseService {

    private resourceUrl = 'api/purchases';

    constructor(private http: HttpClient) {
    }

    create(vat: boolean): Observable<EntityResponseType> {
        return this.http.post(this.resourceUrl, {vat}, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(purchase: Purchase): Observable<EntityResponseType> {
        const copy = this.convert(purchase);
        return this.http.put(`${this.resourceUrl}/${purchase.id}`, copy,  { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Purchase>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Purchase[]>> {
        let options = createRequestOption(req);
        options = options.append('vat', req.vat);
        return this.http.get<Purchase[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Purchase[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Purchase = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Purchase[]>): HttpResponse<Purchase[]> {
        const jsonResponse: Purchase[] = res.body;
        const body: Purchase[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Purchase.
     */
    private convertItemFromServer(purchase: Purchase): Purchase {
        return Object.assign({}, purchase);
    }

    /**
     * Convert a Purchase to a JSON which can be sent to the server.
     */
    private convert(purchase: Purchase): Purchase {
        return Object.assign({}, purchase);
    }
}
