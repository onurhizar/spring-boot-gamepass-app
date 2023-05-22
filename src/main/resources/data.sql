-- User Data (admin password : z&x!HL%gV$383$UDz@Ha)
INSERT INTO users (id, email, name, surname, password_hash, role, verified, verification_code, verification_code_expire_date, created_at, updated_at)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', 'admin@mail.com', 'Onur', 'Hızar',
        '$2a$10$zMU1xrd/RVdor9D/YD8uxO7N8GlRLIW1ZRHBY4HEvKAZ/aZNlc4uG', 'ADMIN', true,
        '72014de5-1e02-4b31-b9f4-0792ce889b83', CURRENT_TIMESTAMP + INTERVAL '1 year', now(), now());

-- password : 62jryMC%jQ4N#HM@k7MZ
INSERT INTO users (id, email, name, surname, password_hash, role, verified, verification_code, verification_code_expire_date, created_at, updated_at)
VALUES ('12a06d89-97af-4fa5-a7b1-ea5c6c2e0d5e', 'mervan@mail.com', 'Hamit Mervan', 'Çelik',
        '$2a$10$OIy6it3osM29MGAICIw2veZ3uHsY3iOvZd5JuBxMWFDDbLAYvQaaK', 'ADMIN', true,
        '103195dc-3049-42a7-bbf0-4371f78327f1', CURRENT_TIMESTAMP + INTERVAL '1 year', now(), now());

-- password of rest is 123456
INSERT INTO users (id, email, name, surname, password_hash, role, verified, verification_code, verification_code_expire_date, created_at, updated_at)
VALUES ('aa59d163-5e7e-4290-b6ac-b901b0b4543a', 'test@mail.com', 'Test', 'User',
        '$2a$10$NjRTff/grP7d/87oE28BouNtRWto6WA6ApMHwGyqVDyMENh/msb4a', 'MEMBER', true,
        '06a3d67a-f588-4298-8cfc-3ee3597b0b89', CURRENT_TIMESTAMP + INTERVAL '1 year', now(), now());

INSERT INTO users (id, email, name, surname, password_hash, role, verified, verification_code, verification_code_expire_date, created_at, updated_at)
VALUES ('102b8078-276a-49e2-b1df-ad41415e32b9', 'guest@mail.com', 'Guest', 'User',
        '$2a$10$NjRTff/grP7d/87oE28BouNtRWto6WA6ApMHwGyqVDyMENh/msb4a', 'GUEST', false,
        'c4bfe252-8de9-41fa-ae94-d55d78f1adae', CURRENT_TIMESTAMP + INTERVAL '1 year', now(), now());


-- Subscription Data
INSERT INTO subscription (id, duration, is_active, monthly_fee, name, created_at, updated_at)
VALUES ('11b455da-715a-4dc1-b4f1-b526c1c9ab4e', 1, true, 100, '1 MONTH (BRONZE)', now(), now());

INSERT INTO subscription (id, duration, is_active, monthly_fee, name, created_at, updated_at)
VALUES ('33b455da-335a-4dc1-b4f1-b526c1c9ab4e', 3, true, 90, '3 MONTHS (SILVER)', now(), now());

INSERT INTO subscription (id, duration, is_active, monthly_fee, name, created_at, updated_at)
VALUES ('66b455da-665a-4dc1-b4f1-b526c1c9ab4e', 6, true, 80, '6 MONTHS (GOLD)', now(), now());


-- Category Data
INSERT INTO category (id, name, parent_id, is_super_category, created_at, updated_at)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', 'GAME', null, true, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created_at, updated_at)
VALUES ('66403305-972b-42b1-a71a-d7bb2828eebe', 'PUZZLE', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', false, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created_at, updated_at)
VALUES ('50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4', 'RACING', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', false, now(), now());

-- children of PUZZLE category
INSERT INTO category (id, name, parent_id, is_super_category, created_at, updated_at)
VALUES ('932fbf36-b7f9-4c5a-9f6d-ef8b0905e0dc', 'TRADITIONAL PUZZLE', '66403305-972b-42b1-a71a-d7bb2828eebe', false, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created_at, updated_at)
VALUES ('06046e4c-f8f1-493c-9919-49e0856462cc', 'STRATEGY PUZZLE', '66403305-972b-42b1-a71a-d7bb2828eebe', false, now(), now());

-- children of RACING category
INSERT INTO category (id, name, parent_id, is_super_category, created_at, updated_at)
VALUES ('75d1169f-11f9-4c6f-8a9d-4a30dc9bc282', 'CAR RACING', '50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4', false, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created_at, updated_at)
VALUES ('2f1036ba-44a1-4c4e-9867-18386a7d4910', 'BIKE RACING', '50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4', false, now(), now());

-- children of CAR RACING category
INSERT INTO category (id, name, parent_id, is_super_category, created_at, updated_at)
VALUES ('1d063f5a-d1f5-4388-afb5-349464a79ac7', 'RALLY RACING', '75d1169f-11f9-4c6f-8a9d-4a30dc9bc282', false, now(), now());


-- Game Data
INSERT INTO game (id, title, created_at, updated_at)
VALUES ('b4dceb23-d2ea-4432-aa7a-c71b4b15bcee', 'Portal 3', now(), now());

INSERT INTO game (id, title, created_at, updated_at)
VALUES ('cbf27a05-9abe-40c0-a943-ede62f9ca3de', 'NFS Most Wanted', now(), now());

INSERT INTO game (id, title, created_at, updated_at)
VALUES ('421450be-91e2-4184-8450-3dcc12a33e63', 'CS:GO', now(), now());

INSERT INTO game (id, title, created_at, updated_at)
VALUES ('559139b9-ff8c-4713-9000-3332dce26359', 'Sudoku', now(), now());

INSERT INTO game (id, title, created_at, updated_at)
VALUES ('3e4a7ce1-da2c-4267-9b93-96493be145f2', 'MotoGP 2023', now(), now());

INSERT INTO game (id, title, created_at, updated_at)
VALUES ('f724e3e0-d0a4-49cb-a695-5e5a184ec27f', 'Dirt Rally', now(), now());

INSERT INTO game (id, title, created_at, updated_at)
VALUES ('1f9e9b57-4f30-4366-a00b-21993248967c', 'Dirt Rally 2', now(), now());

-- categories_games join table
INSERT INTO categories_games (category_id, game_id)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', 'b4dceb23-d2ea-4432-aa7a-c71b4b15bcee'); -- game : portal 3

INSERT INTO categories_games (category_id, game_id)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', 'cbf27a05-9abe-40c0-a943-ede62f9ca3de'); -- game : nfs mw

INSERT INTO categories_games (category_id, game_id)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', '421450be-91e2-4184-8450-3dcc12a33e63'); -- game : csgo

INSERT INTO categories_games (category_id, game_id)
VALUES ('66403305-972b-42b1-a71a-d7bb2828eebe', 'b4dceb23-d2ea-4432-aa7a-c71b4b15bcee'); -- puzzle : portal 3

INSERT INTO categories_games (category_id, game_id)
VALUES ('932fbf36-b7f9-4c5a-9f6d-ef8b0905e0dc', '559139b9-ff8c-4713-9000-3332dce26359'); -- traditional puzzle : sudoku

INSERT INTO categories_games (category_id, game_id)
VALUES ('2f1036ba-44a1-4c4e-9867-18386a7d4910', '3e4a7ce1-da2c-4267-9b93-96493be145f2'); -- bike racing : MotoGP 2023

INSERT INTO categories_games (category_id, game_id)
VALUES ('1d063f5a-d1f5-4388-afb5-349464a79ac7', 'f724e3e0-d0a4-49cb-a695-5e5a184ec27f'); -- rally racing : Dirt Rally

INSERT INTO categories_games (category_id, game_id)
VALUES ('1d063f5a-d1f5-4388-afb5-349464a79ac7', '1f9e9b57-4f30-4366-a00b-21993248967c'); -- rally racing : Dirt Rally 2


-- User Interest : favorite games : users_games join table
INSERT INTO users_games (user_id, game_id)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', 'b4dceb23-d2ea-4432-aa7a-c71b4b15bcee'); -- admin favorite portal 3

INSERT INTO users_games (user_id, game_id)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', 'cbf27a05-9abe-40c0-a943-ede62f9ca3de'); -- admin favorites nfs mw


-- User Interest : following categories : users_categories join table
INSERT INTO users_categories (user_id, category_id)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', '66403305-972b-42b1-a71a-d7bb2828eebe'); -- admin follows PUZZLE

INSERT INTO users_categories (user_id, category_id)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', '50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4'); -- admin follows RACING
