INSERT INTO spare_parts (id, created_by, created_when, amount, code, name, price, unit, warehouse_id)
VALUES (1, 'Elena M', now(), 5, '99925', 'Масло трансмиссионное 80W-90 GL-4 FAVORIT', 500, 1, 1),
       (2, 'Elena M', now(), 5, '0888581026', 'Масло трансмиссионное  75w-90', 3500, 1, 1),
       (3, 'Elena M', now(), 2, '0121938', 'Набор фильтров салона', 2000, 0, 1),
       (4, 'Elena M', now(), 4, '232341', 'Масленный фильтр', 800, 0, 1),
       (5, 'Elena M', now(), 4, '24531341', 'Тормозные колодки', 2800, 0, 1),
       (6, 'Elena M', now(), 10, '02912', 'Свечи', 1100, 0, 1),
       (7, 'Elena M', now(), 2, '123832', 'Сигнализация', 11000, 0, 1),
       (8, 'Elena M', now(), 2, '4521', 'Коврики в салон', 1500, 0, 2),
       (9, 'Elena M', now(), 5, '76321', 'Чехлы фирменные', 5500, 0, 2),
       (10, 'Elena M', now(), 1, '414521', 'Детское кресло', 8500, 0, 2),
       (11, 'Elena M', now(), 10, '414522', 'Сувенирная продукция', 500, 0, 2);

SELECT setval('spare_parts_seq', 11);