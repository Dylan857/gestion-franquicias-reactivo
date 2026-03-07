CREATE TABLE franchises (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITHOUT TIME ZONE
);

ALTER TABLE franchises ADD CONSTRAINT unique_franchise_name UNIQUE (name);

CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_franchise_modtime
    BEFORE UPDATE ON franchises
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_column();

CREATE TABLE branches (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TRIGGER update_branch_modtime
    BEFORE UPDATE ON branches
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_column();


CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITHOUT TIME ZONE
);

ALTER TABLE products ADD CONSTRAINT unique_product_name UNIQUE (name);


CREATE TABLE branch_franchises (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    franchise_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    
    CONSTRAINT fk_bf_branch FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE CASCADE,
    CONSTRAINT fk_bf_franchise FOREIGN KEY (franchise_id) REFERENCES franchises(id) ON DELETE CASCADE
);

CREATE TABLE product_branch_stock (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    
    CONSTRAINT fk_pbs_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_pbs_branch FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE CASCADE
);

CREATE TRIGGER update_products_modtime BEFORE UPDATE ON products FOR EACH ROW EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_branch_franchises_modtime BEFORE UPDATE ON branch_franchises FOR EACH ROW EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_product_branch_stock_modtime BEFORE UPDATE ON product_branch_stock FOR EACH ROW EXECUTE FUNCTION update_modified_column();