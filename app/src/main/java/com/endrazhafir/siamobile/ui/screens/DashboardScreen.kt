package com.endrazhafir.siamobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endrazhafir.siamobile.R
import com.endrazhafir.siamobile.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.endrazhafir.siamobile.ui.viewmodel.DashboardViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    userName: String = "Admin",
    onLogoutClick: () -> Unit = {},
    onStudentClick: () -> Unit = {},
    onSubjectClick: () -> Unit = {},
    onLecturerClick: () -> Unit = {},
    viewModel: DashboardViewModel = viewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Logic ON_RESUME (misal balik dari StatsActivity)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getStatistics(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Logic pull to refresh
    val stats = viewModel.statistics.value
    val isLoading = viewModel.isLoading.value

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()

    // Sinkronisasi status loading viewModel dengan UI Refresh
    LaunchedEffect(isLoading) {
        if (!isLoading) {
            isRefreshing = false
        }
    }

    val onRefresh: () -> Unit = {
        isRefreshing = true
        viewModel.getStatistics(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(UGNGreen),
    ) {
        // Toolbar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            color = UGNGreen
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_color),
                    contentDescription = "Logo UGN",
                    modifier = Modifier.size(50.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
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

                TextButton(onClick = onLogoutClick) {
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.bodyMedium,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Content area (yg bisa di pull ama scroll)
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = pullRefreshState,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    state = pullRefreshState,
                    isRefreshing = isRefreshing,
                    containerColor = UGNGreen,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        ) {
            // Profile, Stats, dan Menu dalam satu Column scrollable biar UX scroll-nya enak
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundCream)
                    .verticalScroll(rememberScrollState())
            ) {
                // Profile & Stats
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(UGNGreen)
                        .padding(start = 20.dp, end = 20.dp, bottom = 30.dp)
                        .background(UGNGreen)
                ) {
                    // Profile
                    Text(
                        text = "Selamat datang, ",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp,
                        color = White,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 20.sp,
                        color = UGNGold,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Statistics Header
                    Text(
                        text = "Statistik",
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 30.sp,
                        color = White,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    // Cards Row
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .height(170.dp)
                            .fillMaxWidth()
                    ) {
                        item {
                            StatCard(
                                icon = R.drawable.ic_student_gold,
                                title = "Mahasiswa Aktif",
                                count = if (isLoading && !isRefreshing) "..." else stats.totalStudents.toString()
                            )
                        }
                        item {
                            StatCard(
                                icon = R.drawable.ic_subject_gold,
                                title = "Mata Kuliah Aktif",
                                count = if (isLoading && !isRefreshing) "..." else stats.totalSubjects.toString()
                            )
                        }
                        item {
                            StatCard(
                                icon = R.drawable.ic_lecturer_gold,
                                title = "Dosen Aktif",
                                count = if (isLoading && !isRefreshing) "..." else stats.totalLecturers.toString()
                            )
                        }
                    }
                }

                // Menu Navigasi
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Sistem Manajemen",
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 30.sp,
                        color = UGNGreen,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    ManagementCard(
                        icon = R.drawable.ic_student,
                        title = "Manage Data Mahasiswa",
                        subtitle = "Kelola data mahasiswa dalam sistem",
                        onClick = onStudentClick
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ManagementCard(
                        icon = R.drawable.ic_subject,
                        title = "Manage Data Mata Kuliah",
                        subtitle = "Kelola data mata kuliah dalam sistem",
                        onClick = onSubjectClick
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ManagementCard(
                        icon = R.drawable.ic_lecturer,
                        title = "Manage Data Dosen",
                        subtitle = "Kelola data dosen dalam sistem",
                        onClick = onLecturerClick
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                }
            }
        }
    }
}


@Composable
fun StatCard(
    icon: Int,
    title: String,
    count: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(170.dp)
            .height(170.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundCream
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(50.dp),
                colorFilter = ColorFilter.tint(UGNGold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = count,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 26.sp,
                color = UGNGold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                color = UGNGold
            )
        }
    }
}

@Composable
fun ManagementCard(
    icon: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(50.dp),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontFamily = urbanistFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = UGNGreen
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    fontFamily = urbanistFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = UGNGold
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=393dp,height=851dp,dpi=420"
)
@Composable
fun DashboardScreenPreview() {
    SiaMobileTheme {
        DashboardScreen()
    }
}