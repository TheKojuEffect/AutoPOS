import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class ActivateService {

    constructor(private http: HttpClient) {
    }

    get(key: string): Observable<any> {
        return this.http.get('api/activate', {
            params: new HttpParams().set('key', key)
        });
    }
}
