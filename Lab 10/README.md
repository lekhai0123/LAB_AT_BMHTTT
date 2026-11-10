# LAB 1 - Mã hóa đối xứng cổ điển

Ứng dụng Java Swing/Maven cho JDK 17+, thiết kế để mở và chạy bằng VS Code. SQLite thay cho SQL Server nên không cần cài dịch vụ cơ sở dữ liệu, không cần tài khoản hoặc mật khẩu. Database `lab1_history.db` được tạo cạnh project khi ứng dụng ghi lịch sử.

## Chạy bằng VS Code

1. Cài JDK 17+ và VS Code với Extension Pack for Java; Maven Wrapper chưa được kèm nên cài Maven 3.9+ hoặc dùng extension Maven for Java.
2. Mở thư mục `LAB1_TuLam` trong VS Code và chờ Maven tải driver SQLite.
3. Chạy `mvn clean package`, sau đó `mvn exec:java`; cũng có thể chọn Run trên lớp `week_01/frm_Menu.java`.
4. Mở form tổng hợp, mã hóa các mẫu bên dưới. Lịch sử được lưu trong file SQLite cục bộ. CSDL/schema có thể dựng và truy vấn từ các file trong `03_SQLite` bằng SQLite CLI/DB Browser for SQLite.

## Bộ dữ liệu theo hướng dẫn

| Thuật toán | Plaintext | Key | Ciphertext |
|---|---|---|---|
| Caesar | HELLO | 3 | KHOOR |
| Vigenère | HELLO | KEY | RIJVS |
| Rail Fence | HELLO WORLD | 3 | HOREL OLLWD |
| Playfair | HELLOWORLD | KEYWORD | GYIZSCOKCFBU |
| Transposition | BAOMAT | 2,3,1 | AAOTBM |
| Autokey | HELLO | KEY | RIJSS |

`report/BaoCao_LAB1.docx` gồm bảng kết quả và chín ảnh chụp từ ứng dụng Swing đang chạy trên JDK 17 qua CI/Xvfb, cùng ảnh truy vấn SQLite có sáu bản ghi.
