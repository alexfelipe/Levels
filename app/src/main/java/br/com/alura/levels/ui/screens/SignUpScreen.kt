package br.com.alura.levels.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.levels.ui.theme.LevelsTheme

@Composable
fun SignUpScreen(onSignUpClick: () -> Unit = {}) {
    Column(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var nickname by remember {
            mutableStateOf("")
        }
        TextField(
            value = nickname,
            onValueChange = {
                nickname = it
            },
            Modifier.fillMaxWidth(),
            label = {
                Text(text = "Nickname")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null
                )
            }
        )
        var password by remember {
            mutableStateOf("")
        }
        TextField(
            value = password, onValueChange = {
                password = it
            },
            Modifier.fillMaxWidth(),
            label = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Password,
                    contentDescription = null
                )
            }
        )
        Button(
            onClick = { onSignUpClick() },
            Modifier.fillMaxWidth()
        ) {
            Text(text = "Cadastrar")
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    LevelsTheme {
        Surface {
            SignUpScreen()
        }
    }
}