SELECT id, created_at, algorithm, operation, input_text, result_text
FROM history ORDER BY id DESC LIMIT 20;
SELECT COUNT(*) AS record_count FROM history;
SELECT algorithm, COUNT(*) AS run_count FROM history GROUP BY algorithm ORDER BY run_count DESC;
