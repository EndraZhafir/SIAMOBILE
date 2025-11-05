import com.endrazhafir.siamobile.R

// Foundation (Layout, Shape, Image, dll.)
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions

// Material 3 (Komponen UI)
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text

// Runtime (State Management)
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// UI (Core UI, Modifier, Color, Alignment, dll.)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endrazhafir.siamobile.ui.theme.urbanistFontFamily

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: (String, String) -> Unit = { _, _ -> }
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.ugn_green))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // fungsi Spacer untuk push logo ke tengah secara vertical
            Spacer(modifier = Modifier.weight(1f))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_color),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(400.dp)
                    .height(400.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.weight(1f))

            // Card Content
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.background_cream)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    // Title
                    Text(
                        text = stringResource(R.string.login_text),
                        fontFamily = urbanistFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.ugn_green),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        textAlign = TextAlign.Start
                    )

                    // Welcome Text
                    Text(
                        text = stringResource(R.string.selamat_datang),
                        fontFamily = urbanistFontFamily,
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        textAlign = TextAlign.Start
                    )

                    // Email Label
                    Text(
                        text = stringResource(R.string.email),
                        fontFamily = urbanistFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.ugn_green),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )

                    // Email Input
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.email_hint),
                                fontFamily = urbanistFontFamily,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.ugn_gray).copy(alpha = 0.5f) // Set font and 25% opacity
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_mail),
                                contentDescription = "Email",
                                modifier = Modifier
                                    .size(24.dp),
                                tint = colorResource(id = R.color.ugn_gray)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.ugn_gold),
                            unfocusedBorderColor = colorResource(id = R.color.ugn_gold),
                            focusedLeadingIconColor = colorResource(id = R.color.ugn_green),
                            unfocusedLeadingIconColor = colorResource(id = R.color.ugn_green),
                            cursorColor = colorResource(id = R.color.ugn_green)
                        )
                    )

                    // Password Label
                    Text(
                        text = stringResource(R.string.password),
                        fontFamily = urbanistFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.ugn_green),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )

                    // Password Input
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.password_hint),
                                fontFamily = urbanistFontFamily,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.ugn_gray).copy(alpha = 0.5f) // Set font and 25% opacity
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_lock),
                                contentDescription = "Password",
                                modifier = Modifier
                                    .size(24.dp),
                                tint = colorResource(id = R.color.ugn_gray)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        if (passwordVisible) R.drawable.ic_visible_on
                                        else R.drawable.ic_visible_off
                                    ),
                                    contentDescription = "Toggle password",
                                    modifier = Modifier
                                        .size(24.dp),
                                    tint = colorResource(id = R.color.ugn_gray)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.ugn_gold),
                            unfocusedBorderColor = colorResource(id = R.color.ugn_gold),
                            focusedLeadingIconColor = colorResource(id = R.color.ugn_green),
                            unfocusedLeadingIconColor = colorResource(id = R.color.ugn_green),
                            focusedTrailingIconColor = colorResource(id = R.color.ugn_green),
                            unfocusedTrailingIconColor = colorResource(id = R.color.ugn_green),
                            cursorColor = colorResource(id = R.color.ugn_green)
                        )
                    )

                    // Login Button
                    Button(
                        onClick = { onLoginClick(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.ugn_gold)
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.login_button),
                            fontFamily = urbanistFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
