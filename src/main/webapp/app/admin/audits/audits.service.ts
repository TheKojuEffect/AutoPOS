import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { Audit } from './audit.model';
import { createRequestOption} from '../../shared';

@Injectable()
export class AuditsService  {
    constructor(private http: HttpClient) { }

    query(req: any): Observable<HttpResponse<Audit[]>> {
        const params: HttpParams = createRequestOption(req);

        const requestURL = 'management/audits';

        return this.http.get<Audit[]>(requestURL, {
            params,
            observe: 'response'
        });
    }
}
