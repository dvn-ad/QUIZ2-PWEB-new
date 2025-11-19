# Laporan Proyek: Sistem Manajemen Perpustakaan (CRUD Authors & Books)

## 1. Ringkasan Proyek
Proyek ini adalah aplikasi web berbasis Java (Jakarta EE) yang mengimplementasikan operasi CRUD (Create, Read, Update, Delete) untuk dua entitas utama: **Authors** (Penulis) dan **Books** (Buku). Aplikasi ini menggunakan arsitektur MVC (Model-View-Controller) sederhana tanpa database eksternal (menggunakan penyimpanan in-memory) dan dijalankan di atas server Apache Tomcat.

## 2. Struktur Proyek
Struktur direktori proyek adalah sebagai berikut:
```
quizJakarta/
├── pom.xml                     # Konfigurasi Maven dan dependensi
├── src/
│   └── main/
│       ├── java/com/example/quizjakarta/
│       │   ├── model/          # Kelas POJO (Plain Old Java Object)
│       │   │   ├── Author.java
│       │   │   └── Book.java
│       │   ├── repository/     # Penyimpanan data (In-Memory)
│       │   │   └── DataStore.java
│       │   └── servlet/        # Controller (Menangani request HTTP)
│       │       ├── AuthorServlet.java
│       │       └── BookServlet.java
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
    *   Berfungsi sebagai database simulasi (in-memory).
    *   Menggunakan `static List<Author>` dan `static List<Book>` untuk menyimpan data selama aplikasi berjalan.
    *   Menggunakan `AtomicInteger` untuk menghasilkan ID unik secara otomatis (auto-increment).
    *   Menyediakan metode statis untuk operasi CRUD:
        *   `getAllAuthors()`, `getAuthorById(id)`, `addAuthor(author)`, `updateAuthor(author)`, `deleteAuthor(id)`.
        *   Metode serupa untuk `Book`.
    *   **Fitur Cascade Delete**: Saat penulis dihapus, buku yang ditulis oleh penulis tersebut juga ikut dihapus untuk menjaga integritas data.

### C. Servlet / Controller (`com.example.quizjakarta.servlet`)
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
    *   `jakarta.servlet.jsp.jstl-api` & `jakarta.servlet.jsp.jstl`: Library untuk menggunakan tag JSTL di JSP (seperti `<c:forEach>`, `<c:if>`).
    *   `junit-jupiter`: Untuk unit testing (opsional).
*   **Build Plugin**: `maven-war-plugin` untuk memaketkan aplikasi menjadi file `.war`.

## 6. Cara Menjalankan
1.  Build proyek dengan perintah: `mvn clean package`.
2.  Ambil file `quizJakarta.war` dari folder `target/`.
3.  Letakkan file tersebut di folder `webapps/` pada instalasi Apache Tomcat 10.1.
4.  Jalankan Tomcat (`bin/startup.bat` atau `bin/startup.sh`).
5.  Akses aplikasi di browser: `http://localhost:8080/quizJakarta/`.
