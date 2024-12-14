CREATE TABLE tb_cartao
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    saldo         DECIMAL(19, 2) NOT NULL,
    numero_cartao VARCHAR(255)   NOT NULL,
    senha         VARCHAR(255)   NOT NULL
);