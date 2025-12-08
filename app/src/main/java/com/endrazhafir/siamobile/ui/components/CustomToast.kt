package com.endrazhafir.siamobile.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.endrazhafir.siamobile.R
import com.endrazhafir.siamobile.ui.theme.UGNGreenLight
import com.endrazhafir.siamobile.ui.theme.UGNRed
import kotlinx.coroutines.delay

enum class ToastType {
    SUCCESS, ERROR
}

data class ToastData(
    val message: String,
    val type: ToastType
)

object ToastManager {
    val toastEvent = mutableStateOf<ToastData?>(null)

    fun show(message: String, type: ToastType) {
        toastEvent.value = ToastData(message, type)
    }
}

@Composable
fun CustomToastHost() {
    val toastData = ToastManager.toastEvent.value
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(toastData) {
        if (toastData != null) {
            isVisible = true
            delay(3000)
            isVisible = false
            delay(500)
            if (ToastManager.toastEvent.value == toastData) {
                ToastManager.toastEvent.value = null
            }
        }
    }

    // Animasi pop-up custom toast yang nanti slide In/Out dari atas cihuyy
    AnimatedVisibility(
        visible = isVisible && toastData != null,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp, start = 20.dp, end = 20.dp)
            .zIndex(99f)
    ) {
        toastData?.let { data ->
            val backgroundColor = if (data.type == ToastType.SUCCESS) UGNGreenLight else UGNRed
            val iconRes = if (data.type == ToastType.SUCCESS) R.drawable.ic_check else R.drawable.ic_error

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor.copy(alpha = 0.9f), RoundedCornerShape(12.dp))
                    .border(1.dp, backgroundColor, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = data.message,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}