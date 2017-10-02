import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Sale } from './sale.model';
import { createRequestOption, ResponseWrapper } from '../shared';

@Injectable()
export class SaleService {

    private resourceUrl = 'api/sales';

    constructor(private http: Http, private dateUtils: JhiDateUtils) {
    }

    create(): Observable<Sale> {
        return this.http.post(this.resourceUrl, null).map((res: Response) => {
            return res.json();
        });
    }

    update(sale: Sale): Observable<Sale> {
        const copy = this.convert(sale);
        return this.http.put(`${this.resourceUrl}/${sale.id}`, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Sale> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        options.search.set('status', req.status);
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

    private convert(sale: Sale): Sale {
        const copy: Sale = Object.assign({}, sale);

        // copy.date = this.dateUtils.toDate(sale.date);
        return copy;
    }
}
