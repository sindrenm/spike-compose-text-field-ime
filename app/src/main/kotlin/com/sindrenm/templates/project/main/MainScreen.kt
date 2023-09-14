@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.sindrenm.templates.project.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen() {
  Scaffold(
    topBar = { TopAppBar(title = { Text("Top App Bar") }) },
    bottomBar = {
      Spacer(
        Modifier
          .navigationBarsPadding()
          .imePadding()
          .padding(bottom = 12.dp),
      )
    },
  ) { screenPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(screenPadding),
      verticalArrangement = Arrangement.spacedBy(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      repeat(30) { item -> YourTextField(item) }
    }
  }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun YourTextField(index: Int) {
  val (value, onValueChange) = remember { mutableStateOf("") }
  val bringIntoViewRequester = remember { BringIntoViewRequester() }
  val coroutineScope = rememberCoroutineScope()
  val focusManager = LocalFocusManager.current

  TextField(
    value = value,
    label = { Text("Text field $index") },
    onValueChange = onValueChange,
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
    singleLine = true,
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions = KeyboardActions(
      onNext = { focusManager.moveFocus(FocusDirection.Next) }
    )
  )
}
