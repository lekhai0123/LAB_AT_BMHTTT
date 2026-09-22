# README - LAB3: Nhận diện và ứng phó các mối đe dọa đến an toàn thông tin

## 1. Thông tin bài thực hành

-   Môn học: An toàn thông tin
-   Bài Lab: LAB3 - Identifying and Responding to Information Security
    Threats
-   Môi trường thực hiện:
    -   Hệ điều hành: Windows 10 (máy thật)
    -   Thư mục làm việc: `D:\Project\ATTT\Lab3`

## 2. Mục tiêu

-   Phân biệt Vulnerability, Threat, Risk và Attack.
-   Nhận diện các nhóm nguồn đe dọa:
    -   Hành động vô ý
    -   Hành động cố ý
    -   Thảm họa tự nhiên
    -   Lỗi kỹ thuật
    -   Lỗi quản lý
-   Thu thập bằng chứng từ endpoint và mạng bằng:
    -   Microsoft Defender
    -   Windows Event Log
    -   Sysmon
    -   Autoruns
    -   Process Explorer
    -   Wireshark

## 3. Cấu trúc thư mục LAB3

    D:\Project\ATTT\Lab3
    │
    ├── Evidence
    │   └── Lưu log, ảnh chụp, hash bằng chứng
    │
    ├── Downloads
    │   └── Chứa file tải về
    │
    ├── lab3_assets
    │   ├── www
    │   ├── scripts
    │   ├── data
    │   └── samples
    │
    └── Sysinternals
        └── Tools
            ├── Sysmon
            ├── Autoruns
            └── ProcessExplorer

## 4. Công cụ sử dụng

-   Microsoft Defender Antivirus
-   Sysmon
-   Autoruns
-   Process Explorer
-   Wireshark
-   Python HTTP Server

## 5. Quy trình thực hiện

## Bước 0 - Baseline

Thu thập trạng thái ban đầu:

-   Thông tin Windows
-   Defender
-   Firewall
-   Network
-   Process

Các file tạo ra:

    Evidence/
    ├── baseline_os.txt
    ├── baseline_defender.txt
    ├── baseline_firewall.txt
    ├── baseline_network.txt
    └── baseline_processes.txt

## Bước 1 - TH1: Risk Register

Phân tích:

Asset → Vulnerability → Threat → Risk → Control

Ví dụ:

  -----------------------------------------------------------------------------
  Asset          Vulnerability   Threat           Risk           Control
  -------------- --------------- ---------------- -------------- --------------
  Tài khoản lab  Password yếu    Brute            Mất quyền truy MFA, password
                                 force/Phishing   cập            policy

  Dữ liệu LAB3   Thiếu backup    Xóa nhầm/Malware Mất dữ liệu    Backup, hash
  -----------------------------------------------------------------------------

## Bước 2 - TH2: Malware Detection bằng EICAR

Mục tiêu:

-   Kiểm tra Defender phát hiện mẫu kiểm thử.
-   Thu thập bằng chứng detection.

Kết quả cần có:

    Evidence/
    └── defender_eicar.txt

Chụp:

    H4_ProtectionHistory_EICAR.png

## Bước 3 - TH3: Password và Authentication Log

Thực hiện:

-   Tạo tài khoản lab3user.
-   Sinh login thành công.
-   Sinh login thất bại.
-   Kiểm tra Event ID:

```{=html}
<!-- -->
```
    4624 - Successful Logon
    4625 - Failed Logon
    4648 - Explicit Credential

Evidence:

    auth_events.txt

## Bước 4 - TH4: Persistence và Listener

### Cài Sysmon

Đường dẫn:

    D:\Project\ATTT\Lab3\Sysinternals\Tools\Sysmon\Sysmon64.exe

### Kiểm tra Autoruns

Evidence:

    autoruns_before.csv

### HTTP Listener

Chạy:

    cd D:\Project\ATTT\Lab3\lab3_assets\www

    python -m http.server 8080 --bind 127.0.0.1

Kiểm tra:

    Get-NetTCPConnection -LocalPort 8080 -State Listen

Kiểm tra process bằng Process Explorer.

Ảnh:

    H8_ProcessExplorer_Python.png

## Bước 5 - TH5: Wireshark HTTP/HTTPS

Capture HTTP:

    curl.exe "http://127.0.0.1:8080/?lab_user=lab3_student&lab_code=TRAINING_ONLY"

Filter:

    http.request || tcp.port == 8080

Evidence:

    H9_HTTP_Plaintext.png

So sánh HTTPS:

    tls || tcp.port == 443

## Bước 6 - TH6: DoS/DDoS và Mail Bombing

Chạy tải giới hạn localhost:

    local_load_test.py

Phân tích:

-   ddos_sample.csv
-   mailbomb_sample.csv

Evidence:

    local_load_test.txt
    ddos_sources.txt
    mail_sender_counts.txt

## Bước 7 - TH7: Social Engineering / Phishing

Phân tích file:

    lab3_assets\samples\phishing_email.txt

Xác định các dấu hiệu:

-   Tạo cảm giác khẩn cấp
-   Display name giả
-   Domain cần xác minh
-   Reply-To bất thường
-   Yêu cầu cung cấp thông tin

## 8. Cleanup

Sau khi thu thập đủ bằng chứng:

-   Xóa persistence LAB3.
-   Xóa scheduled task.
-   Dừng HTTP server.
-   Xóa tài khoản thử nghiệm.

## 9. Hash bằng chứng

Tạo SHA-256:

    Get-ChildItem Evidence -File |
    Get-FileHash -Algorithm SHA256

Xuất:

    Evidence/evidence_sha256.csv

## 10. Lưu ý an toàn

-   Không sử dụng tài khoản thật.
-   Không nhập mật khẩu thật.
-   Không chạy traffic ra ngoài.
-   Chỉ sử dụng localhost `127.0.0.1:8080`.
-   Không tắt Microsoft Defender.
