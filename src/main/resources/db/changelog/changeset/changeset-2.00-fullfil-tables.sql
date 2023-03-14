--liquibase formatted sql

--changeset gznznzjsn:fulfill-tasks-and-requirements

insert into tasks (name, duration, cost_per_hour, specialization)
values ('painting', 3, 10000, 'CLEANER'),
       ('tire replacement', 1, 2000, 'REPAIRER'),
       ('technical inspection', 4, 1000, 'INSPECTOR'),
       ('headlight replacement', 2, 10000, 'CLEANER');
