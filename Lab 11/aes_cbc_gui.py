"""LAB 2 nâng cao: AES-256-CBC với giao diện Tkinter.

Cài thư viện: py -m pip install cryptography
Chạy tự kiểm thử: py aes_cbc_gui.py --self-test
Chạy giao diện: py aes_cbc_gui.py
"""
import base64
import secrets
import sys
import tkinter as tk
from tkinter import filedialog, messagebox, ttk

from cryptography.hazmat.primitives import padding
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes


def new_key() -> str:
    return base64.b64encode(secrets.token_bytes(32)).decode("ascii")


def new_iv() -> str:
    return base64.b64encode(secrets.token_bytes(16)).decode("ascii")


def _key_iv(key_b64: str, iv_b64: str):
    try:
        key = base64.b64decode(key_b64.strip(), validate=True)
        iv = base64.b64decode(iv_b64.strip(), validate=True)
    except Exception as exc:
        raise ValueError("Key/IV phải là chuỗi Base64 hợp lệ.") from exc
    if len(key) != 32 or len(iv) != 16:
        raise ValueError("Key cần 32 byte, IV cần 16 byte. Hãy bấm Generate Key/IV.")
    return key, iv


def encrypt_text(plaintext: str, key_b64: str, iv_b64: str) -> str:
    key, iv = _key_iv(key_b64, iv_b64)
    padder = padding.PKCS7(128).padder()
    data = padder.update(plaintext.encode("utf-8")) + padder.finalize()
    enc = Cipher(algorithms.AES(key), modes.CBC(iv)).encryptor()
    return base64.b64encode(enc.update(data) + enc.finalize()).decode("ascii")


def decrypt_text(ciphertext_b64: str, key_b64: str, iv_b64: str) -> str:
    key, iv = _key_iv(key_b64, iv_b64)
    try:
        data = base64.b64decode(ciphertext_b64.strip(), validate=True)
    except Exception as exc:
        raise ValueError("Ciphertext phải là Base64.") from exc
    if not data or len(data) % 16:
        raise ValueError("Độ dài bản mã phải là bội số của 16 byte.")
    dec = Cipher(algorithms.AES(key), modes.CBC(iv)).decryptor()
    padded = dec.update(data) + dec.finalize()
    try:
        unpadder = padding.PKCS7(128).unpadder()
        return (unpadder.update(padded) + unpadder.finalize()).decode("utf-8")
    except Exception as exc:
        raise ValueError("Sai Key/IV hoặc bản mã đã bị sửa.") from exc


class App(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("AES-CBC Python - LAB 2")
        self.resizable(False, False)
        frame = ttk.Frame(self, padding=14)
        frame.grid()
        ttk.Label(frame, text="BÀI NÂNG CAO 02 - AES-256-CBC (PYTHON)",
                  font=("Segoe UI", 14, "bold")).grid(row=0, column=0, columnspan=3, sticky="w", pady=(0, 8))
        ttk.Label(frame, text="Plaintext:").grid(row=1, column=0, sticky="nw")
        self.txt_plain = tk.Text(frame, width=64, height=5, wrap="word")
        self.txt_plain.grid(row=1, column=1, columnspan=2, pady=3)
        self.key = tk.StringVar()
        self.iv = tk.StringVar()
        ttk.Label(frame, text="Key (Base64):").grid(row=2, column=0, sticky="w")
        ttk.Entry(frame, textvariable=self.key, width=60).grid(row=2, column=1, sticky="w", pady=3)
        ttk.Button(frame, text="Generate Key", command=lambda: self.key.set(new_key())).grid(row=2, column=2)
        ttk.Label(frame, text="IV (Base64):").grid(row=3, column=0, sticky="w")
        ttk.Entry(frame, textvariable=self.iv, width=60).grid(row=3, column=1, sticky="w", pady=3)
        ttk.Button(frame, text="Generate IV", command=lambda: self.iv.set(new_iv())).grid(row=3, column=2)
        ttk.Label(frame, text="Ciphertext:").grid(row=4, column=0, sticky="nw")
        self.txt_cipher = tk.Text(frame, width=64, height=5, wrap="char")
        self.txt_cipher.grid(row=4, column=1, columnspan=2, pady=3)
        buttons = ttk.Frame(frame)
        buttons.grid(row=5, column=1, columnspan=2, sticky="w", pady=6)
        for label, callback in (("Encrypt", self.encrypt), ("Decrypt", self.decrypt),
                                ("Save File", self.save), ("Open File", self.open)):
            ttk.Button(buttons, text=label, command=callback).pack(side="left", padx=(0, 8))
        self.status = tk.StringVar(value="Bấm Generate Key và Generate IV trước.")
        ttk.Label(frame, textvariable=self.status).grid(row=6, column=1, columnspan=2, sticky="w")

    @staticmethod
    def _get(widget):
        return widget.get("1.0", "end-1c")

    @staticmethod
    def _set(widget, value):
        widget.delete("1.0", "end")
        widget.insert("1.0", value)

    def encrypt(self):
        try:
            self._set(self.txt_cipher, encrypt_text(self._get(self.txt_plain), self.key.get(), self.iv.get()))
            self.status.set("Đã mã hóa AES-CBC.")
        except ValueError as exc:
            messagebox.showerror("Lỗi", str(exc))

    def decrypt(self):
        try:
            self._set(self.txt_plain, decrypt_text(self._get(self.txt_cipher), self.key.get(), self.iv.get()))
            self.status.set("Đã giải mã AES-CBC.")
        except ValueError as exc:
            messagebox.showerror("Lỗi", str(exc))

    def save(self):
        path = filedialog.asksaveasfilename(defaultextension=".txt", initialfile="aes_cbc_ciphertext.txt")
        if path:
            with open(path, "w", encoding="utf-8") as stream:
                stream.write("IV=" + self.iv.get().strip() + "\n" + self._get(self.txt_cipher).strip() + "\n")
            self.status.set("Đã lưu IV và bản mã (không lưu Key): " + path)

    def open(self):
        path = filedialog.askopenfilename(filetypes=[("Text file", "*.txt")])
        if path:
            with open(path, encoding="utf-8") as stream:
                lines = stream.read().strip().split("\n", 1)
            if len(lines) < 2 or not lines[0].startswith("IV="):
                messagebox.showerror("Lỗi", "Tệp cần dòng 1 IV=..., dòng 2 là bản mã.")
                return
            self.iv.set(lines[0][3:].strip())
            self._set(self.txt_cipher, lines[1].strip())
            self.status.set("Đã mở tệp. Nhập đúng Key rồi bấm Decrypt.")


def self_test():
    key, iv = new_key(), new_iv()
    long_text = "Văn bản dài tiếng Việt có dấu. " * 200
    for value in ("Xin chào AES-CBC", "", "1234567890123456", long_text):
        assert decrypt_text(encrypt_text(value, key, iv), key, iv) == value
    assert len(base64.b64decode(encrypt_text("abc", key, iv))) == 16
    print("SELF-TEST OK: mã hóa/giải mã đúng với 4 thông điệp.")


if __name__ == "__main__":
    if "--self-test" in sys.argv:
        self_test()
    else:
        App().mainloop()
