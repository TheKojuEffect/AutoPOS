import { platformBrowser } from '@angular/platform-browser';
import { ProdConfig } from './blocks/config/prod.config';
import { AutoPosAppModuleNgFactory } from '../../../../build/aot/src/main/webapp/app/app.module.ngfactory';

ProdConfig();

platformBrowser().bootstrapModuleFactory(AutoPosAppModuleNgFactory)
.then((success) => console.log(`Application started`))
.catch((err) => console.error(err));
