# README - LAB 4: KHẢO SÁT VÀ ĐÁNH GIÁ BỀ MẶT MẠNG BẰNG NMAP

## 1. Thông tin sinh viên

- **Họ và tên:** [Điền họ tên]
- **MSSV:** [Điền MSSV]
- **Tên lab:** LAB 4 - Khảo sát và đánh giá bề mặt mạng bằng Nmap
- **Môn học:** An toàn hệ thống thông tin

## 2. Phiên bản và môi trường thực hành

- **Máy thật:** Windows
- **Phần mềm ảo hóa:** VMware Workstation
- **Máy ảo:** Kali Linux 2026.2
- **Nmap trên Kali:** 7.99
- **Kiểu mạng:** VMware Host-Only - VMnet1
- **Dải mạng thực hành:** 192.168.12.0/24
- **Windows Host - VMnet1:** 192.168.12.1/24
- **Kali Linux - eth0:** 192.168.12.128/24
- **Máy đích:** Windows máy thật thông qua VMware Network Adapter VMnet1
- **Metasploitable 2:** Không sử dụng trong lần thực hành này do giới hạn thời gian và tài nguyên máy.

## 3. Cách dựng môi trường

1. Cài VMware Workstation trên Windows.
2. Import hoặc tạo máy ảo Kali Linux.
3. Trong cấu hình Network Adapter của Kali, chọn **Host-only**.
4. Kiểm tra VMware Network Adapter VMnet1 trên Windows bằng lệnh:

```cmd
ipconfig
```

5. Ghi nhận địa chỉ VMnet1 của Windows:

```text
192.168.12.1/24
```

6. Trên Kali kiểm tra địa chỉ IP bằng:

```bash
ip -br addr
```

Kết quả ghi nhận:

```text
eth0 UP 192.168.12.128/24
```

7. Kiểm tra Nmap trên Kali:

```bash
nmap --version
```

8. Xác nhận Kali và Windows cùng thuộc mạng Host-Only `192.168.12.0/24`.

## 4. Các tình huống đã thực hiện

### 4.1. Kiểm tra kết nối Kali -> Windows

Lệnh:

```bash
ping -c 4 192.168.12.1
```

**Kết quả ban đầu:** FAIL

- Kali gửi ICMP nhưng không nhận được phản hồi.
- Kết quả xuất hiện `100% packet loss`.

**Nguyên nhân dự kiến:** Windows Firewall chặn ICMP Echo Request trên adapter VMnet1.

**Cách xử lý:** Kiểm tra Windows Firewall và có thể bật rule `File and Printer Sharing (Echo Request - ICMPv4-In)`. Nếu chỉ cần tiếp tục kiểm tra bằng Nmap và địa chỉ IP đã chính xác, có thể sử dụng tùy chọn `-Pn` khi cần.

---

### 4.2. Host Discovery

Lệnh:

```bash
sudo nmap -sn 192.168.12.0/24
```

**Kết quả:** PASS

Các host được phát hiện trong mạng Host-Only gồm:

- `192.168.12.1` - Windows Host / VMware VMnet1
- `192.168.12.128` - Kali Linux
- Một địa chỉ VMware khác có thể xuất hiện do dịch vụ mạng ảo/DHCP của VMware.

Host discovery xác nhận mạng Host-Only hoạt động và Kali có thể quan sát các host trong subnet.

---

### 4.3. TCP Connect Scan (-sT)

Lệnh:

```bash
nmap -sT 192.168.12.1
```

**Kết quả:** PASS về mặt thực thi lệnh.

Kết quả ghi nhận:

```text
Host is up.
All 1000 scanned ports on 192.168.12.1 are in ignored states.
Not shown: 1000 filtered tcp ports (no-response)
```

Tổng hợp:

- **Open:** 0
- **Closed:** 0
- **Filtered:** 1000
- **Thời gian quét:** 25.79 giây

**Nhận xét:** Máy Windows phản hồi là host đang hoạt động nhưng toàn bộ 1000 cổng TCP mặc định bị lọc. Kết quả này phù hợp với trường hợp Windows Firewall không phản hồi các gói dò cổng từ Kali.

---

## 5. Các bước tiếp theo của bài lab

Các nội dung tiếp theo cần thực hiện và cập nhật kết quả vào README:

```text
[ ] SYN scan (-sS)
[ ] FIN scan (-sF)
[ ] Xmas scan (-sX)
[ ] NULL scan (-sN)
[ ] ACK scan (-sA)
[ ] UDP scan (-sU --top-ports 20)
[ ] Service/version detection (-sV)
[ ] OS detection (-O)
[ ] Aggressive scan (-A)
[ ] NSE / SMB nếu cổng 445 có thể kiểm tra
[ ] Xuất kết quả .txt
[ ] Xuất kết quả .xml
[ ] Before/After hardening
```

Sau khi chạy từng tình huống, cập nhật trạng thái thành **PASS** hoặc **FAIL** theo kết quả thực tế.

## 6. Xuất và lưu kết quả Nmap

Ví dụ xuất kết quả dạng văn bản:

```bash
sudo nmap -sV -O 192.168.12.1 -oN ket_qua.txt
```

Xuất XML:

```bash
sudo nmap -sV -O 192.168.12.1 -oX ket_qua.xml
```

Nếu chạy lệnh tại thư mục home của Kali (`~`) thì các file được lưu tại:

```text
/home/kali/ket_qua.txt
/home/kali/ket_qua.xml
```

Kiểm tra bằng:

```bash
pwd
ls -lh ket_qua.txt
```

Hoặc tìm file bằng:

```bash
find ~ -name "ket_qua.txt" 2>/dev/null
```

## 7. Lỗi gặp phải và cách khắc phục

### Lỗi 1: Ping từ Kali sang Windows bị 100% packet loss

**Hiện tượng:**

```text
4 packets transmitted, 0 received, 100% packet loss
```

**Nguyên nhân:** Windows Firewall có thể chặn ICMP Echo Request trên adapter VMnet1.

**Khắc phục:**

- Kiểm tra hai máy có cùng subnet.
- Kiểm tra Kali sử dụng Host-Only.
- Kiểm tra Windows VMnet1 là `192.168.12.1/24`.
- Cho phép ICMPv4 Echo Request trên Windows Firewall nếu cần.
- Khi IP đã xác định đúng nhưng host không trả lời ping, có thể dùng `-Pn` với Nmap.

### Lỗi/quan sát 2: TCP Connect Scan cho 1000 cổng filtered

**Hiện tượng:**

```text
Not shown: 1000 filtered tcp ports (no-response)
```

**Giải thích:** Đây không nhất thiết là lỗi của Nmap. Firewall trên Windows có thể đang lọc toàn bộ lưu lượng dò cổng từ Kali.

**Cách xử lý:**

- Giữ kết quả này để phân tích trạng thái `filtered`.
- Nếu bài thực hành cần quan sát cổng `open`, có thể bật một dịch vụ test hợp pháp trên chính máy Windows và chỉ cho phép cổng đó trên VMnet1.
- Không quét các IP hoặc hệ thống bên ngoài khi chưa được phép.

## 8. Kết luận

Môi trường Kali Linux và Windows Host đã được kết nối qua VMware Host-Only VMnet1. Host discovery hoạt động thành công. TCP Connect Scan thực hiện thành công nhưng toàn bộ 1000 cổng TCP mặc định trên Windows đang ở trạng thái `filtered`, cho thấy firewall đang hạn chế phản hồi đối với hoạt động dò cổng. Các kỹ thuật quét còn lại sẽ tiếp tục được thực hiện trên cùng môi trường và cập nhật theo kết quả thực tế.
