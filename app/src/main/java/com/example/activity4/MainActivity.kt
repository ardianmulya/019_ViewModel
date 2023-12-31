package com.example.activity4

import android.graphics.Outline
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.activity4.data.DataForm
import com.example.activity4.data.DataSource.jenis
import com.example.activity4.data.DataSource.status
import com.example.activity4.data.dataST
import com.example.activity4.ui.theme.Activity4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Activity4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilLayout()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun TampilLayout(
    modifier: Modifier = Modifier
){
    Card(
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ){
            TampilForm()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilForm(cobaViewModel: CobaViewModel = viewModel()) {
    var textNama by remember {
        mutableStateOf("")
    }
    var textTlp by remember {
        mutableStateOf("")
    }
    var almt by remember {
        mutableStateOf("")
    }
    var em by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val dataform: DataForm
    val sta : dataST
    val uiState by cobaViewModel.uiState.collectAsState()
    val state by cobaViewModel.state.collectAsState()
    dataform = uiState
    sta = state

    Row (horizontalArrangement = Arrangement.spacedBy(120.dp), modifier = Modifier.fillMaxWidth()){
        Image(painter = painterResource(R.drawable.arrow_back), contentDescription =" ")
        Text(text = "Register", modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
    }
    Divider()

    Text(text = "Create Your Account",
        fontWeight = FontWeight.Bold,
        fontSize =20.sp
        )

    OutlinedTextField(value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Username") },
        onValueChange = { textNama = it }
    )
    OutlinedTextField(value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telepon") },
        onValueChange = { textTlp = it }
    )
    OutlinedTextField(value = em,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email") },
        onValueChange = { em = it }
    )
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Jenis Kelamin")
        SelectJK(option = jenis.map { id -> context.resources.getString(id) },
            onSelectionChanged = { cobaViewModel.setJenisK(it) }
        )
    }
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Status")
        SelectST(option = status.map { id -> context.resources.getString(id) },
            onSelectionChanged = { cobaViewModel.setJenisK(it) }
        )
    }
    OutlinedTextField(value = almt,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Alamat ") },
        onValueChange = { almt = it }
    )
    Button(modifier = Modifier.fillMaxWidth(),
        onClick = { cobaViewModel.insertData(textNama, textTlp, dataform.sex, almt,sta.st,em) }) {
        Text(
            text = stringResource(id = R.string.submit),
            fontSize = 16.sp
        )
    }
    TampilHasil(status = cobaViewModel.status, email = cobaViewModel.email, jenisnya = cobaViewModel.jenisKl, alamatnya = cobaViewModel.alamat)

}


@Composable
fun SelectJK(
    option: List<String>,
    onSelectionChanged: (String) -> Unit = {}
) {
    var selectedVal by rememberSaveable {
        mutableStateOf("")
    }
    Row (modifier = Modifier
        .fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        option.forEach { item ->
            Row(
                modifier = Modifier.selectable(
                    selected = selectedVal == item,
                    onClick = {
                        selectedVal = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedVal == item,
                    onClick = {
                        selectedVal = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)

            }
        }

    }
}

@Composable
fun SelectST(
    option: List<String>,
    onSelectionChanged: (String) -> Unit = {}
) {
    var selectedValue by rememberSaveable {
        mutableStateOf("")
    }
    Row (modifier = Modifier
        .fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        option.forEach { item ->
            Row(
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)

            }
        }

    }
}

@Composable
fun TampilHasil(status: String, email: String, jenisnya: String, alamatnya:String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Jenis Kelamin : " + jenisnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text = "Status : " + status,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text = "Alamat : " + alamatnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text = "Email : " + email,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Activity4Theme {
        TampilLayout()
    }
}