DELETE FROM votes_on_answers WHERE answer_id = 100;
DELETE FROM reputation WHERE author_id IN (100, 101);
DELETE FROM answer WHERE id = 100;
DELETE FROM question WHERE id = 100;
DELETE FROM user_entity WHERE id IN (100, 101);
DELETE FROM role WHERE id = 1;