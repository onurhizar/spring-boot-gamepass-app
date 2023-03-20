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

