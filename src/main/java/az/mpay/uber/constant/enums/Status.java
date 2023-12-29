/*
 * Copyright © 2013-2021, TechFin UAB. All rights reserved.
 * SDK.finance® is registered trademark owned by TechFin UAB.
 * This file is part of the SDK.finance® product.
 * Unauthorised copying of this file, via any medium, modification and/or any type of its distribution
 * is strictly prohibited.
 * Proprietary and confidential.
 */

package az.mpay.uber.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    created(0),
    processing(1),
    processed(2),
    declined(3),
    exception(4);

    private final int code;
}
