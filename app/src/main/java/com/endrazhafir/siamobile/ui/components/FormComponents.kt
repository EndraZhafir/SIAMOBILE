package com.endrazhafir.siamobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.endrazhafir.siamobile.ui.theme.UGNGreen
import com.endrazhafir.siamobile.R
import com.endrazhafir.siamobile.data.AddDosenRequest
import com.endrazhafir.siamobile.data.AddMahasiswaRequest
import com.endrazhafir.siamobile.data.MataKuliah
import com.endrazhafir.siamobile.data.Program

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
                focusedBorderColor = UGNGreen,
                unfocusedBorderColor = UGNGreen,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}

@Composable
fun FormCheckbox(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(
                1.dp,
                UGNGreen,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = UGNGreen,
                uncheckedColor = Color.Gray
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = UGNGreen.copy(alpha = 0.5f),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableProgramDropdown(
    label: String,
    options: List<Program>,
    selectedProgramId: Int?,
    onProgramSelected: (Program) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val initialName = options.find { it.id == selectedProgramId }?.name ?: ""

    // Logic untuk filter options berdasarkan searchText
    val filteredOptions = if (searchText.isEmpty() || searchText == initialName) {
        options
    } else {
        options.filter { it.name.contains(searchText, ignoreCase = true) }
    }

    if (searchText.isEmpty() && selectedProgramId != null) {
        searchText = initialName
    }

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
                value = searchText,
                onValueChange = {
                    searchText = it
                    expanded = true
                },
                placeholder = { Text("Cari Program Studi...") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = UGNGreen,
                    unfocusedBorderColor = UGNGreen,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    filteredOptions.forEach { program ->
                        DropdownMenuItem(
                            text = { Text(program.name) },
                            onClick = {
                                searchText = program.name
                                onProgramSelected(program)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddMahasiswaContent(
    programsList: List<Program>,
    onSave: (AddMahasiswaRequest) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }
    var selectedProgramId by remember { mutableStateOf<Int?>(null) }
    var isActive by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Kolom username
        FormTextField(
            label = "Username",
            value = username,
            onValueChange = { username = it },
            placeholder = "Masukkan username mahasiswa"
        )

        // Kolom nama lengkap
        FormTextField(
            label = "Nama Lengkap",
            value = nama,
            onValueChange = { nama = it },
            placeholder = "Masukkan nama lengkap mahasiswa"
        )

        // Kolom NIM
        FormTextField(
            label = "NIM",
            value = nim,
            onValueChange = { nim = it },
            placeholder = "Masukkan NIM mahasiswa"
        )

        // Kolom email
        FormTextField(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            placeholder = "Masukkan email mahasiswa"
        )

        // Kolom password
        FormTextField(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            isPassword = true,
            placeholder = "Masukkan password"
        )

        // Kolom konfirmasi password
        FormTextField(
            label = "Konfirmasi Password",
            value = passwordConfirmation,
            onValueChange = { passwordConfirmation = it },
            isPassword = true,
            placeholder = "Ulangi password"
        )

        // Kolom dropdown prodi
        SearchableProgramDropdown(
            label = "Program Studi",
            options = programsList,
            selectedProgramId = selectedProgramId,
            onProgramSelected = { program ->
                selectedProgramId = program.id
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Kolom checkbox status
        FormCheckbox(
            label = "Status Aktif",
            checked = isActive,
            onCheckedChange = { isActive = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (username.isBlank() || nama.isBlank() || nim.isBlank() || email.isBlank() || password.isBlank()) {
                    ToastManager.show("Mohon lengkapi semua data wajib!", ToastType.ERROR)
                    return@Button
                }

                if (password != passwordConfirmation) {
                    ToastManager.show("Konfirmasi password tidak cocok!", ToastType.ERROR)
                    return@Button
                }

                if (selectedProgramId == null) {
                    ToastManager.show("Silakan pilih Program Studi!", ToastType.ERROR)
                    return@Button
                }

                val idProgram = selectedProgramId!!

                val request = AddMahasiswaRequest(
                    username = username,
                    nameStudent = nama,
                    nim = nim,
                    email = email,
                    password = password,
                    passwordConfirmation = passwordConfirmation,
                    idProgram = idProgram,
                    isActive = isActive,
                )
                onSave(request)
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

@Composable
fun AddMataKuliahContent(
    onSave: (MataKuliah) -> Unit
) {
    var nama by remember { mutableStateOf("") }
    var kode by remember { mutableStateOf("") }
    var sks by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Kolom nama matkul
        FormTextField(
            label = "Nama Matkul",
            value = nama,
            onValueChange = { nama = it },
            placeholder = "Pemrograman Aplikasi Mobile"
        )
        // Kolom kode matkul
        FormTextField(
            label = "Kode Matkul",
            value = kode,
            onValueChange = { kode = it },
            placeholder = "PAM-206"
        )
        // Kolom sks
        FormTextField(
            label = "Jumlah SKS",
            value = sks,
            onValueChange = { sks = it },
            placeholder = "4",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (nama.isBlank() || kode.isBlank() || sks.isBlank()) {
                    ToastManager.show("Data Mata Kuliah tidak boleh kosong!", ToastType.ERROR)
                    return@Button
                }

                if (sks.toIntOrNull() == null) {
                    ToastManager.show("SKS harus berupa angka!", ToastType.ERROR)
                    return@Button
                }

                val sksInt = sks.toInt() // Aman karena sudah dicek
                val newMatkul = MataKuliah(0, nama, kode, sksInt)

                onSave(newMatkul)
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

@Composable
fun AddDosenContent(
    programsList: List<Program>,
    onSave: (AddDosenRequest) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }
    var selectedProgramId by remember { mutableStateOf<Int?>(null) }
    var isActive by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Kolom username
        FormTextField(
            label = "Username",
            value = username,
            onValueChange = { username = it },
            placeholder = "Masukkan username dosen"
        )

        // Kolom nama lengkap
        FormTextField(
            label = "Nama Lengkap",
            value = nama,
            onValueChange = { nama = it },
            placeholder = "Masukkan nama lengkap dosen"
        )

        // Kolom email
        FormTextField(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            placeholder = "Masukkan email dosen"
        )

        // Kolom password
        FormTextField(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            isPassword = true,
            placeholder = "Masukkan password"
        )

        // Kolom konfirmasi password
        FormTextField(
            label = "Konfirmasi Password",
            value = passwordConfirmation,
            onValueChange = { passwordConfirmation = it },
            isPassword = true,
            placeholder = "Ulangi password"
        )

        // Kolom dropdown prodi
        SearchableProgramDropdown(
            label = "Program Studi",
            options = programsList,
            selectedProgramId = selectedProgramId,
            onProgramSelected = { program ->
                selectedProgramId = program.id
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Kolom checkbox status
        FormCheckbox(
            label = "Status Aktif",
            checked = isActive,
            onCheckedChange = { isActive = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (username.isBlank() || nama.isBlank() || email.isBlank() || password.isBlank()) {
                    ToastManager.show("Mohon lengkapi semua data dosen!", ToastType.ERROR)
                    return@Button
                }

                if (password != passwordConfirmation) {
                    ToastManager.show("Konfirmasi password tidak cocok!", ToastType.ERROR)
                    return@Button
                }

                if (selectedProgramId == null) {
                    ToastManager.show("Silakan pilih Program Studi!", ToastType.ERROR)
                    return@Button
                }

                val idProgram = selectedProgramId!!

                val request = AddDosenRequest(
                    username = username,
                    nameLecturer = nama,
                    email = email,
                    password = password,
                    passwordConfirmation = passwordConfirmation,
                    idProgram = idProgram,
                    isActive = isActive,
                )
                onSave(request)
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

        Spacer(modifier = Modifier.height(50.dp))

    }
}

@Composable
fun EditMataKuliahContent(
    mataKuliah: MataKuliah,
    onUpdate: (MataKuliah) -> Unit
) {
    var nama by remember { mutableStateOf(mataKuliah.nameSubject) }
    var kode by remember { mutableStateOf(mataKuliah.codeSubject) }
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
                onUpdate(mataKuliah.copy(
                    nameSubject = nama,
                    codeSubject = kode,
                    sks = updatedSks)
                )
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