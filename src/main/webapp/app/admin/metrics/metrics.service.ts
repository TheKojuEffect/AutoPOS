import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

@Injectable()
export class AposMetricsService {

    constructor(private http: Http) {}

    getMetrics(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'management/metrics').map((res: Response) => res.json());
    }

    threadDump(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'management/dump').map((res: Response) => res.json());
    }
}
