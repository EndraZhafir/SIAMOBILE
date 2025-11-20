package com.endrazhafir.siamobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endrazhafir.siamobile.R
import com.endrazhafir.siamobile.data.*
import com.endrazhafir.siamobile.ui.components.AddDosenContent
import com.endrazhafir.siamobile.ui.components.AddMahasiswaContent
import com.endrazhafir.siamobile.ui.components.AddMataKuliahContent
import com.endrazhafir.siamobile.ui.components.ConfirmationDialog
import com.endrazhafir.siamobile.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    type: String,
    onBackClick: () -> Unit = {}
) {

    // Sample Data untuk Mahasiswa
    val mahasiswaList = remember {
        mutableStateListOf(
            Mahasiswa(1, "literally_me", "ryangosling@gmail.com", "Ryan Gosling", "24/538769/SV/24535", "Software Engineer", "Aktif"),
            Mahasiswa(2, "dark_passenger", "dextermorgan@gmail.com", "Dexter Morgan", "24/123456/SV/12345", "Software Engineer", "Aktif"),
            Mahasiswa(3, "gatau_deh", "email@gmail.com", "Anonim", "Tes Nim", "Prodi Ghaib", "Aktif"),
        )
    }

    // Sample Data untuk Mata Kuliah
    val mataKuliahList = remember {
        mutableStateListOf(
            MataKuliah(1, "Pemrograman Mobile", "PM-301", 3),
            MataKuliah(2, "Basis Data Lanjut", "BD-302", 3),
            MataKuliah(3, "Sistem Operasi", "SO-303", 3),
            MataKuliah(4, "Jaringan Komputer", "JK-304", 4),
            MataKuliah(5, "Kecerdasan Buatan", "AI-123", 3),
            MataKuliah(6, "Kecerdasan Alami", "AI-420", 3),
            MataKuliah(7, "Kalkulus", "KK-376", 3),
            MataKuliah(8, "Statistika", "ST-777", 3),
            MataKuliah(9, "Bahasa Inggris", "EN-101", 3),
            MataKuliah(10, "Ilmu Hitam", "IH-350", 3),
            MataKuliah(11, "Negromancy", "NG-305", 3),
        )
    }

    // Sample Data untuk Dosen
    val dosenList = remember {
        mutableStateListOf(
            Dosen(1, "prfsr_grhm", "professorgraham@gmail.com", "Will Graham", "Criminology", "Aktif"),
            Dosen(2, "dr_lecter", "hanniballecter@gmail.com", "Hannibal Lecter", "Psychology", "Non-Aktif"),
        )
    }

    // State dialog konfirmasi
    // State hapus matkul
    var showDeleteMatkulDialog by remember { mutableStateOf(false) }
    var selectedMatkulToDelete by remember { mutableStateOf<MataKuliah?>(null) }

    // State untuk Status Mahasiswa/Dosen
    var showStatusDialog by remember { mutableStateOf(false) }

    // Data target (nama user, status sekarang)
    var selectedStatusTarget by remember { mutableStateOf<Triple<Int, String, String>?>(null) }
    // Triple: (ID, Nama/Username, StatusSaatIni) -> ID dipake buat logic update nanti

    var searchQuery by remember { mutableStateOf("") }
    var currentPage by remember { mutableStateOf(1) }
    val itemsPerPage = 10

    val filteredList : List<Any> = remember(type, searchQuery, mahasiswaList, mataKuliahList, dosenList) {
        if (searchQuery.isEmpty()) {
            when (type) {
                "MAHASISWA" -> mahasiswaList
                "MATAKULIAH" -> mataKuliahList
                "DOSEN" -> dosenList
                else -> emptyList()
            }
        } else {
            when (type) {
                "MAHASISWA" -> mahasiswaList.filter {
                    it.nama.contains(searchQuery, ignoreCase = true) || it.nim.contains(searchQuery, ignoreCase = true)
                }
                "MATAKULIAH" -> mataKuliahList.filter {
                    it.nama.contains(searchQuery, ignoreCase = true) || it.kode.contains(searchQuery, ignoreCase = true)
                }
                "DOSEN" -> dosenList.filter {
                    it.nama.contains(searchQuery, ignoreCase = true) || it.username.contains(searchQuery, ignoreCase = true)
                }
                else -> emptyList()
            }
        }
    }

    val totalActiveData = remember(type, mahasiswaList, mataKuliahList, dosenList) {
        when (type) {
            "MAHASISWA" -> mahasiswaList.size
            "MATAKULIAH" -> mataKuliahList.size
            "DOSEN" -> dosenList.size
            else -> 0
        }
    }

    val totalPages = remember(filteredList.size) {
        kotlin.math.max(1, (filteredList.size + itemsPerPage - 1) / itemsPerPage)
    }

    val paginatedList = remember(filteredList, currentPage) {
        val startIndex = (currentPage - 1) * itemsPerPage
        val endIndex = kotlin.math.min(startIndex + itemsPerPage, filteredList.size)
        if (startIndex < filteredList.size) {
            filteredList.subList(startIndex, endIndex)
        } else {
            emptyList()
        }
    }

    // State untuk bottom sheet
    var showSheet by remember { mutableStateOf(false) }
    var sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Judul sebelah tombol tambah
    val title = remember(type) {
        when (type) {
            "MAHASISWA" -> "Statistik Mahasiswa"
            "MATAKULIAH" -> "Statistik Mata Kuliah"
            "DOSEN" -> "Statistik Dosen"
            else -> "Statistik"
        }
    }

    // Judul dalam card-nya.
    val cardTitle = remember(type) {
        when (type) {
            "MAHASISWA" -> "Total Mahasiswa"
            "MATAKULIAH" -> "Total Mata Kuliah"
            "DOSEN" -> "Total Dosen"
            else -> "Total"
        }
    }

    // Text dalam card-nya dibawah angka total.
    val cardSubtitle = remember(type) {
        when (type) {
            "MAHASISWA" -> "Mahasiswa aktif"
            "MATAKULIAH" -> "Mata Kuliah aktif"
            "DOSEN" -> "Dosen aktif"
            else -> "Aktif"
        }
    }

    // Text placeholder di searchbar.
    val searchPlaceholder = remember(type) {
        when (type) {
            "MAHASISWA" -> "Cari Mahasiswa"
            "MATAKULIAH" -> "Cari Matkul"
            "DOSEN" -> "Cari Dosen"
            else -> "Cari..."
        }
    }

    // Icon sesuai card-nya
    val cardIconRes = remember(type) {
        when (type) {
            "MAHASISWA" -> R.drawable.ic_student_goldbg
            "MATAKULIAH" -> R.drawable.ic_subject_goldbg
            "DOSEN" -> R.drawable.ic_lecturer_goldbg
            else -> R.drawable.ic_student_goldbg
        }
    }

    // Handle state scroll table horizontal
    val tableScrollState = rememberScrollState()
    val tableWidth = 1000.dp

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = BackgroundCream,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(UGNGreen)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tambah $title",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = { showSheet = false }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }

                when (type) {
                    "MAHASISWA" -> AddMahasiswaContent(onSave = { showSheet = false })
                    "MATAKULIAH" -> AddMataKuliahContent(onSave = { showSheet = false })
                    "DOSEN" -> AddDosenContent(onSave = { showSheet = false })
                }
            }
        }
    }

    // Pop Up dialog konfirmasi
    // Pop up matkul
    if(showDeleteMatkulDialog && selectedMatkulToDelete != null) {
        ConfirmationDialog(
            message = "Apakah Anda yakin ingin menghapus mata kuliah \"${selectedMatkulToDelete?.nama}\"?",
            onConfirm = {
                mataKuliahList.remove(selectedMatkulToDelete)

                showDeleteMatkulDialog = false
                selectedMatkulToDelete = null
            },
            onDismiss = {
                showDeleteMatkulDialog = false
                selectedMatkulToDelete = null
            }
        )
    }

    // Pop up mhs/dsn
    if(showStatusDialog && selectedStatusTarget != null) {
        val (id, name, currentStatus) = selectedStatusTarget!!
        val isActive = currentStatus.equals("Aktif", ignoreCase = true)

        val actionWord = if (isActive) "non-aktifkan" else "aktifkan"

        ConfirmationDialog(
            message = "Apakah Anda yakin ingin $actionWord akun ($name)?",
            onConfirm = {
                if (type == "MAHASISWA") {
                    val index = mahasiswaList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        val item = mahasiswaList[index]
                        val newStatus = if (isActive) "Non-Aktif" else "Aktif"
                        mahasiswaList[index] = item.copy(status = newStatus)
                    }
                } else if (type == "DOSEN") {
                    val index = dosenList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        val item = dosenList[index]
                        val newStatus = if (isActive) "Non-Aktif" else "Aktif"
                        dosenList[index] = item.copy(status = newStatus)
                    }
                }

                showStatusDialog = false
                selectedStatusTarget = null
            },
            onDismiss = {
                showStatusDialog = false
                selectedStatusTarget = null
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream),
    ) {
        // Topbar
        StatsTopBar(
            onBackClick = onBackClick,
        )

        // Semua isi konten (kecuali topbar) dapat di-scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .navigationBarsPadding(),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Header dengan title dan tombol tambah
            item{
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = UGNGreen,
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        onClick = { showSheet = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = UGNGreen
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = "Tambah",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            item { Spacer( modifier = Modifier.height(20.dp)) }

            item {
                // Statistics Card
                StatsDetailCard(
                    totalActive = totalActiveData,
                    title = cardTitle,
                    subtitle = cardSubtitle,
                    iconResId = cardIconRes
                )
            }

            item { Spacer( modifier = Modifier.height(20.dp)) }

            item {
                // Search Field
                StatsSearchField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        currentPage = 1
                    },
                    placeholder = searchPlaceholder
                )
            }

            item { Spacer (modifier = Modifier.height(20.dp)) }

            // Group table (header + rows)
            when (type) {
                "MAHASISWA" -> {
                    item {
                        MahasiswaTableHeader(
                            scrollState = tableScrollState,
                            width = tableWidth
                        )
                    }
                    itemsIndexed(paginatedList) { index, item ->
                        val data = item as Mahasiswa
                        val globalIndex = (currentPage - 1) * itemsPerPage + index + 1

                        MahasiswaTableRow(
                            number = globalIndex,
                            mahasiswa = data,
                            isLastItem = (index == paginatedList.lastIndex),
                            onDeleteClick = {
                                selectedStatusTarget = Triple(
                                    data.id,
                                    data.username,
                                    data.status)
                                showStatusDialog = true },
                            scrollState = tableScrollState,
                            width = tableWidth
                        )
                    }
                }

                "MATAKULIAH" -> {
                    item {
                        MataKuliahTableHeader(
                            scrollState = tableScrollState,
                            width = tableWidth
                        )
                    }
                    itemsIndexed(paginatedList) { index, item ->
                        val data = item as MataKuliah
                        val globalIndex = (currentPage - 1) * itemsPerPage + index + 1

                        MataKuliahTableRow(
                            number = globalIndex,
                            mataKuliah = data,
                            isLastItem = (index == paginatedList.lastIndex),
                            onEditClick = { /*...*/ },
                            onDeleteClick = {
                                selectedMatkulToDelete = data
                                showDeleteMatkulDialog = true },
                            scrollState = tableScrollState,
                            width = tableWidth
                        )
                    }
                }

                "DOSEN" -> {
                    item {
                        DosenTableHeader(
                            scrollState = tableScrollState,
                            width = tableWidth
                        )
                    }
                    itemsIndexed(paginatedList) { index, item ->
                        val data = item as Dosen
                        val globalIndex = (currentPage - 1) * itemsPerPage + index + 1

                        DosenTableRow(
                            number = globalIndex,
                            dosen = data,
                            isLastItem = (index == paginatedList.lastIndex),
                            onDeleteClick = {
                                selectedStatusTarget = Triple(
                                    data.id,
                                    data.username,
                                    data.status)
                                showStatusDialog = true },
                            scrollState = tableScrollState,
                            width = tableWidth
                        )
                    }
                }
            }

            item { Spacer( modifier = Modifier.height(20.dp)) }
            
            item {
                // Pagination
                StatsPaginationControls (
                    currentPage = currentPage,
                    totalPages = totalPages,
                    onPreviousClick = { if (currentPage > 1) currentPage-- },
                    onNextClick = { if (currentPage < totalPages) currentPage++ }
                )
            }
        }
    }
}

@Composable
fun StatsTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(UGNGreen)
            .statusBarsPadding()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.rounded_arrow_back_24),
            contentDescription = "Back",
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Universitas Global Nusantara",
                style = MaterialTheme.typography.bodySmall,
                color = UGNGold,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Dashboard Admin",
                style = MaterialTheme.typography.bodySmall,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_color),
            contentDescription = "UGN Logo",
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun StatsDetailCard(
    totalActive: Int,
    title: String,
    subtitle: String,
    iconResId: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = UGNGreen)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = title,
                    modifier = Modifier.size(32.dp)
                )
            }

            Column(
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(
                    text = totalActive.toString(),
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 30.sp,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun StatsSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = UGNGold
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                tint = UGNGold,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UGNGold,
            unfocusedBorderColor = UGNGold,
            focusedTextColor = UGNGreen,
            unfocusedTextColor = UGNGreen
        ),
        shape = RoundedCornerShape(10.dp),
        textStyle = MaterialTheme.typography.bodyLarge
        )
}

@Composable
fun MahasiswaTableHeader(
    scrollState : ScrollState,
    width : Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .width(width)
                .background(UGNGold, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = "No",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(0.5f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Username",
                style =MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1.5f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Email",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Nama",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(0.7f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "NIM",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Program",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Status",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(0.7f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Actions",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MataKuliahTableHeader(
    scrollState : ScrollState,
    width : Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .width(width)
                .background(UGNGold, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = "No",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(0.5f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Nama Matkul",
                style =MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1.5f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Kode MK",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "SKS",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(0.7f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Actions",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DosenTableHeader(
    scrollState : ScrollState,
    width : Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .width(width)
                .background(UGNGold, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = "No",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(0.5f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Username",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1.5f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Email",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Nama",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(0.7f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Program",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Status",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(0.7f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Actions",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MahasiswaTableRow(
    number: Int,
    mahasiswa: Mahasiswa,
    isLastItem: Boolean,
    onDeleteClick: () -> Unit,
    scrollState: ScrollState,
    width: Dp
) {
    val rowShape = if (isLastItem) {
        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    } else {
        androidx.compose.ui.graphics.RectangleShape
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Column(modifier = Modifier.width(width)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UGNLightGold, shape = rowShape)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = mahasiswa.username,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = mahasiswa.email,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = mahasiswa.nama,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = mahasiswa.nim,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = mahasiswa.program,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                )

                Box(
                    modifier = Modifier.weight(0.7f),
                    contentAlignment = Alignment.Center
                ) {
                    // Status Aktif/Non-Aktif
                    Text(
                        text = mahasiswa.status,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 11.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(UGNGreen, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Logic warna
                    // cek status mahasiswa

                    val isActive = mahasiswa.status.equals("Aktif", ignoreCase = true)
                    val buttonColor = if (isActive) UGNRed else UGNGreenLight

                    // Active/Deactivate Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(buttonColor)
                            .clickable { onDeleteClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_power),
                            contentDescription = "Status Action",
                            modifier = Modifier.size(18.dp),
                            tint = Color.White
                        )
                    }
                }
            }

            if (!isLastItem) {
                HorizontalDivider(
                    color = UGNGold,
                    thickness = 1.dp,
                )
            }
        }
    }
}

@Composable
fun MataKuliahTableRow(
    number: Int,
    mataKuliah: MataKuliah,
    isLastItem: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    scrollState: ScrollState,
    width: Dp
) {
    val rowShape = if (isLastItem) {
        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    } else {
        androidx.compose.ui.graphics.RectangleShape
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Column(modifier = Modifier.width(width)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UGNLightGold, shape = rowShape)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = mataKuliah.nama,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(horizontal = 8.dp),
                )
                Text(
                    text = mataKuliah.kode,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier.weight(0.7f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${mataKuliah.sks} SKS",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 11.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(UGNGreen, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Edit Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(UGNGreenLight)
                            .clickable { onEditClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Edit",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Delete Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(UGNRed)
                            .clickable { onDeleteClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Delete",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White 
                        )
                    }
                }
            }

            if (!isLastItem) {
                HorizontalDivider(
                    color = UGNGold,
                    thickness = 1.dp,
                )
            }
        }
    }
}

@Composable
fun DosenTableRow(
    number: Int,
    dosen: Dosen,
    isLastItem: Boolean,
    onDeleteClick: () -> Unit,
    scrollState: ScrollState,
    width: Dp
) {
    val rowShape = if (isLastItem) {
        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    } else {
        androidx.compose.ui.graphics.RectangleShape
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Column(modifier = Modifier.width(width)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UGNLightGold, shape = rowShape)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = dosen.username,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = dosen.email,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = dosen.nama,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = dosen.program,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    color = Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier.weight(0.7f),
                    contentAlignment = Alignment.Center
                ) {
                    // Status Aktif/Non-Aktif
                    Text(
                        text = dosen.status,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 11.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(UGNGreen, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Logic warna
                    // cek status dosen

                    val isActive = dosen.status.equals("Aktif", ignoreCase = true)
                    val buttonColor = if (isActive) UGNRed else UGNGreenLight

                    // Active/Deactivate Button
                    Box(
                        modifier = Modifier
                            .size(32.dp) // Ukuran Luar Tombol
                            .clip(RoundedCornerShape(8.dp)) // INI KUNCINYA: Bentuk Rounded Box
                            .background(buttonColor) // Warna Background
                            .clickable { onDeleteClick() }, // Pindahkan aksi klik ke sini
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_power),
                            contentDescription = "Status Action",
                            modifier = Modifier.size(18.dp), // Ukuran Icon (proporsional)
                            tint = Color.White
                        )
                    }
                }
            }

            if (!isLastItem) {
                HorizontalDivider(
                    color = UGNGold,
                    thickness = 1.dp,
                )
            }
        }
    }
}

@Composable
fun StatsPaginationControls(
    currentPage: Int,
    totalPages: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        // Previous Button
        Text(
            text = "< Previous",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 11.sp,
            color = if (currentPage > 1) UGNGreen else Color.Gray,
            modifier = Modifier
                .clickable(enabled = currentPage > 1) { onPreviousClick() }
                .padding(8.dp)
        )

        Text(
            text = "Page",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 11.sp,
            color = UGNGreen
        )

        // Box untuk current page
        Box(
            modifier = Modifier
                .background(UGNGreen, RoundedCornerShape(4.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentPage.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = Color.White
            )
        }

        // Teks page ke berapa ("of X")
        Text(
            text = "of $totalPages",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 11.sp,
            color = UGNGreen
        )

        // Next Button
        Text(
            text = "Next >",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 11.sp,
            color = if (currentPage < totalPages) UGNGreen else Color.Gray,
            modifier = Modifier
                .clickable(enabled = currentPage < totalPages) { onNextClick() }
                .padding(8.dp)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=393dp,height=851dp,dpi=420"
)
@Composable
fun StatsScreenPreview() {
    SiaMobileTheme {
        StatsScreen(
            type = "MATAKULIAH",
            onBackClick = {}
        )
    }
}

