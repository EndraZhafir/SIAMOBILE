package com.endrazhafir.siamobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endrazhafir.siamobile.R
import com.endrazhafir.siamobile.ui.theme.*

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UGNGreen),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_color),
                contentDescription = "Logo SIAMOBILE",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Welcome to",
                style = MaterialTheme.typography.displayLarge,
                color = White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "SIAMOBILE",
                style = MaterialTheme.typography.displayLarge,
                color = UGNGold,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=393dp,height=851dp,dpi=420"
)
@Composable
fun MainScreenPreview() {
    SiaMobileTheme {
        MainScreen()
    }
}

