import {AlertService} from '@app/services/ui/alert.service';
import {JsonApiService} from '@app/services/json-api.service';
import {UserService} from '@app/services/user.service';
import {MomentService} from '@app/services/date/moment.service';

export const services = [
    AlertService,
    JsonApiService,
    UserService,
    MomentService,
];
//
export * from './ui/alert.service';
export * from './json-api.service';
export * from './user.service';
// export * from "./auth-token.service";
// export * from "./auth.service";
// export * from "./token.interceptor";
// export * from "./json-api.service";
// export * from "./user.service";
// export * from "./chat.service";
// export * from "./notification.service";
// export * from "./body.service";
// export * from "./layout.service";
// export * from "./sound.service";
// export * from "./voice";
// export * from "./cms-common.service";
// export * from "./validation.service";
