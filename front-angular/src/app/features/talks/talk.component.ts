import {Component, OnInit, ViewChild} from '@angular/core';
import {AlertService} from '@app/services/ui/alert.service';
import {HomeListComponent} from '@app/features/home/modal/home-list.component';
import {JsonApiService} from '@app/services/json-api.service';

import {Router} from '@angular/router';
import {asyncScheduler, Observable, of, EMPTY, zip, pipe, combineLatest, concat, merge} from 'rxjs';
import {finalize, mergeMap, map, tap, subscribeOn, startWith, concatAll, filter} from 'rxjs/operators';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {UserTokenContain} from '@app/models/UserTokenContain';
import {UserService, WsService} from '@app/services';
import {com} from '@generate/models';
import Room = com.clone.chat.domain.Room;
import User = com.clone.chat.domain.User;
import {BasicModalComponent, ButtonsClickType} from '@app/shareds/modals/basic-modal/basic-modal.component';
import { v4 as uuidv4 } from 'uuid';
class UserCheck extends User {
    value: boolean;
}
@Component({
    selector: 'app-home',
    templateUrl: './talk.component.html',
    styleUrls: ['./talk.component.css']
})
export class TalkComponent implements OnInit {

    @ViewChild('modal') modal: BasicModalComponent;
    private rooms: Room[];
    private friends: UserCheck[];
    private newRoomName = uuidv4();


    constructor(private userService: UserService, private wsService: WsService, private alertService: AlertService, public router: Router, private api: JsonApiService) {
        this.userService.user$.pipe(filter(it => it.authorities && it.authorities.length > 0)).subscribe((userTokenContain: UserTokenContain) => {
            this.wsService.watch<Room[]>('/user/queue/rooms').subscribe((it) => {
                this.rooms = it;
            });

            this.wsService.watch<string>(`/topic/rooms/${userTokenContain.authorizationHeaders}`).subscribe((it) => {
                console.log(it);
            });
            this.send();
        });
    }

    ngOnInit() {}

    send() {
        this.wsService.publish('/app/rooms');
    }

    public getUser() {
        this.wsService.publish('/app/friends');
    }

    createRoom() {
        this.api.get<User[]>('/apis/friends').subscribe(it => {
            this.friends = it.map(sit => Object.assign(new UserCheck(), sit));
            this.newRoomName = uuidv4();
        });
    }

    check($event: ButtonsClickType) {
        if ($event.name === 'ok') {
            console.log(this.friends);
            const data = {
                name: this.newRoomName,
                users: this.friends.filter(it => it.value).map(it => it.id)
            };

            this.wsService.publish('/app/rooms/create-room', data);
            this.modal.close();
        }
    }

    create() {
        this.wsService.publish('/app/rooms/create-room');
    }
}
