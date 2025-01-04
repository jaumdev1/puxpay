-- V2__add_indexes_and_constraints.sql
CREATE INDEX idx_users_document ON transaction.users(document);
CREATE INDEX idx_users_email ON transaction.users(email);

CREATE INDEX idx_wallets_user_id ON transaction.wallets(user_id);

CREATE INDEX idx_deposits_wallet_id ON transaction.deposits(wallet_id);

CREATE INDEX idx_transfers_from_wallet ON transaction.transfers(from_wallet_id);
CREATE INDEX idx_transfers_to_wallet ON  transaction.transfers(to_wallet_id);
