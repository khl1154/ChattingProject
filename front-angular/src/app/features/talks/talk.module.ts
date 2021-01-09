import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {routing} from './talk.routing';
import {TalkComponent} from './talk.component';
import {HomeListComponent} from '@app/features/talks/modal/home-list.component';
import {SharedModule} from '@app/shareds/shared.module';

@NgModule({
    imports: [
        CommonModule,
        routing,
        SharedModule,
    ],
    declarations: [TalkComponent, HomeListComponent]
})
export class TalkModule {
}
