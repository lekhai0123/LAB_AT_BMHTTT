-- Chạy trong SQLite: tạo cấu trúc tương đương database LAB2.
-- Ứng dụng Java tự tạo hai bảng này trong lab2.db khi chạy lần đầu.
CREATE TABLE IF NOT EXISTS app_users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    salt BLOB NOT NULL,
    password_hash BLOB NOT NULL,
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS crypto_history (
    history_id INTEGER PRIMARY KEY AUTOINCREMENT,
    algorithm TEXT NOT NULL,
    operation TEXT NOT NULL,
    app_username TEXT,
    input_length INTEGER NOT NULL,
    output_length INTEGER NOT NULL,
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);
