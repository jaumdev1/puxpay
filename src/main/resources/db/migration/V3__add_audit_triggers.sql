-- Certifique-se de que o esquema "transaction" é referenciado corretamente
CREATE TABLE transaction.audit_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    table_name VARCHAR(255) NOT NULL,
    operation_type VARCHAR(50) NOT NULL,
    operation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    old_data JSONB,
    new_data JSONB
);

-- Função para registrar logs de auditoria
CREATE OR REPLACE FUNCTION transaction.log_audit()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO transaction.audit_log(table_name, operation_type, new_data)
        VALUES (TG_TABLE_NAME, TG_OP, row_to_json(NEW)::jsonb);
        RETURN NEW;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO transaction.audit_log(table_name, operation_type, old_data, new_data)
        VALUES (TG_TABLE_NAME, TG_OP, row_to_json(OLD)::jsonb, row_to_json(NEW)::jsonb);
        RETURN NEW;
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO transaction.audit_log(table_name, operation_type, old_data)
        VALUES (TG_TABLE_NAME, TG_OP, row_to_json(OLD)::jsonb);
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Triggers para as tabelas no esquema "transaction"
CREATE TRIGGER audit_users_trigger
AFTER INSERT OR UPDATE OR DELETE ON transaction.users
FOR EACH ROW EXECUTE FUNCTION transaction.log_audit();

CREATE TRIGGER audit_wallets_trigger
AFTER INSERT OR UPDATE OR DELETE ON transaction.wallets
FOR EACH ROW EXECUTE FUNCTION transaction.log_audit();

CREATE TRIGGER audit_deposits_trigger
AFTER INSERT OR UPDATE OR DELETE ON transaction.deposits
FOR EACH ROW EXECUTE FUNCTION transaction.log_audit();

CREATE TRIGGER audit_transfers_trigger
AFTER INSERT OR UPDATE OR DELETE ON transaction.transfers
FOR EACH ROW EXECUTE FUNCTION transaction.log_audit();
