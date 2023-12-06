CREATE TABLE IF NOT EXISTS umag_adm.offset_job
(
    id                INTEGER AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(25) NOT NULL COMMENT 'Уникальное наименование задания',
    offset            INTEGER     NOT NULL COMMENT 'Смещение',
    size              INTEGER     NOT NULL COMMENT 'Количество запрашиваемых данных (limit)',
    status            VARCHAR(10),
    executed_datetime DATETIME,
    UNIQUE (name)
) COMMENT 'adm / service_offset: Служебная таблица фиксации смещения';

-- ROLLBACK SQL SCRIPT
-- DROP TABLE IF EXISTS umag_adm.job_offset;
