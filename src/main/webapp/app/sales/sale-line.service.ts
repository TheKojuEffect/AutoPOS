import { Injectable } from '@angular/core';
import { BaseRequestOptions, Http, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SaleLine } from './sale-line.model';

@Injectable()
export class SaleLineService {

    private resourceUrl = 'api/sales/:saleId/lines/:saleLineId';

    constructor(private http: Http) {
    }

    create(saleId: number, saleLine: SaleLine): Observable<SaleLine> {
        let copy: SaleLine = Object.assign({}, saleLine);
        return this.http.post(this.getResourceUrl(saleId), copy)
            .map((res: Response) => {
                return res.json();
            });
    }

    update(saleId: number, saleLineId: number, saleLine: SaleLine): Observable<SaleLine> {
        let copy: SaleLine = Object.assign({}, saleLine);

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

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options);
    }

    delete(saleId: number, id: number): Observable<Response> {
        return this.http.delete(this.getResourceUrl(saleId, id));
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

    private getResourceUrl = (saleId: number, saleLineId?: number): string => {
        const lines = `api/sales/${saleId}/lines`;
        if (saleLineId) {
            return `${lines}/${saleLineId}`;
        }
        return lines;
    }

}
