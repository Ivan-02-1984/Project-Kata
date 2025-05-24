DELETE FROM role WHERE id = 1;
INSERT INTO role (id, name) VALUES (1, 'USER');

-- Создаем пользователей, ответ 999 не создаем
INSERT INTO user_entity (
    id, email, password, full_name, persist_date,
    is_enabled, nickname, role_id, last_redaction_date
) VALUES (
             100, 'author@example.com', 'password', 'Автор Ответа', CURRENT_TIMESTAMP,
             true, 'author', 1, CURRENT_TIMESTAMP
         );

-- Вопрос
INSERT INTO question (id, user_id, title, description, persist_date, last_redaction_date, is_deleted)
VALUES (100, 100, 'Тестовый вопрос', 'Описание вопроса', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);