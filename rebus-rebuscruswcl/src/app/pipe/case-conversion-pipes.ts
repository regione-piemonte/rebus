/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import {Pipe, PipeTransform} from '@angular/core';
import {invalidPipeArgumentError} from './invalid_pipe_argument_error';

/**
 * Transforms text to lowercase.
 *
 * {@example  common/pipes/ts/lowerupper_pipe.ts region='LowerUpperPipe' }
 *
 * @stable
 */
@Pipe({name: 'lowercase'})
export class LowerCasePipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return value;
    if (typeof value !== 'string') {
      throw invalidPipeArgumentError(LowerCasePipe, value);
    }
    return value.toLowerCase();
  }
}


/**
 * Helper method to transform a single word to titlecase.
 *
 * @stable
 */
function titleCaseWord(word: string) {
  if (!word) return word;
  return word[0].toUpperCase() + word.substr(1).toLowerCase();
}

/**
 * Transforms text to titlecase.
 *
 * @stable
 */
@Pipe({name: 'titlecase'})
export class TitleCasePipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return value;
    if (typeof value !== 'string') {
      throw invalidPipeArgumentError(TitleCasePipe, value);
    }

    return value.replace(/\w\S*/g, (word => titleCaseWord(word)));
  }
}

/**
 * Transforms text to uppercase.
 *
 * @stable
 */
@Pipe({name: 'uppercase'})
export class UpperCasePipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return value;
    if (typeof value !== 'string') {
      throw invalidPipeArgumentError(UpperCasePipe, value);
    }
    return value.toUpperCase();
  }
}