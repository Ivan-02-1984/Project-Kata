-- Удаляем все записи, ссылающиеся на answer.id = 100
DELETE FROM votes_on_answers WHERE answer_id = 100;
DELETE FROM reputation WHERE answer_id = 100;

-- Теперь можно удалить сам ответ
DELETE FROM answer WHERE id = 100;

-- Аналогично с вопросом, если на него нет ссылок
DELETE FROM question WHERE id = 100;

-- Удаляем пользователей и роли
DELETE FROM user_entity WHERE id IN (100, 101);
DELETE FROM role WHERE id = 1;
