package id.ac.stmi.pabkel2

import id.ac.stmi.pabkel2.model.Product

object ProductCatalog {
    val categories = listOf("Semua Kategori", "Rem", "Mesin", "Filter", "Lampu", "Oli", "Kelistrikan")

    val products = listOf(
        Product(1, "Kampas Rem Mobil", "REM-001", "Rem", 120000.0, "Toyota, Daihatsu", "Kampas rem depan untuk pengereman yang aman.", 12, R.drawable.kampas_rem_mobil),
        Product(2, "Kampas Rem Motor", "REM-002", "Rem", 45000.0, "Honda, Yamaha", "Kampas rem motor harian.", 18, R.drawable.kampas_rem_mobil),
        Product(3, "Busi Iridium", "BUS-001", "Mesin", 45000.0, "Universal", "Busi iridium untuk pembakaran lebih stabil.", 24, R.drawable.busi_iridium),
        Product(4, "Belt Mesin", "BLT-001", "Mesin", 95000.0, "Toyota, Suzuki", "Belt mesin dengan daya tahan tinggi.", 9, R.drawable.belt_mesin),
        Product(5, "Filter Udara", "FLT-001", "Filter", 65000.0, "Mobil", "Filter udara untuk menjaga aliran udara mesin.", 8, R.drawable.filter_udara),
        Product(6, "Filter Oli", "FLT-002", "Filter", 25000.0, "Motor, mobil", "Filter oli untuk membantu menjaga kebersihan oli.", 12, R.drawable.filter_oli),
        Product(7, "Lampu LED 12V", "LMP-001", "Lampu", 75000.0, "Universal", "Lampu LED kendaraan 12 volt.", 7, R.drawable.lampu_led),
        Product(8, "Oli Mesin 1L", "OLI-001", "Oli", 85000.0, "Mobil dan motor", "Oli mesin untuk perawatan kendaraan.", 25, R.drawable.oli_mesin),
        Product(9, "Aki Motor", "AKI-001", "Kelistrikan", 280000.0, "Motor", "Aki motor siap pakai.", 6, R.drawable.aki_motor),
        Product(10, "Aki Mobil", "AKI-002", "Kelistrikan", 650000.0, "Mobil", "Aki mobil untuk kebutuhan kelistrikan.", 4, R.drawable.aki_mobil),
        Product(11, "Kampas Kopling", "KPL-001", "Mesin", 185000.0, "Mobil dan motor", "Kampas kopling pengganti untuk perpindahan gigi yang halus.", 10, R.drawable.kampas_kopling)
    )

    fun byId(id: Int): Product? = products.find { it.id == id }
}
