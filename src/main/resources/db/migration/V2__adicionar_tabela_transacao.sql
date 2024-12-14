CREATE TABLE tb_transacao
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cartao VARCHAR(255)   NOT NULL,
    senha_cartao  VARCHAR(255)   NOT NULL,
    valor         DECIMAL(19, 2) NOT NULL
);
