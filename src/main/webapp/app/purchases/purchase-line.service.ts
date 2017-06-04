import { Injectable } from '@angular/core';
import { BaseRequestOptions, Http, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PurchaseLine } from './purchase-line.model';

@Injectable()
export class PurchaseLineService {

    private resourceUrl = 'api/purchases/:purchaseId/lines/:purchaseLineId';

    constructor(private http: Http) {
    }

    create(purchaseId: number, purchaseLine: PurchaseLine): Observable<PurchaseLine> {
        let copy: PurchaseLine = Object.assign({}, purchaseLine);
        return this.http.post(this.getResourceUrl(purchaseId), copy)
            .map((res: Response) => {
                return res.json();
            });
    }

    update(purchaseId: number, purchaseLineId: number, purchaseLine: PurchaseLine): Observable<PurchaseLine> {
        let copy: PurchaseLine = Object.assign({}, purchaseLine);

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

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options);
    }

    delete(purchaseId: number, id: number): Observable<Response> {
        return this.http.delete(this.getResourceUrl(purchaseId, id));
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }

    private getResourceUrl = (purchaseId: number, purchaseLineId?: number): string => {
        const lines = `api/purchases/${purchaseId}/lines`;
        if (purchaseLineId) {
            return `${lines}/${purchaseLineId}`;
        }
        return lines;
    }

}
