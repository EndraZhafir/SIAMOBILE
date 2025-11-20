package com.endrazhafir.siamobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.endrazhafir.siamobile.ui.theme.UGNGold
import com.endrazhafir.siamobile.ui.theme.UGNGreen
import com.endrazhafir.siamobile.R
import com.endrazhafir.siamobile.data.MataKuliah

@Composable
fun FormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = UGNGreen,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = UGNGold,
                unfocusedBorderColor = UGNGold,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = UGNGreen,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Pilih opsi") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = UGNGold,
                    unfocusedBorderColor = UGNGold,
                    focusedContainerColor = UGNGold.copy(alpha = 0.2f),
                    unfocusedContainerColor = UGNGold.copy(alpha = 0.2f)
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddMahasiswaContent(onSave: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FormDropdown(
            label = "Program Studi",
            options = listOf("Teknik Informatika", "Sistem Informasi", "Manajemen Informatika"),
            selectedOption = program,
            onOptionSelected = { program = it }
        )

        FormTextField(
            label = "Username",
            value = username,
            onValueChange = { username = it }
        )

        FormTextField(
            label = "Email",
            value = email,
            onValueChange = { email = it }
        )

        FormTextField(
            label = "Nama Lengkap",
            value = nama,
            onValueChange = { nama = it }
        )

        FormTextField(
            label = "NIM",
            value = nim,
            onValueChange = { nim = it }
        )

        FormTextField(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = UGNGreen),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "Simpan",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}

@Composable
fun AddMataKuliahContent(onSave: () -> Unit) {
    var nama by remember { mutableStateOf("") }
    var kode by remember { mutableStateOf("") }
    var sks by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FormTextField(
            label = "Nama Matkul",
            value = nama,
            onValueChange = { nama = it },
            placeholder = "Pemrograman Aplikasi Mobile"
        )
        FormTextField(
            label = "Kode Matkul",
            value = kode,
            onValueChange = { kode = it },
            placeholder = "PAM-206"
        )
        FormTextField(
            label = "Jumlah SKS",
            value = sks,
            onValueChange = { sks = it },
            placeholder = "4",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = UGNGreen),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "Simpan",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}

@Composable
fun AddDosenContent(onSave: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FormDropdown(
            label = "Program Studi",
            options = listOf("Sistem Informasi", "Informatika", "Teknologi Informasi"),
            selectedOption = program,
            onOptionSelected = { program = it }
        )

        FormTextField(
            label = "Username",
            value = username,
            onValueChange = { username = it }
        )

        FormTextField(
            label = "Email",
            value = email,
            onValueChange = { email = it }
        )

        FormTextField(
            label = "Nama Lengkap",
            value = nama,
            onValueChange = { nama = it }
        )

        FormTextField(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = UGNGreen),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "Simpan",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

    }
}

@Composable
fun EditMataKuliahContent(
    mataKuliah: MataKuliah,
    onUpdate: (MataKuliah) -> Unit
) {
    var nama by remember { mutableStateOf(mataKuliah.nama) }
    var kode by remember { mutableStateOf(mataKuliah.kode) }
    var sks by remember { mutableStateOf(mataKuliah.sks.toString()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FormTextField(
            label = "Nama Matkul",
            value = nama,
            onValueChange = { nama = it }
        )

        FormTextField(
            label = "Kode Matkul",
            value = kode,
            onValueChange = { kode = it }
        )

        FormTextField(
            label = "Jumlah SKS",
            value = sks,
            onValueChange = { sks = it },
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val updatedSks = sks.toIntOrNull() ?: 0
                onUpdate(mataKuliah.copy(nama = nama, kode = kode, sks = updatedSks))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = UGNGreen),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "Simpan",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}