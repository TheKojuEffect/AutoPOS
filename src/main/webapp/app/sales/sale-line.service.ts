import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SaleLine } from './sale-line.model';

type EntityResponseType = HttpResponse<SaleLine>;

@Injectable()
export class SaleLineService {

    private resourceUrl = 'api/sales/:saleId/lines/:saleLineId';

    constructor(private http: HttpClient) {
    }

    create(saleId: number, saleLine: SaleLine): Observable<EntityResponseType> {
        const copy = this.convert(saleLine);
        return this.http.post<SaleLine>(this.getResourceUrl(saleId), copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(saleId: number, saleLineId: number, saleLine: SaleLine): Observable<EntityResponseType> {
        const copy = this.convert(saleLine);
        return this.http.put<SaleLine>(this.getResourceUrl(saleId, saleLineId), copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    delete(saleId: number, id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(this.getResourceUrl(saleId, id), {observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SaleLine = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SaleLine[]>): HttpResponse<SaleLine[]> {
        const jsonResponse: SaleLine[] = res.body;
        const body: SaleLine[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SaleLine.
     */
    private convertItemFromServer(saleLine: SaleLine): SaleLine {
        const copy: SaleLine = Object.assign({}, saleLine);
        return copy;
    }

    /**
     * Convert a SaleLine to a JSON which can be sent to the server.
     */
    private convert(saleLine: SaleLine): SaleLine {
        const copy: SaleLine = Object.assign({}, saleLine);
        return copy;
    }

    private getResourceUrl = (saleId: number, saleLineId?: number): string => {
        const lines = `api/sales/${saleId}/lines`;
        if (saleLineId) {
            return `${lines}/${saleLineId}`;
        }
        return lines;
    }
}
