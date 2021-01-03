import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/index';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {ApiHttpOption, JsonApiService} from '@app/services/json-api.service';
import {map} from 'rxjs/internal/operators/map';
import {AlertService} from '@app/services/ui/alert.service';
import * as url from 'url';
import {Token, User} from "@generate/models";

declare let $: any;
declare let moment: any;

// export type AuthDetailsOption = { hddnCd?: UseCd, prntUrlSeq?: number, crudTypeCd?: CrudTypeCd };

const defaultUser = {
    id: 'Guest'
} as User;

// const defaultUser = userDetails;

@Injectable()
export class UserService  { // implements CanActivate

    public user$ = new BehaviorSubject<User>(Object.assign({}, defaultUser) as User);

    constructor(private http: HttpClient, private alertService: AlertService, private api: JsonApiService, private httpClient: HttpClient, private router: Router) {
        this.reloadUserDetails();
    }

    public next(userDetails: User) {
        this.user$.next(userDetails);
    }

    public setUserDetails(userDetails: User) {
        this.user$.next(userDetails);
    }

    get userDetails(): User {
        return this.user$.getValue();
    }

    public login(username: string, password: string) {
        const params = {username, password}
        this.api.post<Token>('/securitys/user-sign-in',{params}).subscribe((it: Token) => {
            window.localStorage.setItem('token', it.token);
            this.reloadUserDetails();
        }, this.api.errorHandler.bind(this.api));
    }

    public reloadUserDetails() {
        let token = window.localStorage.getItem('token');

        let headers: HttpHeaders = new HttpHeaders();
        headers = headers.append('Authorization', token);


        this.api.post<User>('/apis/auths/details',{headers}).subscribe((it: User) => {
            it = Object.assign({}, it);
            if (it.role) {
                this.user$.next(it);
            } else {
                this.router.navigate(['/login'], {queryParams: {type: 'sign-out'}});
            }
        }, (err: HttpErrorResponse) => {
            this.api.errorHandler(err);
            this.router.navigate(['/login'], {queryParams: {type: 'sign-out'}});
        });

    }



}
