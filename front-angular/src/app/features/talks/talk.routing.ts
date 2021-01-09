import {RouterModule, Routes} from '@angular/router';
import {TalkComponent} from './talk.component';
import {ModuleWithProviders} from '@angular/core';
export const homeRoutes: Routes = [
    {
        path: '',
        component: TalkComponent,
        data: {
            pageTitle: 'talks'
        },
    }
];

export const routing: ModuleWithProviders = RouterModule.forChild(homeRoutes);

