import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { JhiDateUtils } from 'ng-jhipster';

import { DayBookEntry } from './day-book-entry.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DayBookEntry>;

@Injectable()
export class DayBookEntryService {

    private resourceUrl = 'api/day-book-entries';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {
    }

    create(dayBookEntry: DayBookEntry): Observable<EntityResponseType> {
        const copy = this.convert(dayBookEntry);
        return this.http.post<DayBookEntry>(this.resourceUrl, copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dayBookEntry: DayBookEntry): Observable<EntityResponseType> {
        const copy = this.convert(dayBookEntry);
        return this.http.put<DayBookEntry>(this.resourceUrl, copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DayBookEntry>(`${this.resourceUrl}/${id}`, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DayBookEntry[]>> {
        const options = createRequestOption(req);
        return this.http.get<DayBookEntry[]>(this.resourceUrl, {params: options, observe: 'response'})
            .map((res: HttpResponse<DayBookEntry[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DayBookEntry = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DayBookEntry[]>): HttpResponse<DayBookEntry[]> {
        const jsonResponse: DayBookEntry[] = res.body;
        const body: DayBookEntry[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DayBookEntry.
     */
    private convertItemFromServer(dayBookEntry: DayBookEntry): DayBookEntry {
        const copy: DayBookEntry = Object.assign({}, dayBookEntry);
        copy.date = this.dateUtils
            .convertLocalDateFromServer(dayBookEntry.date);
        return copy;
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
