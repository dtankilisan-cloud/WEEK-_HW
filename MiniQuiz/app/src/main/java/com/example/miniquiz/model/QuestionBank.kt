package com.example.miniquiz.model

private fun q(text: String, a: String, b: String, c: String, d: String, correct: Int) =
    Question(text, listOf(a, b, c, d), correct)

object QuestionBank {
    val questions: List<Question> = listOf(
        q("Ibu Kota Di Indonesia?", "Surabaya", "Bandung", "Jakarta", "Medan", 2),
        q("Ada berapa hari dalam seminggu?", "5", "6", "7", "11", 2),
        q("Mata Kuliah VP pakai apa?", "Word", "Android Studio", "Excel", "Photoshop", 1),
        q("Apakah Mata Kuliah VP susah?", "Sangat Sulit", "Sulit", "Sedang", "Sangat Mudah", 3),
        q("Natal dirayakan pada bulan?", "Agustus", "Oktober", "Desember", "Maret", 2),
        q("Hari Kemerdekaan Indonesia jatuh pada tanggal?", "17 Agustus", "1 Juni", "28 Oktober", "10 November", 0),
        q("Berapa hasil dari 8 x 7?", "54", "56", "58", "64", 1),
        q("Planet terbesar di tata surya adalah?", "Mars", "Bumi", "Saturnus", "Jupiter", 3),
        q("Bahasa pemrograman resmi untuk Android modern adalah?", "Kotlin", "Swift", "Ruby", "PHP", 0),
        q("Lambang kimia untuk air adalah?", "CO2", "H2O", "O2", "NaCl", 1),
        q("Siapa presiden pertama Indonesia?", "Soeharto", "Habibie", "Soekarno", "Jokowi", 2),
        q("Berapa jumlah provinsi di pulau Jawa (termasuk DKI & DIY)?", "4", "5", "6", "7", 2),
        q("Hewan khas pulau Komodo adalah?", "Harimau", "Komodo", "Orangutan", "Badak", 1),
        q("Gunung tertinggi di Indonesia adalah?", "Semeru", "Rinjani", "Puncak Jaya", "Merapi", 2),
        q("Berapa hasil dari 100 : 4?", "20", "25", "30", "40", 1),
        q("Alat musik tradisional dari Jawa Barat adalah?", "Angklung", "Sasando", "Tifa", "Gong Sumba", 0),
        q("Samudra terluas di dunia adalah?", "Atlantik", "Hindia", "Arktik", "Pasifik", 3),
        q("Ibu kota Jawa Timur adalah?", "Malang", "Surabaya", "Kediri", "Madiun", 1),
        q("Jetpack Compose digunakan untuk membuat?", "Database", "UI Android", "Server", "Game 3D", 1),
        q("Pola arsitektur yang dipakai di tugas ini adalah?", "MVC", "MVP", "MVVM", "VIPER", 2),
        q("Berapa jumlah bulan dalam setahun?", "10", "11", "12", "13", 2),
        q("Warna campuran biru dan kuning adalah?", "Hijau", "Ungu", "Oranye", "Merah", 0),
        q("Mata uang negara Indonesia adalah?", "Ringgit", "Rupiah", "Baht", "Peso", 1),
        q("Benua tempat Indonesia berada adalah?", "Afrika", "Eropa", "Asia", "Amerika", 2),
        q("Berapa jumlah sisi pada segitiga?", "2", "3", "4", "5", 1)
    )
}
