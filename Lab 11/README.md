# LAB 2 - Mã hóa đối xứng hiện đại

**Họ tên:** Lê Thanh Khải  
**MSSV:** 1150080020

Project Java viết bằng Swing, chạy được trong VS Code hoặc NetBeans. Theo yêu cầu giảm cài đặt, phần lưu tài khoản/lịch sử dùng SQLite cục bộ thay SQL Server. CSDL `lab2.db` và hai bảng được tạo tự động khi mở ứng dụng lần đầu. Tài khoản lưu salt và PBKDF2 hash, không lưu mật khẩu gốc.

## Cần cài

- JDK 17 (hoặc mới hơn) và Apache Maven.
- VS Code; tiện ích Extension Pack for Java hữu ích nhưng có thể chạy bằng Maven trong Terminal.
- Python 3.10+ cho bài AES-CBC.
- Kết nối Internet ở lần build Java đầu tiên để Maven tải SQLite JDBC và Bouncy Castle.

Không cần cài SQL Server, SSMS hay SQLite riêng. Maven tải các thư viện Java tự động; Python cần một thư viện ngoài là `cryptography`.

## Chạy ứng dụng Java trong VS Code

1. Mở thư mục `Lab11` trong VS Code.
2. Chọn Terminal → New Terminal.
3. Chạy `mvn clean package` để biên dịch.
4. Chạy `mvn exec:java` để mở menu.

Có thể dùng `mvn compile exec:java` để chạy nhanh sau khi dependencies đã tải. CSDL `lab2.db` được tạo trong thư mục hiện hành.

## Các bài Java

- **2.1 DES:** Plaintext `Xin chào Lab 2 - Mã hóa hiện đại!`, Key `12345678`.
- **2.2 TripleDES:** Key mặc định 24 ký tự `123456789012345678901234`; xóa một ký tự để thấy báo lỗi độ dài.
- **2.3 AES-GCM:** Bấm Kết nối SQLite → Test Connection; đăng ký tài khoản mới (username 3-40 ký tự, mật khẩu từ 8 ký tự) → Login → nhập plaintext và Key AES → Encrypt rồi Decrypt. Đăng nhập cấp Ticket; lịch sử đăng ký/đăng nhập/mã hóa/giải mã lưu trong SQLite.
- **2.4 RC4:** Plaintext `Plaintext`, Key `Key`; bản mã HEX mong đợi `BBF316E8D940AF0AD3`.
- **2.5 Twofish:** Generate IV → nhập plaintext → Encrypt/Decrypt. Save File ghi IV cùng bản mã; Open File khôi phục cả hai.
- **2.6 AES-CTR:** Generate Key và Generate Nonce → Encrypt/Decrypt. Bản mã Base64 của CTR có độ dài byte bằng bản rõ UTF-8; tệp lưu nonce và bản mã, không lưu khóa.
- **Lịch sử:** Mở Xem lịch sử từ menu để xem hoạt động đã ghi.

Các lớp form có tệp `.form` đi kèm theo cấu trúc dự án NetBeans. Giao diện được dựng bằng Swing trong mã Java nên chạy trực tiếp trong VS Code, không cần trình thiết kế giao diện NetBeans.

## Chạy bài Python AES-CBC

Trong Terminal ở thư mục `Lab11`:

```powershell
py -m pip install cryptography
py aes_cbc_gui.py --self-test
py aes_cbc_gui.py
```

Trong cửa sổ Tkinter, bấm Generate Key, Generate IV, nhập văn bản rồi Encrypt/Decrypt. Save File lưu IV và ciphertext nhưng không lưu Key. Khi chụp ảnh tự kiểm thử, Terminal cần hiện `SELF-TEST OK`.

## SQL scripts

Các tệp trong `sql/` là cú pháp SQLite:

- `01_tao_csdl.sql`: tạo hai bảng; ứng dụng cũng tự tạo schema này.
- `02_kiem_tra_du_lieu.sql`: truy vấn người dùng và lịch sử. Mở `lab2.db` bằng tiện ích SQLite trong VS Code (hoặc DB Browser for SQLite nếu máy đã có) rồi chạy các truy vấn.

## Danh sách ảnh cần tự chụp

Tạo thư mục `screenshots/` rồi lưu ảnh chụp thật từ máy bạn. Gợi ý ảnh phù hợp với yêu cầu bài:

1. `01_menu.png` - menu chính và họ tên/MSSV.
2. `02_des_encrypt_decrypt.png` - DES mã hóa, sau đó giải mã khôi phục đúng plaintext.
3. `03_tripledes.png` - TripleDES chạy thành công; có thể thêm ảnh lỗi với khóa 23 ký tự.
4. `04_aes_register_login.png` - đăng ký/đăng nhập thành công và Ticket hiện ra.
5. `05_aes_encrypt_decrypt.png` - AES mã hóa và khôi phục plaintext.
6. `06_sqlite_history.png` - bảng lịch sử trong ứng dụng và/hoặc kết quả truy vấn SQLite.
7. `07_rc4.png` - RC4 với kết quả HEX `BBF316E8D940AF0AD3`.
8. `08_twofish_file.png` - Twofish Encrypt và thao tác Open File/Decrypt khôi phục IV.
9. `09_aes_ctr.png` - AES-CTR chạy thành công.
10. `10_aes_cbc_python.png` - cửa sổ Python sau Encrypt/Decrypt; ảnh Terminal có thể bổ sung kết quả self-test.

Đừng tự tạo ảnh minh họa thay ảnh kết quả chạy thật. Khi chụp xong, đặt ảnh vào `screenshots/`.
