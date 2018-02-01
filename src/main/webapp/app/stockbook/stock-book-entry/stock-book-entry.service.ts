import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { StockBookEntry } from './stock-book-entry.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<StockBookEntry>;

@Injectable()
export class StockBookEntryService {

    private resourceUrl = 'api/stock-book-entries';

    constructor(private http: HttpClient) {
    }

    create(stockBookEntry: StockBookEntry): Observable<EntityResponseType> {
        const copy = this.convert(stockBookEntry);
        return this.http.post<StockBookEntry>(this.resourceUrl, copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(stockBookEntry: StockBookEntry): Observable<EntityResponseType> {
        const copy = this.convert(stockBookEntry);
        return this.http.put<StockBookEntry>(this.resourceUrl, copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<StockBookEntry>(`${this.resourceUrl}/${id}`, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<StockBookEntry[]>> {
        const options = createRequestOption(req);
        return this.http.get<StockBookEntry[]>(this.resourceUrl, {params: options, observe: 'response'})
            .map((res: HttpResponse<StockBookEntry[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: StockBookEntry = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<StockBookEntry[]>): HttpResponse<StockBookEntry[]> {
        const jsonResponse: StockBookEntry[] = res.body;
        const body: StockBookEntry[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to StockBookEntry.
     */
    private convertItemFromServer(stockBookEntry: StockBookEntry): StockBookEntry {
        return Object.assign({}, stockBookEntry);
    }

    /**
     * Convert a StockBookEntry to a JSON which can be sent to the server.
     */
    private convert(stockBookEntry: StockBookEntry): StockBookEntry {
        return Object.assign({}, stockBookEntry);
    }
}
