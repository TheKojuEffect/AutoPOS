import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PurchaseLine } from './purchase-line.model';
import { createRequestOption, ResponseWrapper } from '../shared';

@Injectable()
export class PurchaseLineService {

    private resourceUrl = 'api/purchases/:purchaseId/lines/:purchaseLineId';

    constructor(private http: Http) {
    }

    create(purchaseId: number, purchaseLine: PurchaseLine): Observable<PurchaseLine> {
        const copy = this.convert(purchaseLine);
        return this.http.post(this.getResourceUrl(purchaseId), copy)
            .map((res: Response) => {
                return res.json();
            });
    }

    update(purchaseId: number, purchaseLineId: number, purchaseLine: PurchaseLine): Observable<PurchaseLine> {
        const copy = this.convert(purchaseLine);
        return this.http.put(this.getResourceUrl(purchaseId, purchaseLineId), copy)
            .map((res: Response) => {
                return res.json();
            });
    }

    find(id: number): Observable<PurchaseLine> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(purchaseId: number, id: number): Observable<Response> {
        return this.http.delete(this.getResourceUrl(purchaseId, id));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(purchaseLine: PurchaseLine): PurchaseLine {
        const copy: PurchaseLine = Object.assign({}, purchaseLine);
        return copy;
    }

    private getResourceUrl = (purchaseId: number, purchaseLineId?: number): string => {
        const lines = `api/purchases/${purchaseId}/lines`;
        if (purchaseLineId) {
            return `${lines}/${purchaseLineId}`;
        }
        return lines;
    }
}
