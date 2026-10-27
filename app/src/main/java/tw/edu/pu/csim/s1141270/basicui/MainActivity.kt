package tw.edu.pu.csim.s1141270.basicui

import android.graphics.Color.alpha
import android.media.Image
import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import tw.edu.pu.csim.s1141270.basicui.ui.theme.BasicUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
fun Main( modifier: Modifier = Modifier) {
    Box {
        LazyColumnExample(modifier)
        ColumnExample(modifier)
        TextFieldExample(modifier)
        MiddleExample(modifier)
    }

}
@Composable
fun LazyColumnExample( modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {Text("標題列")}
        items(50){ index ->
            Text("$index")
        }
        item { Text("結尾列") }
    }
}


@Composable
fun ColumnExample(modifier: Modifier = Modifier) {
    var msq by remember { mutableStateOf("") }
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x8AE0BBE4))
            .pointerInput(Unit){
                detectTapGestures (
                    onTap = {msq = "短按" },
                    onDoubleTap = {msq = "連按兩下"},
                    onLongPress = {msq = "長按"}
                )
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("行動應用軟體開發：基本元件")
        Text("作者：蔡承佑")
        Text( msq )
        Spacer(modifier = Modifier.height(8.dp))
        RowExample(modifier)

    }
}

@Composable
fun RowExample(modifier: Modifier = Modifier) {
    Row {
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
    // 管理輸入框的文字狀態
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

@Composable
fun MiddleExample(modifier: Modifier = Modifier){
    Row (
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Image(
            painter = painterResource(id = R.drawable.animal0),
            contentDescription = "animal 0 ",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            alpha = 0.6f
        )

        Image(
            painter = painterResource(id = R.drawable.animal1),
            contentDescription = "animal 1 ",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            alpha = 0.6f
        )


        Image(
            painter = painterResource(id = R.drawable.animal2),
            contentDescription = "animal 2 ",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            alpha = 0.6f
        )

    }
}





