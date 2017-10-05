import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DayBookEntry } from './day-book-entry.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DayBookEntryService {

    private resourceUrl = SERVER_API_URL + 'api/day-book-entries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(dayBookEntry: DayBookEntry): Observable<DayBookEntry> {
        const copy = this.convert(dayBookEntry);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(dayBookEntry: DayBookEntry): Observable<DayBookEntry> {
        const copy = this.convert(dayBookEntry);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DayBookEntry> {
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
     * Convert a returned JSON object to DayBookEntry.
     */
    private convertItemFromServer(json: any): DayBookEntry {
        const entity: DayBookEntry = Object.assign(new DayBookEntry(), json);
        entity.date = this.dateUtils
            .convertLocalDateFromServer(json.date);
        return entity;
    }

    /**
     * Convert a DayBookEntry to a JSON which can be sent to the server.
     */
    private convert(dayBookEntry: DayBookEntry): DayBookEntry {
        const copy: DayBookEntry = Object.assign({}, dayBookEntry);
        copy.date = this.dateUtils
            .convertLocalDateToServer(dayBookEntry.date);
        return copy;
    }
}
