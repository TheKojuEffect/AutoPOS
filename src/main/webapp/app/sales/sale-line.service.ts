import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SaleLine } from './sale-line.model';
import { createRequestOption, ResponseWrapper } from '../shared';

@Injectable()
export class SaleLineService {

    private resourceUrl = 'api/sales/:saleId/lines/:saleLineId';

    constructor(private http: Http) {
    }

    create(saleId: number, saleLine: SaleLine): Observable<SaleLine> {
        const copy = this.convert(saleLine);
        return this.http.post(this.getResourceUrl(saleId), copy)
            .map((res: Response) => {
                return res.json();
            });
    }

    update(saleId: number, saleLineId: number, saleLine: SaleLine): Observable<SaleLine> {
        const copy = this.convert(saleLine);
        return this.http.put(this.getResourceUrl(saleId, saleLineId), copy)
            .map((res: Response) => {
                return res.json();
            });
    }

    find(id: number): Observable<SaleLine> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(saleId: number, id: number): Observable<Response> {
        return this.http.delete(this.getResourceUrl(saleId, id));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

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
