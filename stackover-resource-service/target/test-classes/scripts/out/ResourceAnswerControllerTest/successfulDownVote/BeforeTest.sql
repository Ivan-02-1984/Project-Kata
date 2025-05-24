DELETE FROM role WHERE id = 1;
INSERT INTO role (id, name) VALUES (1, 'USER');

-- Создаем пользователей
INSERT INTO user_entity (id, email, password, full_name, persist_date, last_redaction_date, is_enabled, nickname, role_id)
VALUES
    (100, 'author@example.com', 'password', 'Автор Ответа', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'author', 1),
    (101, 'voter@example.com', 'password', 'Голосующий', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'voter', 1);

-- Создаем вопрос
INSERT INTO question (id, user_id, title, description, persist_date, last_redaction_date, is_deleted)
VALUES (100, 100, 'Тестовый вопрос', 'Описание вопроса', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);

-- Создаем ответ
INSERT INTO answer (id, user_id, question_id, html_body, persist_date, update_date, is_deleted, is_deleted_by_moderator, is_helpful)
VALUES (100, 100, 100, 'Текст ответа', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, false, false);

-- Инициализируем репутацию (укажите sender_id, например, того же user 101)
INSERT INTO reputation (id, author_id, sender_id, count, type, persist_date)
VALUES (100, 100, 101, 100, 2, CURRENT_TIMESTAMP);