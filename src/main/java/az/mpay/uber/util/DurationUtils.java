/*
 * Copyright © 2013-2021, TechFin UAB. All rights reserved.
 * SDK.finance® is registered trademark owned by TechFin UAB.
 * This file is part of the SDK.finance® product.
 * Unauthorised copying of this file, via any medium, modification and/or any type of its distribution
 * is strictly prohibited.
 * Proprietary and confidential.
 */

package az.mpay.uber.util;

import az.mpay.uber.exception.DurationFormatException;
import lombok.NonNull;

import java.time.Duration;
import java.time.format.DateTimeParseException;

/**
 * Util class to work with Duration
 */
public class DurationUtils {

    public static Duration parse(@NonNull String durationExpression,
                                 @NonNull String defaultExpression) {
        try {
            return Duration.parse(durationExpression);
        } catch (DateTimeParseException e) {
            try {
                return Duration.parse(defaultExpression);
            } catch (DateTimeParseException e2) {
                throw new DurationFormatException("Format of duration expressions is invalid");
            }
        }
    }
}
