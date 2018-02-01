import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { Brand } from './brand.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Brand>;

@Injectable()
export class BrandService {

    private resourceUrl = 'api/brands';

    constructor(private http: HttpClient) { }

    create(brand: Brand): Observable<EntityResponseType> {
        const copy = this.convert(brand);
        return this.http.post<Brand>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(brand: Brand): Observable<EntityResponseType> {
        const copy = this.convert(brand);
        return this.http.put<Brand>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Brand>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Brand[]>> {
        const options = createRequestOption(req);
        return this.http.get<Brand[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Brand[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Brand = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Brand[]>): HttpResponse<Brand[]> {
        const jsonResponse: Brand[] = res.body;
        const body: Brand[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Brand.
     */
    private convertItemFromServer(brand: Brand): Brand {
        const copy: Brand = Object.assign({}, brand);
        return copy;
    }

    /**
     * Convert a Brand to a JSON which can be sent to the server.
     */
    private convert(brand: Brand): Brand {
        const copy: Brand = Object.assign({}, brand);
        return copy;
    }
}
