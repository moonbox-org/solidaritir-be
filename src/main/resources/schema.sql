drop table if exists collection_points;
drop table if exists provinces;
drop table if exists categories cascade;
drop table if exists products;

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
    code serial primary key,
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

create table if not exists categories (
    id serial primary key,
    name varchar(255) not null unique,
    description text,
    parent_id bigint,
    created_at timestamp,
    last_updated_at timestamp,
    created_by varchar(255),
    last_updated_by varchar(255),
    constraint fk_parent_category
        foreign key (parent_id)
        references categories(id)
        on delete cascade
);

create table if not exists products (
    id serial primary key,
    name varchar(255) not null unique,
    description text,
    category_id bigint,
    active boolean default true,
    brand_name varchar(255),
    manufacturer varchar(255),
    ean13 varchar(13),
    created_at timestamp,
    last_updated_at timestamp,
    created_by varchar(255),
    last_updated_by varchar(255),
    constraint fk_category
        foreign key (category_id)
        references categories(id)
        on delete set null
);

create index if not exists idx_province_name on provinces(name);
create index if not exists idx_collection_point_province_id on collection_points(province_id);
create index if not exists idx_categories_parent_id on categories(parent_id);
create index if not exists idx_products_category_id on products(category_id);
create index if not exists idx_categories_created_at on categories(created_at);
create index if not exists idx_products_created_at on products(created_at);
