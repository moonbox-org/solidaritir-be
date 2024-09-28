CREATE TABLE IF NOT EXISTS provinces(
    code VARCHAR(2) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    region VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    last_updated_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_by VARCHAR(255)
);