import { BaseRequestOptions } from '@angular/http';
import { Injectable } from '@angular/core';

@Injectable()
export class CustomRequestOptions extends BaseRequestOptions {
    constructor() {
        super();
        this.headers.append('Authorization', 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTQ4NDA0NTg1M30.3GFx5JtTdXoMQ1QUI505wO8PP6YprWHdAgAeUFWyBxWtdvve1mCtsEutBH0Wdil2hgplVfqNcdZLu1-OUYybrQ');
    }
}
