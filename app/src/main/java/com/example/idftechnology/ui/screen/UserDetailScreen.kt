package com.example.idftechnology.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.idftechnology.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(userId: Int, viewModel: UserViewModel = hiltViewModel()) {
    val user = viewModel.users.collectAsState().value.find { it.id == userId }

    Scaffold(
        topBar = { TopAppBar(title = { Text("User Details") }) }
    ) { innerPadding ->
        user?.let {
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                Text(text = "Name: ${it.name}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Email: ${it.email}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Phone: ${it.phone}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "City: ${it.address.city}", style = MaterialTheme.typography.bodyMedium)
            }
        } ?: Text("User not found", Modifier.padding(innerPadding).padding(16.dp))
    }
}