-- Удалить из таблицы репутации
DELETE FROM reputation WHERE id = 100;

-- Удалить из таблицы голосов за ответы (vote_answer)
DELETE FROM votes_on_answers WHERE answer_id = 100 OR user_id = 100;

-- Удалить сам ответ
DELETE FROM answer WHERE id = 100;

-- Удалить вопрос
DELETE FROM question WHERE id = 100;

-- Удалить пользователя
DELETE FROM user_entity WHERE id = 100;

-- Удалить роль, если она была создана только для тестов
DELETE FROM role WHERE id = 1;
