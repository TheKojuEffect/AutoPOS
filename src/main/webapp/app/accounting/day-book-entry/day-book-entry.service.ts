import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { DayBookEntry } from './day-book-entry.model';
import { createRequestOption, ResponseWrapper } from '../../shared';

@Injectable()
export class DayBookEntryService {

    private resourceUrl = 'api/day-book-entries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) {
    }

    create(dayBookEntry: DayBookEntry): Observable<DayBookEntry> {
        const copy = this.convert(dayBookEntry);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(dayBookEntry: DayBookEntry): Observable<DayBookEntry> {
        const copy = this.convert(dayBookEntry);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<DayBookEntry> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.date = this.dateUtils
            .convertLocalDateFromServer(entity.date);
    }

    private convert(dayBookEntry: DayBookEntry): DayBookEntry {
        const copy: DayBookEntry = Object.assign({}, dayBookEntry);
        copy.date = this.dateUtils
            .convertLocalDateToServer(dayBookEntry.date);
        return copy;
    }
}
