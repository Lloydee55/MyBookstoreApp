package com.example.mybookapp.add_book_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mybookapp.R
import com.example.mybookapp.data.Book
import com.example.mybookapp.login.LoginButton
import com.example.mybookapp.login.RoundedCornerTextField
import com.example.mybookapp.ui.theme.BoxFilterColor
import com.example.mybookapp.ui.theme.MainTextColor
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

@Preview(showBackground = true)
@Composable
fun AddBookScreen(
    onSaved: () -> Unit = {}
) {

    var selectedCategory = "Bestsellers"

    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val price = remember {
        mutableStateOf("")
    }

    //val selectedImageUri = remember { mutableStateOf<Uri?>((null)) }

    val firestore = remember {
        Firebase.firestore
    }

    val storage = remember {
        Firebase.storage
    }

    /*val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri ->
        selectedImageUri.value = uri
    }*/

    /*Image(
        painter = rememberAsyncImagePainter(model = selectedImageUri.value),
        contentDescription = "Background Image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )*/

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoxFilterColor)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 40.dp, end = 40.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(170.dp)
        )
        Text(
            text = "Add new book",
            color = MainTextColor,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerDropDownMenu { selectedItem ->
            selectedCategory = selectedItem
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = title.value,
            label = "title"
        ) {
            title.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            singleLine = false,
            maxLines = 5,
            text = description.value,
            label = "description"
        ) {
            description.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = price.value,
            label = "price"
        ) {
            price.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(text = "Select Image") {
            //imageLauncher.launch("image/*")
        }
        LoginButton(text = "Save") {
            saveBookImage(
                //selectedImageUri.value!!,
                storage,
                firestore,
                Book(
                    name = title.value,
                    description = description.value,
                    price = price.value,
                    category = selectedCategory
                ),
                onSaved = {
                    onSaved()
                },
                onError = {

                }
            )
        }
    }
}

private fun saveBookImage(
    //uri: Uri,
    storage: FirebaseStorage,
    firestore: FirebaseFirestore,
    book: Book,
    onSaved: () -> Unit,
    onError: () -> Unit
){
    val timeStamp = System.currentTimeMillis()
    val storageRef = storage.reference
        .child("book_images")
        .child("image_$timeStamp.jpg")
    //val uploadTask = storageRef.putFile(uri)
    //uploadTask.addOnPausedListener{
        storageRef.downloadUrl.addOnFailureListener{ url ->
            saveBookToFireStore(
                firestore,
                //url.toString(),
                book,
                onSaved = {
                    onSaved()
            },
                onError = {
                    onError()
                }
            )
        }
    //}
}

private fun saveBookToFireStore(
    firestore: FirebaseFirestore,
    //url: String,
    book: Book,
    onSaved: () -> Unit,
    onError: () -> Unit
){
    val dp = firestore.collection("books")
    val key = dp.document().id
    dp.document(key)
        .set(
            book.copy(
                key = key,
                //imageUrl = url
                )
        )
        .addOnSuccessListener{
            onSaved()
        }
        .addOnFailureListener{
            onError()
        }
}
