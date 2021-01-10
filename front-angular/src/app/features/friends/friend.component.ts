import {Component, OnInit, ViewChild} from '@angular/core';
import {AlertService} from '@app/services/ui/alert.service';
import {HomeListComponent} from '@app/features/home/modal/home-list.component';
import {JsonApiService} from '@app/services/json-api.service';

import {Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {RxStompService} from '@stomp/ng2-stompjs';
import {UserService} from '@app/services';
import {UserTokenContain} from '@app/models/UserTokenContain';

@Component({
    selector: 'app-home',
    templateUrl: './friend.component.html',
    styleUrls: ['./friend.component.css']
})
export class FriendComponent implements OnInit {

    @ViewChild(HomeListComponent) modal: HomeListComponent;


    constructor(private rxStompService: RxStompService, private userService: UserService, private http: HttpClient, private alertService: AlertService, public router: Router, private api: JsonApiService) {
    }

    ngOnInit() {
        // this.stompService.subscribe('/user/queue/friends').subscribe((msg) => {
        //     console.log(msg);
        // }, error => {
        //     console.log(error);
        // })
        // this.rxStompService.watch('/user/queue/friends',{'Authorization': token});
        this.userService.user$.pipe(filter(it => it.authorities && it.authorities.length > 0)).subscribe((sit: UserTokenContain) => {
            this.rxStompService.watch('/user/queue/friends', sit.authorizationHeaders).subscribe((wit) => {
                console.log('user queue friends-->', wit);
            });
            // ws.send("/app/friends", {'Authorization': token}, data);
            const data = {
                // id: "zz"
            };
            this.rxStompService.publish({destination: '/app/friends', body: JSON.stringify(data), headers: sit.authorizationHeaders});
        });
    }

    public send() {
        const data = {
            // id: "zz"
        };
        this.rxStompService.publish({destination: '/app/friends', body: JSON.stringify(data), headers: this.userService.userDetails.authorizationHeaders});
    }
}
