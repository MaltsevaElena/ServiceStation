insert into service_books (id, created_by, created_when, amount_spare_part, code_spare_part, mileage_car,
                           name_spare_part, repair_date, unit_spare_part, car_id)
VALUES (2, 'Елена М', now(), 2, 'werdsf231', 2000, 'Лампочка', now(), 0, 1),
       (3, 'Елена М', now(), 5, '2w412erds2', 5000, 'Масло', now(), 1, 1),
       (4, 'Елена М', now(), 1, '234234', 10000, 'Лампочка', now(), 0, 2),
       (5, 'Елена М', now(), 2, 'w123erdsf231', 20000, 'Лампочка', now(), 0, 2),
       (6, 'Елена М', now(), 2, 'wer1dsf231', 20000, 'Лампочка', now(), 0, 3),
       (7, 'Елена М', now(), 5, '2w412erds2', 5000, 'Масло', now(), 1, 4),
       (8, 'Елена М', now(), 1, '234234', 10000, 'Лампочка', now(), 0, 4),
       (9, 'Елена М', now(), 5, '2w412erds2', 5000, 'Масло', now(), 1, 5),
       (10, 'Елена М', now(), 1, '234234', 10000, 'Лампочка', now(), 0, 5);

SELECT setval('service_books', 11);