import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { PurchaseLine } from './purchase-line.model';

type EntityResponseType = HttpResponse<PurchaseLine>;

@Injectable()
export class PurchaseLineService {

    private resourceUrl = 'api/purchases/:purchaseId/lines/:purchaseLineId';

    constructor(private http: HttpClient) {
    }

    create(purchaseId: number, purchaseLine: PurchaseLine): Observable<EntityResponseType> {
        const copy = this.convert(purchaseLine);
        return this.http.post<PurchaseLine>(this.getResourceUrl(purchaseId), copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(purchaseId: number, purchaseLineId: number, purchaseLine: PurchaseLine): Observable<EntityResponseType> {
        const copy = this.convert(purchaseLine);
        return this.http.put<PurchaseLine>(this.getResourceUrl(purchaseId, purchaseLineId), copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    delete(purchaseId: number, id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(this.getResourceUrl(purchaseId, id), {observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PurchaseLine = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PurchaseLine[]>): HttpResponse<PurchaseLine[]> {
        const jsonResponse: PurchaseLine[] = res.body;
        const body: PurchaseLine[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PurchaseLine.
     */
    private convertItemFromServer(purchaseLine: PurchaseLine): PurchaseLine {
        const copy: PurchaseLine = Object.assign({}, purchaseLine);
        return copy;
    }

    /**
     * Convert a PurchaseLine to a JSON which can be sent to the server.
     */
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
