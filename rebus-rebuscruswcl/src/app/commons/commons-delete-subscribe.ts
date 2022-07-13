/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class CommonsDeleteSubscribe {
    
    public static deleteSubscribeBykey(subscribers: any, key: string) {
        for (let subscriberKey in subscribers) {
            if (subscriberKey == key) {
                subscribers[key].unsubscribe();
            }
        }
    }

    public static deleteMultipleSubscribe(subscribers: any, keyArray: any) {
        for (let subscriberKey in subscribers) {
                for (let key in keyArray) {
                    if (key == subscriberKey) {
                        subscribers[key].unsubscribe();
                        delete keyArray[key];
                    }
                }
        }
    }
}