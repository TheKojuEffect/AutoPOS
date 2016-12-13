import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';
import { UpgradeModule } from '@angular/upgrade/static';

// depending on the env mode, enable prod mode or add debugging modules
if (process.env.ENV === 'build') {
    enableProdMode();
}

export function main() {
    return platformBrowserDynamic().bootstrapModule(AppModule)
        .then(platformRef => {
            const upgrade = platformRef.injector.get(UpgradeModule) as UpgradeModule;
            upgrade.bootstrap(document.body, ['autopos']);
        })
        .then(success => console.log('Bootstrap Success'))
        .catch(err => console.error(err));

}

if (document.readyState === 'complete') {
    main();
} else {
    document.addEventListener('DOMContentLoaded', main);
}
