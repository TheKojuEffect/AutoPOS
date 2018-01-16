import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';


import { JhiDateUtils } from 'ng-jhipster';

import { PriceHistory } from './price-history.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PriceHistoryService {

    private resourceUrl = 'api/price-histories';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(priceHistory: PriceHistory): Observable<PriceHistory> {
        const copy = this.convert(priceHistory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(priceHistory: PriceHistory): Observable<PriceHistory> {
        const copy = this.convert(priceHistory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PriceHistory> {
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
     * Convert a returned JSON object to PriceHistory.
     */
    private convertItemFromServer(json: any): PriceHistory {
        const entity: PriceHistory = Object.assign(new PriceHistory(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a PriceHistory to a JSON which can be sent to the server.
     */
    private convert(priceHistory: PriceHistory): PriceHistory {
        const copy: PriceHistory = Object.assign({}, priceHistory);

        copy.date = this.dateUtils.toDate(priceHistory.date);
        return copy;
    }
}
