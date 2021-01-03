import {Component, OnInit} from '@angular/core';
import {UserService} from '@app/services/user.service';
import {filter} from 'rxjs/operators';

declare let $: any;
@Component({
    selector: 'component-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

    constructor(public userService: UserService) {
    }

    ngOnInit() {
    }

    // https://getbootstrap.com/docs/4.0/components/collapse/
    closeCollapse() {
        $('.collapse').collapse('hide');
    }
}
