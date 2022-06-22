package com.test.project.features.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.test.project.R
import com.test.project.features.auth.login.models.LoginState
import com.test.project.shared.compose.theme.AppTheme
import com.test.project.shared.compose.widgets.*
import com.test.project.shared.compose.widgets.inputs.EmailTextField
import com.test.project.shared.compose.widgets.inputs.PasswordTextField
import com.test.project.shared.models.KeyboardActionType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

  private val loginVM: LoginVM by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(inflater.context).apply {
      setContent {
        AppTheme {
          SetSystemUiPrimarySurfaceColor()
          Scaffold(
            modifier = Modifier
              .statusBarsPadding(),
            topBar = {
              AppBar(
                modifier = Modifier.padding(start = 24.dp),
                navigationBackButtonIconColor = ColorFilter.tint(MaterialTheme.colors.secondary),
              )
            }
          ) { paddingValues ->
            val state by loginVM.state.collectAsState()
            ContentUI(
              modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
              state
            )

            HandleDefaultErrors(
              state = state,
              callback = loginVM
            )
          }
        }
      }
    }
  }

  @OptIn(ExperimentalComposeUiApi::class)
  @Composable
  private fun ContentUI(modifier: Modifier, state: LoginState) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(
      modifier = modifier
        .fillMaxSize()
        .navigationBarsPadding()
        .clickable(interactionSource = interactionSource, indication = null) {
          focusManager.clearFocus()
          keyboardController?.hide()
        }
        .padding(start = 16.dp, end = 16.dp)
    ) {
      Column(
        modifier = Modifier
          .width(496.dp)
          .align(Alignment.Center)
          .verticalScroll(rememberScrollState())
          .imePadding(),
        verticalArrangement = Arrangement.Center
      ) {
        val focusRequester = remember { FocusRequester() }

        EmailTextField(
          modifier = Modifier
            .fillMaxWidth(),
          modifierTextField = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .height(80.dp)
            .background(
              color = MaterialTheme.colors.surface,
              shape = MaterialTheme.shapes.small
            ),
          placeholderText = state.email.placeholder,
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = when (state.email.keyboardActionType) {
              KeyboardActionType.Done -> ImeAction.Done
              KeyboardActionType.Next -> ImeAction.Next
            }
          ),
          keyboardActions = KeyboardActions(
            onNext = {
              focusManager.moveFocus(FocusDirection.Down)
            },
            onDone = {
              focusManager.clearFocus()
            }
          ),
          value = state.email.value,
          onValueChange = {
            if (state.email.maxLength == null || it.length <= state.email.maxLength) {
              loginVM.onEmailChanged(state.email.copy(value = it))
            }
          },
          maxLength = state.email.maxLength,
          subLabel = state.email.subLabel,
          isError = state.email.isError,
          maxLines = 1,
        )

        Spacer(modifier = Modifier.size(8.dp))

        PasswordTextField(
          modifier = Modifier
            .fillMaxWidth(),
          modifierTextField = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .height(80.dp)
            .background(
              color = MaterialTheme.colors.surface,
              shape = MaterialTheme.shapes.small
            ),
          placeholderText = state.password.placeholder,
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
          ),
          keyboardActions = KeyboardActions(
            onDone = {
              focusManager.clearFocus()
              keyboardController?.hide()
              loginVM.onLogin()
            }
          ),
          value = state.password.value,
          onValueChange = {
            if (state.email.maxLength == null || it.length <= state.email.maxLength) {
              loginVM.onPasswordChanged(state.password.copy(value = it))
            }
          },
          maxLength = state.password.maxLength,
          subLabel = state.password.subLabel,
          isError = state.password.isError,
          maxLines = 1,
        )

//        Spacer(modifier = Modifier.size(8.dp))
//
//        PrimaryOutlinedButton(
//          onClick = { },
//          modifier = Modifier
//            .widthIn(min = 137.dp, max = 250.dp)
//            .height(56.dp),
//          buttonContentModifier = Modifier
//            .wrapContentWidth(),
//          border = null,
//          text = stringResource(id = R.string.login__forgot_password)
//        )

        Spacer(modifier = Modifier.size(24.dp))

        PrimaryButton(
          modifier = Modifier
            .height(64.dp)
            .width(216.dp),
          onClick = {
            focusManager.clearFocus()
            keyboardController?.hide()
            loginVM.onLogin()
          },
          enabled = !state.isLoading,
          isLoading = state.isLoading,
          text = stringResource(id = R.string.login__log_in),
        )
      }

      CircleImageButton(
        onClick = {
          focusManager.clearFocus()
          keyboardController?.hide()
          loginVM.onHelp()
        },
        painter = painterResource(R.drawable.ic_question),
        backgroundColor = colorResource(R.color.primary_variant).copy(alpha = 0.32f),
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(bottom = 12.dp)
          .size(40.dp)
      )
    }
  }

}
