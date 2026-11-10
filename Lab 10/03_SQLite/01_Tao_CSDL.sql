-- SQLite: mở/chạy file này bằng DB Browser for SQLite hoặc sqlite3.
PRAGMA foreign_keys = ON;
CREATE TABLE IF NOT EXISTS history (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  created_at TEXT NOT NULL DEFAULT (datetime('now','localtime')),
  algorithm TEXT NOT NULL,
  operation TEXT NOT NULL CHECK (operation IN ('ENCRYPT','DECRYPT')),
  input_text TEXT NOT NULL,
  result_text TEXT NOT NULL
);
