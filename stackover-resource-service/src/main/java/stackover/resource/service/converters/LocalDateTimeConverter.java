package stackover.resource.service.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Конвертер для автоматического преобразования между {@link LocalDateTime} и {@link Timestamp} в JPA-сущностях.
 * Используется для корректного хранения даты и времени в базе данных.
 *
 * Аннотация {@link Converter} с параметром {@code autoApply = true}
 * позволяет применять конвертер ко всем полям типа {@link LocalDateTime}.
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    /**
     * Конвертирует {@link LocalDateTime} в {@link Timestamp} для сохранения в БД.
     *
     * @param attribute Локальная дата и время (может быть {@code null}).
     * @return Соответствующий {@link Timestamp} или {@code null}, если передан {@code null}.
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute != null ? Timestamp.valueOf(attribute) : null;
    }

    /**
     * Конвертирует {@link Timestamp} из БД в {@link LocalDateTime}.
     *
     * @param dbData Значение из базы данных (может быть {@code null}).
     * @return Соответствующая {@link LocalDateTime} или {@code null}, если передан {@code null}.
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return dbData != null ? dbData.toLocalDateTime() : null;
    }
}