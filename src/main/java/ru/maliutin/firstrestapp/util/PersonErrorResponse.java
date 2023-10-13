package ru.maliutin.firstrestapp.util;

/**
 * Класс необходимый для Jackson для создания JSON ответа
 * с сообщением о выброшенном исключении. Должен иметь геттеры и сеттеры для ВСЕХ полей
 */
public class PersonErrorResponse {
    private String message;  // Поле с сообщением об ошибке
    private long timestamp;  // Время в которое произошла ошибка в миллисекундах

    public PersonErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
