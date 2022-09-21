package br.com.alura.levels.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.levels.R
import br.com.alura.levels.model.User
import br.com.alura.levels.ui.theme.LevelsTheme
import br.com.alura.levels.ui.theme.MainBackgroundColor
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(
    user: User,
    onLeaveClick: () -> Unit = {},
) {
    val imageSize = 100.dp
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(imageSize)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color.Black,
                            Color.Gray
                        )
                    ), shape = RoundedCornerShape(
                        bottomStartPercent = 25,
                        bottomEndPercent = 25
                    )
                )
        ) {
            AsyncImage(
                user.avatar,
                contentDescription = null,
                Modifier
                    .offset(y = imageSize / 2)
                    .size(imageSize)
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.placeholder),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.height(imageSize / 2))
        Text(
            user.nickname, Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { onLeaveClick() }, Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFe53935),
            )
        ) {
            Text(text = "Leave", style = LocalTextStyle.current.copy(color = Color.White))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    LevelsTheme {
        Surface {
            ProfileScreen(
                User(
                    nickname = "alexfelipe",
                    avatar = "https://i0.wp.com/onemoregame.ph/wp-content/uploads/2022/07/yu-yu-hakusho-hiei.jpg"
                )
            )
        }
    }
}
