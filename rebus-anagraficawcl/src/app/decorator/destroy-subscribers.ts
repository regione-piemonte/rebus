/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Subscriber } from "rxjs";

// creating the decorator DestroySubscribers
export function DestroySubscribers() {  
  return function (target: any) {
    // decorating the function ngOnDestroy
    target.prototype.ngOnDestroy = ngOnDestroyDecorator(target.prototype.ngOnDestroy);

    // decorator function
    function ngOnDestroyDecorator(f) {
      return function () {

        // saving the result of ngOnDestroy performance to the variable superData 
        let superData = f ? f.apply(this, arguments) : null;

        // unsubscribing
        for (let subscriberKey in this.subscribers) {
          let subscriber = this.subscribers[subscriberKey];
          if (subscriber instanceof Subscriber) {
            subscriber.unsubscribe();
          }
        }

        // returning the result of ngOnDestroy performance
        return superData;
      }
    }

    // returning the decorated class
    return target;
  }
}