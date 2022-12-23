INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (1, 'Elena M', now(), '2022-11-25', 1200, '2022-08-18', 1);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (2, 'Elena M', now(), null, 2400, '2022-11-26', 1);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (3, 'Elena M', now(), null, 2400, '2022-11-26', 2);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (4, 'Elena M', now(), null, 2400, '2022-11-26', 3);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (5, 'Elena M', now(), null, 2400, '2022-11-26', 4);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (6, 'Elena M', now(), null, 2400, '2022-11-26', 5);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (7, 'Elena M', now(), null, 2400, '2022-11-26', 6);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (8, 'Elena M', now(), null, 2400, '2022-11-26', 7);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (9, 'Elena M', now(), null, 2400, '2022-11-26', 8);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (10, 'Elena M', now(), null, 2200, '2022-11-26', 9);

INSERT INTO tariffs (id, created_by, created_when, end_date, price, start_date, service_station_id)
VALUES (11, 'Elena M', now(), null, 2000, '2022-11-26', 10);


SELECT setval('tariff_seq', 11);