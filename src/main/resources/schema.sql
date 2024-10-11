drop table if exists collection_points;
drop table if exists provinces;
drop table if exists products cascade;
drop table if exists packages cascade;
drop table if exists categories cascade;
drop table if exists container_types cascade;

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

create table if not exists container_types (
    id serial primary key,
    name varchar(255) not null unique,
    description text,
    fragile boolean,
    measuring_unit varchar(255),
    capacity double precision,
    created_at timestamp,
    last_updated_at timestamp,
    created_by varchar(255),
    last_updated_by varchar(255)
);

create table if not exists products (
    id serial primary key,
    name varchar(255) not null,
    description text,
    category_id bigint,
    container_type_id bigint,
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
        on delete set null,
    constraint fk_container_type
        foreign key (container_type_id)
        references container_types(id)
        on delete set null,
    unique (name, category_id, container_type_id)
);

create table if not exists packages (
    id serial primary key,
    product_id bigint,
    created_at timestamp,
    last_updated_at timestamp,
    created_by varchar(255),
    last_updated_by varchar(255),
    constraint fk_product_in_package
        foreign key (product_id)
        references products(id)
        on delete set null
);

create table if not exists items (
    id serial primary key,
    product_id bigint,
    package_id bigint,
    expiration_date date,
    created_at timestamp,
    last_updated_at timestamp,
    created_by varchar(255),
    last_updated_by varchar(255),
    constraint fk_product_in_item
        foreign key (product_id)
        references products(id)
        on delete set null,
    constraint fk_package_in_item
        foreign key (package_id)
        references packages(id)
        on delete set null
);

-- indexes
-- provinces
create index if not exists idx_province_name on provinces(name);
-- collection_points
create index if not exists idx_collection_point_province_id on collection_points(province_id);
-- categories
create index if not exists idx_categories_parent_id on categories(parent_id);
create index if not exists idx_categories_created_at on categories(created_at);
-- products
create index if not exists idx_products_name on products (name);
create index if not exists idx_products_ean13 on products (ean13);
create index if not exists idx_products_active on products (active);
create index if not exists idx_products_created_at on products(created_at);
create index if not exists idx_products_category_id on products(category_id);
create index if not exists idx_products_container_type on products (container_type_id);
-- packages
create index if not exists idx_package_product on packages (product_id);
-- items
create index if not exists idx_items_product on items (product_id);
create index if not exists idx_items_package on items (package_id);
create index if not exists idx_items_expiration_date on items (expiration_date);
