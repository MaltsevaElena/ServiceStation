INSERT INTO services (id, created_by, created_when, code, name, service_station_id, rate_hour)
VALUES (1, 'Elena M', now(), 'SVD001', 'Диагностика на стенде', 1, 0.4),
       (2, 'Elena M', now(), 'SVD002', 'Диагностика электрики', 1, 2),
       (3, 'Elena M', now(), 'SVH001', 'Шиномoнтаж', 1, 0.8),
       (4, 'Elena M', now(), 'SVU001', 'Установка автосигнализации', 1, 3.5),
       (5, 'Elena M', now(), 'SVM001', 'Замена масла', 1, 0.2),
       (6, 'Elena M', now(), 'SVF001', 'Замена фильтров', 1, 0.3),
       (7, 'Elena M', now(), 'SVS001', 'Замена свечей', 1, 0.5),
       (8, 'Elena M', now(), 'SVA001', 'Замена амортизаторов', 1, 1.5),
       (9, 'Elena M', now(), 'SVZ001', 'Замена тормозных колодок', 1, 1.7),
       (10, 'Elena M', now(), 'SVR001', 'Регулировка фар', 1, 0.7),
       (11, 'Elena M', now(), 'SVR002', 'Ремонт КПП', 1, 2.4);

SELECT setval('services_seq', 11);