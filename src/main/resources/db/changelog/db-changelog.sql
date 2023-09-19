--liquibase formatted sql
-- Game event log

-- changeset me.elgregos:create-game-event-sequence
create sequence game_event_sequence;
--rollback drop sequence game_event_sequence;

-- changeset me.elgregos:create-game-event-table
create table if not exists game_event (
  id uuid primary key,
  sequence_num bigint not null default nextval('game_event_sequence'),
  created_at timestamp not null default (now() at time zone 'utc'),
  created_by uuid not null,
  version int not null,
  event_type varchar(100) not null,
  aggregate_id uuid not null,
  event jsonb not null);
--rollback drop table if exists game_event;

-- changeset me.elgregos:create-game-table-sequence
create sequence game_sequence;
--rollback drop sequence game_sequence;

-- changeset me.elgregos:create-game-table
create table if not exists game (
 id uuid primary key,
 sequence_num bigint not null default nextval('game_sequence'),
 version int not null,
 created_at timestamp not null,
 created_by uuid not null,
 updated_at timestamp not null,
 updated_by uuid not null,
 details jsonb not null);
--rollback drop table if exists game;
