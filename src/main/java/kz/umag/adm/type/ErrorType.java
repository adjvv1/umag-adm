package kz.umag.adm.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    CREATING("Ошибка создания"),
    READING("Ошибка чтения"),
    UPDATING("Ошибка обновления"),
    DELETING("Ошибка удаления"),
    UNDEFINED(null);

    private final String message;
}
