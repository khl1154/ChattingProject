import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainLayoutComponent} from '@app/shareds/layout/app-layouts/main-layout.component';
import {EmptyLayoutComponent} from '@app/layouts/empty/layout/empty-layout.component';
import {UserService} from '@app/services/user.service';
import {PageNotFoundComponent} from '@app/features/errors/page-not-found/page-not-found.component';

const routes: Routes = [
    {
        path: '',
        component: EmptyLayoutComponent,
        loadChildren: () => import('./features/auths/auth.module').then(m => m.AuthModule),
    },
    {
        path: '',
        component: MainLayoutComponent,
        // data: {pageTitle: 'Home'},
        children: [
            {
                path: 'home',
                loadChildren: () => import('./features/home/home.module').then(m => m.HomeModule),
            },
            // {
            //     path: 'users',
            //     loadChildren: () => import('./features/brds/brd.module').then(m => m.BrdModule),
            //     canActivate: [UserService]
            // },
            // {
            //     path: 'setups',
            //     loadChildren: () => import('./features/setups/setup.module').then(m => m.SetupModule),
            //     canActivate: [UserService]
            // },
        ]
    },

    {path: '**', component: PageNotFoundComponent}

];

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true, enableTracing: false})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
