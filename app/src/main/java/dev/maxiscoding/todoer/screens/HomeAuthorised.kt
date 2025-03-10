package dev.maxiscoding.todoer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.maxiscoding.todoer.vms.AppRootViewModel
import dev.maxiscoding.todoer.vms.isLoggedIn

@Composable
fun HomeAuthorised(viewModel: AppRootViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen Authorised")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { viewModel.logout() }) {
            Text("Logout")
        }
    }
}