-- Очистка данных (удалять в правильном порядке!)
DELETE FROM reputation WHERE id IN (100, 101);
DELETE FROM votes_on_answers WHERE id = 1;
DELETE FROM answer WHERE id = 100;
DELETE FROM question WHERE id = 100;
DELETE FROM user_entity WHERE id IN (100, 101);
DELETE FROM role WHERE id = 1;

-- Создание роли
INSERT INTO role (id, name) VALUES (1, 'USER');

-- Создание пользователей
INSERT INTO user_entity (id, email, password, full_name, persist_date, last_redaction_date, is_enabled, nickname, role_id)
VALUES
    (100, 'author@example.com', 'password', 'Автор Ответа', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'author', 1),
    (101, 'voter@example.com', 'password', 'Голосующий', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'voter', 1);

-- Создание вопроса
INSERT INTO question (id, user_id, title, description, persist_date, last_redaction_date, is_deleted)
VALUES (100, 100, 'Тестовый вопрос', 'Описание вопроса', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);

-- Создание ответа
INSERT INTO answer (id, user_id, question_id, html_body, persist_date, update_date, is_deleted, is_deleted_by_moderator, is_helpful)
VALUES (100, 100, 100, 'Текст ответа', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, false, false);

-- Предыдущий upvote
INSERT INTO votes_on_answers (id, user_id, answer_id, vote_type_answer, persist_date)
VALUES (1, 101, 100, 'UP', CURRENT_TIMESTAMP);

-- Репутация за upvote
INSERT INTO reputation (id, author_id, sender_id, count, type, persist_date, answer_id)
VALUES (100, 100, 101, 10, 2, CURRENT_TIMESTAMP, 100);
