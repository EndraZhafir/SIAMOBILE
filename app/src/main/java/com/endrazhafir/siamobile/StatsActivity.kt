package com.endrazhafir.siamobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endrazhafir.siamobile.data.MataKuliah
import com.endrazhafir.siamobile.ui.theme.*
import com.endrazhafir.siamobile.ui.theme.SiaMobileTheme
class StatsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SiaMobileTheme {
                StatsScreen(
                    onBackClick = { finish() }
                )
            }
        }
    }
}

@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    // Contoh sample data
    val mataKuliahList = remember {
        mutableStateListOf(
            MataKuliah(1, "Pemrograman Mobile", "IF-301", 3),
            MataKuliah(2, "Basis Data Lanjut", "IF-302", 3),
            MataKuliah(3, "Sistem Operasi", "IF-303", 3),
            MataKuliah(4, "Jaringan Komputer", "IF-304", 4),
            MataKuliah(5, "Kecerdasan Buatan", "IF-305", 3),
            MataKuliah(6, "Negromancy", "NG-305", 3),
            MataKuliah(7, "Ilmu Hitam", "IH-305", 3),
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    var currentPage by remember { mutableStateOf(1) }
    val itemsPerPage = 10

    val filteredList = remember(searchQuery, mataKuliahList) {
        if (searchQuery.isEmpty()) {
            mataKuliahList
        } else {
            mataKuliahList.filter {
                it.nama.contains(searchQuery, ignoreCase = true) ||
                it.kode.contains(searchQuery, ignoreCase = true)
            }
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        // Topbar
        TopBar(onBackClick = onBackClick)

        // Semua isi konten (kecuali topbar) dapat di-scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header dengan title dan tombol tambah
            item{
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Statistik Mata Kuliah",
                        color = UGNGreen,
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        onClick = { /* Handle add */ },
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

            item {
                // Statistics Card
                StatsCard(totalActive = mataKuliahList.size)
            }

            item {
                // Search Field
                SearchField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        currentPage = 1
                    }
                )
            }

            // Group table (header + rows)
            item {
                Column{
                    // Table Header
                    TableHeader()

                    paginatedList.forEachIndexed { index, mataKuliah ->
                        val globalIndex = (currentPage - 1) * itemsPerPage + index + 1

                        val isLastItem = (index == paginatedList.lastIndex)

                        TableRow(
                            number = globalIndex,
                            mataKuliah = mataKuliah,
                            isLastItem = isLastItem,
                            onEditClick = { /* Handle edit */ },
                            onDeleteClick = {
                                mataKuliahList.remove(mataKuliah)
                            }
                        )
                    }
                }
            }

            item {
                // Pagination
                PaginationControls (
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
fun TopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(UGNGreenDark)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.rounded_arrow_back_24),
            contentDescription = "Back",
            modifier = Modifier
                .size(44.dp)
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
fun StatsCard(totalActive: Int) {
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
                    text = "Total Mata Kuliah",
                    color = Color.White,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_subject_goldbg),
                    contentDescription = "Subject Icon",
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
                    text = "Mata kuliah aktif",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                "Cari Matkul",
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
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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

@Composable
fun TableRow(
    number: Int,
    mataKuliah: MataKuliah,
    isLastItem: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val rowShape = if (isLastItem) {
        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    } else {
        androidx.compose.ui.graphics.RectangleShape
    }

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
            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit",
                    tint = UGNGold,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Delete Button
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun PaginationControls(
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StatsScreenPreview() {
    SiaMobileTheme {
        StatsScreen()
    }
}
