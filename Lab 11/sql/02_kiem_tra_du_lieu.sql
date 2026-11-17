SELECT user_id, username, created_at
FROM app_users
ORDER BY user_id DESC;

SELECT history_id, created_at, algorithm, operation, app_username,
       input_length, output_length
FROM crypto_history
ORDER BY history_id DESC
LIMIT 50;

SELECT COUNT(*) AS so_dong_lich_su FROM crypto_history;
