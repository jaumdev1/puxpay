CREATE SCHEMA transaction;

CREATE TABLE transaction.users (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    document VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    user_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transaction.wallets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID UNIQUE NOT NULL,
    balance NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
    version BIGINT,
    FOREIGN KEY (user_id) REFERENCES transaction.users(id) ON DELETE CASCADE
);

CREATE TABLE transaction.deposits (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount NUMERIC(19, 2) NOT NULL,
    wallet_id UUID NOT NULL,
    FOREIGN KEY (wallet_id) REFERENCES transaction.wallets(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transaction.transfers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    from_wallet_id UUID,
    to_wallet_id UUID,
    value NUMERIC(19, 2) NOT NULL,
    request_id UUID NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_wallet_id) REFERENCES transaction.wallets(id) ON DELETE SET NULL,
    FOREIGN KEY (to_wallet_id) REFERENCES transaction.wallets(id) ON DELETE SET NULL
);
