drop table if exists collection_points;
drop table if exists provinces;

create table if not exists provinces(
    code varchar(2) primary key,
    name varchar(255) not null,
    region varchar(255) not null,
    created_at timestamp,
    last_updated_at timestamp,
    created_by varchar(255),
    last_updated_by varchar(255)
);

create table if not exists collection_points (
    code bigint primary key,
    name varchar(255),
    province_id varchar(2),
    active boolean,
    notes text,
    created_by varchar(255),
    created_at timestamp,
    last_updated_by varchar(255),
    last_updated_at timestamp,
    constraint fk_province
        foreign key (province_id)
        references provinces(code)
        on delete set null
);

create index if not exists idx_province_name on provinces(name);
create index if not exists idx_collection_point_province on collection_points(province_id);
