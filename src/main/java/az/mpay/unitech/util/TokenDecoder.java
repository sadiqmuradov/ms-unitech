package az.mpay.unitech.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenDecoder {

    public static String decodePattern(@NonNull String pattern) {

        return new String(Base64.getDecoder().decode(pattern.trim()), StandardCharsets.UTF_8);
    }
}
