# Laporan Proyek: Sistem Manajemen Perpustakaan (CRUD Authors & Books)

## 1. Ringkasan Proyek
Proyek ini adalah aplikasi web berbasis Java (Jakarta EE) yang mengimplementasikan operasi CRUD (Create, Read, Update, Delete) untuk dua entitas utama: **Authors** (Penulis) dan **Books** (Buku). Aplikasi ini menggunakan arsitektur MVC (Model-View-Controller) dan terintegrasi dengan database **MySQL** untuk penyimpanan data persisten. Aplikasi dijalankan di atas server Apache Tomcat.

## 2. Struktur Proyek
Struktur direktori proyek adalah sebagai berikut:
```
quizJakarta/
├── pom.xml                     # Konfigurasi Maven dan dependensi
├── DATABASE_DESIGN.md          # Desain database (ERD & SQL Script)
├── src/
│   └── main/
│       ├── java/com/example/quizjakarta/
│       │   ├── model/          # Kelas POJO (Plain Old Java Object)
│       │   │   ├── Author.java
│       │   │   └── Book.java
│       │   ├── repository/     # Akses Data (DAO Pattern)
│       │   │   └── DataStore.java
│       │   ├── servlet/        # Controller (Menangani request HTTP)
│       │   │   ├── AuthorServlet.java
│       │   │   └── BookServlet.java
│       │   └── util/           # Utilitas
│       │       └── DBConnection.java
│       └── webapp/             # Frontend (JSP & CSS)
│           ├── css/
│           │   └── style.css
│           ├── index.jsp
│           ├── author-list.jsp
│           ├── author-form.jsp
│           ├── book-list.jsp
│           └── book-form.jsp
```

---

## 3. Penjelasan Kode Backend (Java)

### A. Model (`com.example.quizjakarta.model`)
Package ini berisi representasi objek data.

1.  **`Author.java`**:
    *   Merepresentasikan data penulis.
    *   Atribut: `id` (int), `name` (String), `email` (String).
    *   Memiliki constructor kosong dan constructor dengan parameter, serta getter/setter standar.

2.  **`Book.java`**:
    *   Merepresentasikan data buku.
    *   Atribut: `id` (int), `title` (String), `authorId` (int - foreign key ke Author), `price` (double).
    *   Menghubungkan buku dengan penulis melalui `authorId`.

### B. Repository (`com.example.quizjakarta.repository`)
Package ini menangani akses data.

1.  **`DataStore.java`**:
    *   Berfungsi sebagai Data Access Object (DAO).
    *   Menggunakan **JDBC (Java Database Connectivity)** untuk berinteraksi dengan database MySQL.
    *   Menyediakan metode statis untuk operasi CRUD:
        *   `getAllAuthors()`, `getAuthorById(id)`, `addAuthor(author)`, `updateAuthor(author)`, `deleteAuthor(id)`.
        *   Metode serupa untuk `Book`.
    *   Menggunakan `PreparedStatement` untuk mencegah SQL Injection.
    *   Mengimplementasikan *Exception Handling* untuk menangkap dan menampilkan error database.

### C. Utilities (`com.example.quizjakarta.util`)
Package ini berisi kelas pendukung.

1.  **`DBConnection.java`**:
    *   Mengelola koneksi ke database MySQL.
    *   Mendukung konfigurasi dinamis melalui **Environment Variables** (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`).
    *   Memiliki fallback ke konfigurasi default (`localhost`) jika environment variable tidak ditemukan, memudahkan pengembangan di lokal maupun deployment di VPS.

### D. Servlet / Controller (`com.example.quizjakarta.servlet`)
Package ini menangani logika bisnis dan navigasi.

1.  **`AuthorServlet.java`** (`@WebServlet("/authors")`):
    *   Menangani semua request yang berkaitan dengan penulis.
    *   **`doGet`**: Menangani tampilan halaman (list, form tambah, form edit) dan aksi hapus. Menggunakan parameter `action` untuk menentukan tampilan mana yang dirender.
    *   **`doPost`**: Menangani pengiriman data form (insert dan update).
    *   Menggunakan `RequestDispatcher` untuk meneruskan data ke file JSP.

2.  **`BookServlet.java`** (`@WebServlet("/books")`):
    *   Menangani request untuk buku.
    *   Mirip dengan `AuthorServlet`, namun memiliki logika tambahan untuk mengambil daftar penulis (`DataStore.getAllAuthors()`) saat menampilkan form buku. Hal ini memungkinkan pengguna memilih penulis dari *dropdown menu* saat menambah/mengedit buku.

---

## 4. Penjelasan Kode Frontend (JSP & CSS)

### A. Styling (`style.css`)
File CSS ini memberikan tampilan modern dan responsif pada aplikasi.
*   **Desain**: Menggunakan font `Segoe UI` dan skema warna yang bersih (putih, abu-abu, dan aksen ungu/indigo).
*   **Komponen Utama**:
    *   `.container`: Membungkus konten utama di tengah layar dengan efek bayangan (*box-shadow*).
    *   `.card`: Kotak putih dengan border radius untuk mengelompokkan konten.
    *   `.btn`: Tombol dengan efek hover.
    *   `table`: Tabel yang rapi dengan border pemisah antar baris.
    *   `form`: Input field yang lebar dan responsif dengan efek fokus.

### B. Views (JSP)
Menggunakan **JSTL (Jakarta Standard Tag Library)** untuk logika tampilan (looping, kondisi) agar kode lebih bersih daripada menggunakan scriptlet Java murni (`<% %>`).

1.  **`index.jsp`**:
    *   Halaman utama (Dashboard).
    *   Menampilkan dua kartu menu besar untuk navigasi cepat ke "Manage Authors" dan "Manage Books".

2.  **`author-list.jsp`**:
    *   Menampilkan tabel daftar penulis.
    *   Menggunakan `<c:forEach>` untuk meloop data `listAuthors` yang dikirim dari Servlet.
    *   Setiap baris memiliki tombol "Edit" dan "Delete".

3.  **`author-form.jsp`**:
    *   Satu form yang digunakan untuk dua fungsi: **Tambah** dan **Edit**.
    *   Logika: Jika atribut `author` tidak null, maka form akan terisi data (mode Edit) dan action mengarah ke `update`. Jika null, form kosong (mode Insert).

4.  **`book-list.jsp`**:
    *   Menampilkan tabel daftar buku.
    *   Kolom `Author ID` ditampilkan (dalam pengembangan lebih lanjut bisa diganti nama penulis).

5.  **`book-form.jsp`**:
    *   Form untuk buku.
    *   Fitur Khusus: **Dropdown Author**.
    *   Menggunakan `<c:forEach>` untuk menampilkan semua penulis dalam elemen `<select>`.
    *   Logika `<c:if test="${book.authorId == author.id}">selected</c:if>` memastikan penulis yang benar terpilih saat mode Edit.

## 5. Konfigurasi (`pom.xml`)
File ini mengatur dependensi proyek menggunakan Maven.
*   **Dependencies**:
    *   `jakarta.servlet-api`: API dasar untuk Servlet.
    *   `jakarta.servlet.jsp.jstl-api` & `jakarta.servlet.jsp.jstl`: Library untuk menggunakan tag JSTL di JSP.
    *   `mysql-connector-j`: Driver JDBC untuk menghubungkan aplikasi Java dengan MySQL.
    *   `junit-jupiter`: Untuk unit testing (opsional).
*   **Build Plugin**: `maven-war-plugin` untuk memaketkan aplikasi menjadi file `.war`.

## 6. Cara Menjalankan & Deployment

### Persiapan Database
1.  Pastikan MySQL Server sudah berjalan.
2.  Buat database dan tabel menggunakan script SQL berikut (atau lihat `DATABASE_DESIGN.md`):
    ```sql
    CREATE DATABASE quiz2;
    USE quiz2;
    -- (Jalankan script create table authors dan books)
    ```

### Menjalankan di Lokal
1.  Build proyek: `mvn clean package`.
2.  Deploy file `quizJakarta.war` ke Tomcat.
3.  Aplikasi akan menggunakan konfigurasi default (localhost, user: root, tanpa password).

### Deployment ke VPS (Linux)
1.  Setup MySQL di VPS dan pastikan user root bisa diakses (atau buat user khusus).
2.  Konfigurasi Environment Variable di Tomcat agar aplikasi bisa membaca kredensial database yang aman.
    *   Edit `/etc/default/tomcat10` atau gunakan `systemctl edit tomcat10`.
    *   Set variabel: `DB_USER`, `DB_PASSWORD`, `DB_HOST`, dll.
3.  Upload `.war` file ke folder `webapps` Tomcat di VPS.
4.  Restart Tomcat: `sudo systemctl restart tomcat10`.
