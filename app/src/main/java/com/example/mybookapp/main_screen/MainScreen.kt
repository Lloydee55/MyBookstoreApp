package com.example.mybookapp.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mybookapp.data.Book
import com.example.mybookapp.login.data.MainScreenDataObject
import com.example.mybookapp.main_screen.bottom_menu.BottomMenu
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

@Preview(
    showBackground = true
)
@Composable
fun MainScreen(
    navData : MainScreenDataObject? = null,
    onAdminClick : () -> Unit = {}
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val booksListState = remember {
        mutableStateOf( emptyList<Book>())
    }

    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        getAllBooks(db){books ->
            booksListState.value = books
        }
    }

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader(navData!!.email)
                DrawerBody {
                    coroutineScope.launch{
                        drawerState.close()
                    }
                    onAdminClick()
                }
            }

        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomMenu()
            }
        ) { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(booksListState.value){ book ->
                    BookListItemUi(book)
                }
            }
        }

    }
}

private fun getAllBooks(
    db: FirebaseFirestore,
    onBooks: (List<Book>) -> Unit
){
    db.collection("books")
        .get()
        .addOnSuccessListener{ task ->
            onBooks(task.toObjects(Book::class.java))
        }
        .addOnFailureListener{

        }
}