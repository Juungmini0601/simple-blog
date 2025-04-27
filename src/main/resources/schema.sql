-- 기존 테이블 삭제 (있는 경우)
DROP TABLE IF EXISTS oauth;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

-- users 테이블 생성
CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    role     VARCHAR(50)  NOT NULL
);

-- posts 테이블 생성
CREATE TABLE posts
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    user_id    BIGINT       NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- oauth 테이블 생성
CREATE TABLE oauth
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    provider    VARCHAR(50)  NOT NULL,
    provider_id VARCHAR(255) NOT NULL,
    user_id     BIGINT       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT uk_provider_providerid UNIQUE (provider, provider_id)
);
