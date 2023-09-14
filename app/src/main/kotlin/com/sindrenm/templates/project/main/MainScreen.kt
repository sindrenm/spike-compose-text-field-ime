package com.sindrenm.templates.project.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen() {
  val state = rememberLazyListState()

  Scaffold(
    topBar = {
      Spacer(
        Modifier
          .statusBarsPadding()
          .padding(top = 12.dp),
      )
    },
    bottomBar = {
      Spacer(
        Modifier
          .navigationBarsPadding()
          .imePadding()
          .padding(bottom = 12.dp),
      )
    },
  ) { screenPadding ->
    LazyColumn(
      state = state,
      modifier = Modifier.fillMaxSize(),
      contentPadding = screenPadding,
      verticalArrangement = Arrangement.spacedBy(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      items(30) { item -> YourTextField(item) }
    }
  }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun YourTextField(index: Int) {
  val bringIntoViewRequester = remember { BringIntoViewRequester() }
  val coroutineScope = rememberCoroutineScope()

  TextField(
    value = "",
    label = { Text("Text field $index") },
    onValueChange = {},
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp)
      .bringIntoViewRequester(bringIntoViewRequester)
      .onFocusEvent { focusState ->
        if (focusState.isFocused) {
          coroutineScope.launch {
            bringIntoViewRequester.bringIntoView()
          }
        }
      },
  )
}
