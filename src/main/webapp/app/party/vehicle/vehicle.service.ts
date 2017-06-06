import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Vehicle } from './vehicle.model';
import { createRequestOption, ResponseWrapper } from '../../shared';

@Injectable()
export class VehicleService {

    private resourceUrl = 'api/vehicles';

    constructor(private http: Http) {
    }

    create(vehicle: Vehicle): Observable<Vehicle> {
        const copy = this.convert(vehicle);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(vehicle: Vehicle): Observable<Vehicle> {
        const copy = this.convert(vehicle);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Vehicle> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    search(term: string): Observable<Vehicle[]> {
        return this.http
            .get(`${this.resourceUrl}?q=${term}`)
            .map(res => res.json() as Vehicle[]);
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
            }

    private convert(vehicle: Vehicle): Vehicle {
        const copy: Vehicle = Object.assign({}, vehicle);
        return copy;
    }
}
