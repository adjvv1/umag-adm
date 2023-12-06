CREATE SCHEMA IF NOT EXISTS umag_adm;

CREATE TABLE IF NOT EXISTS umag_adm.store_activity
(
    id                 INTEGER AUTO_INCREMENT PRIMARY KEY,
    store_id           INTEGER  NOT NULL COMMENT 'Идентификатор магазина',
    last_activity_date DATE COMMENT 'Дата последнего зафискированного действия',
    create_datetime    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_datetime    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    UNIQUE (store_id)
) COMMENT 'adm / store_activity: Последний зафиксированные день когда магазин был активен';

-- ROLLBACK SQL SCRIPT
-- DROP TABLE IF EXISTS umag_adm.store_activity;
