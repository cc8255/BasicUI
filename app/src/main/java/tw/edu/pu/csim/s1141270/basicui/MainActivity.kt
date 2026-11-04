package tw.edu.pu.csim.s1141270.basicui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import tw.edu.pu.csim.s1141270.basicui.ui.theme.BasicUITheme

data class VideoItem(
    val res: Int,
    val isPortrait: Boolean
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Toast 放在 setContent 外面，避免每次重組都重複跳出
        Toast.makeText(this, "作者蔡承佑", Toast.LENGTH_LONG).show()
        setContent {
            BasicUITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    val videos = remember {
        listOf(
            VideoItem(R.raw.love, isPortrait = false),
            VideoItem(R.raw.handpan, isPortrait = true)
        )
    }
    var current by remember { mutableStateOf(0) }
    val video = videos[current]

    Box(modifier = modifier) {
        LazyColumnExample()
        ColumnExample()
        TextFieldExample()
        LazyRowExample()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleVideoPlayer(
                videoRes = video.res,
                isPortrait = video.isPortrait
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { current = (current + 1) % videos.size }) {
                Text(if (current == 0) "切換至手碼影片" else "切換至Love影片")
            }
        }
    }
}
@Composable
fun LazyColumnExample(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text("標題列") }
        items(50) { index ->
            Text("$index")
        }
        item { Text("結尾列") }
    }
}

@Composable
fun ColumnExample(modifier: Modifier = Modifier) {
    var msq by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xffE0BBE4))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { msq = "短按" },
                    onDoubleTap = { msq = "連按兩下" },
                    onLongPress = { msq = "長按" }
                )
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("行動應用軟體開發：基本元件")
        Text("作者：蔡承佑")
        Text(msq)
        Spacer(modifier = Modifier.height(8.dp))
        RowExample()
    }
}

@Composable
fun RowExample(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.android),
            contentDescription = "Android icon",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Yellow),
            alpha = 0.6f,
        )

        Spacer(modifier = Modifier.width(20.dp))

        Image(
            painter = painterResource(id = R.drawable.compose),
            contentDescription = "Compose icon",
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun TextFieldExample(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("請輸入文字") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (inputText.isBlank()) "尚未輸入內容" else "您輸入的是：$inputText"
        )
    }
}

data class Animal(
    val name: String,
    @DrawableRes val imageRes: Int
)

@Composable
fun LazyRowExample(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val animals = listOf(
        Animal("鴨子", R.drawable.animal0),
        Animal("企鵝", R.drawable.animal1),
        Animal("青蛙", R.drawable.animal2),
        Animal("貓頭鷹", R.drawable.animal3),
        Animal("海豚", R.drawable.animal4),
        Animal("牛", R.drawable.animal5),
        Animal("無尾熊", R.drawable.animal6),
        Animal("獅子", R.drawable.animal7),
        Animal("狐狸", R.drawable.animal8),
        Animal("小雞", R.drawable.animal9)
    )
    LazyRow(
        modifier = modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(animals) { index, animal ->
            Card(
                modifier = Modifier
                    .fillParentMaxWidth(1.0f)
                    .height(260.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFD0BCFF)
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = animal.imageRes),
                        contentDescription = animal.name,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                Toast.makeText(
                                    context,
                                    "您點選的是 ${animal.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "$index ${animal.name}")
                }
            }
        }
    }
}

@Composable
fun SimpleVideoPlayer(
    @RawRes videoRes: Int,
    isPortrait: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val exoPlayer = remember(videoRes) {
        ExoPlayer.Builder(context).build().apply {
            val uri = "android.resource://${context.packageName}/$videoRes"
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose { exoPlayer.release() }
    }

    // 橫向：寬度滿版；直向：高度固定，寬度依 9:16 縮窄並置中
    val sizeModifier = if (isPortrait) {
        Modifier
            .height(250.dp)
            .aspectRatio(9f / 16f, matchHeightConstraintsFirst = true)
    } else {
        Modifier
            .fillMaxWidth()
            .height(250.dp)
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                setBackgroundColor(android.graphics.Color.BLACK)
            }
        },
        update = { it.player = exoPlayer },
        modifier = modifier.then(sizeModifier)
    )
}