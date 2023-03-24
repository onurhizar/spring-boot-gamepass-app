-- User Data (admin pass is 123456)
INSERT INTO users (id, email, name, surname, password_hash, role, verified)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', 'admin@mail.com', 'Onur', 'Hızar',
        '$2a$10$NjRTff/grP7d/87oE28BouNtRWto6WA6ApMHwGyqVDyMENh/msb4a', 'ADMIN', true);

INSERT INTO users (id, email, name, surname, password_hash, role, verified)
VALUES ('aa59d163-5e7e-4290-b6ac-b901b0b4543a', 'test@mail.com', 'Test', 'User',
        '$2a$10$NjRTff/grP7d/87oE28BouNtRWto6WA6ApMHwGyqVDyMENh/msb4a', 'MEMBER', true);

INSERT INTO users (id, email, name, surname, password_hash, role, verified)
VALUES ('102b8078-276a-49e2-b1df-ad41415e32b9', 'guest@mail.com', 'Guest', 'User',
        '$2a$10$NjRTff/grP7d/87oE28BouNtRWto6WA6ApMHwGyqVDyMENh/msb4a', 'GUEST', false);


-- Category Data
INSERT INTO category (id, name, parent_id, is_super_category)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', 'GAME', null, true);

INSERT INTO category (id, name, parent_id, is_super_category)
VALUES ('66403305-972b-42b1-a71a-d7bb2828eebe', 'PUZZLE', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', false);

INSERT INTO category (id, name, parent_id, is_super_category)
VALUES ('50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4', 'RACING', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', false);


-- Game Data
INSERT INTO game (id, title)
VALUES ('b4dceb23-d2ea-4432-aa7a-c71b4b15bcee', 'Portal 3');

INSERT INTO game (id, title)
VALUES ('cbf27a05-9abe-40c0-a943-ede62f9ca3de', 'NFS Most Wanted');

INSERT INTO game (id, title)
VALUES ('421450be-91e2-4184-8450-3dcc12a33e63', 'CS:GO');


-- categories_games join table
INSERT INTO categories_games (category_id, game_id)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', 'b4dceb23-d2ea-4432-aa7a-c71b4b15bcee'); -- game : portal 3

INSERT INTO categories_games (category_id, game_id)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', 'cbf27a05-9abe-40c0-a943-ede62f9ca3de'); -- game : nfs mw

INSERT INTO categories_games (category_id, game_id)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', '421450be-91e2-4184-8450-3dcc12a33e63'); -- game : csgo

INSERT INTO categories_games (category_id, game_id)
VALUES ('66403305-972b-42b1-a71a-d7bb2828eebe', 'b4dceb23-d2ea-4432-aa7a-c71b4b15bcee'); -- puzzle : portal 3