package com.test.project.shared.compose.widgets.inputs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.project.R

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun AllsetOutlinedTextFieldPreview() {
  val focusRequester = remember { FocusRequester() }
  val focusManager: FocusManager = LocalFocusManager.current
  var text by rememberSaveable { mutableStateOf("") }

  AllsetOutlinedTextField(
    modifier = Modifier
      .focusRequester(focusRequester)
      .padding(all = 16.dp)
      .fillMaxWidth()
      .defaultMinSize(minHeight = 98.dp),
    keyboardActions = KeyboardActions(onDone = {
      focusManager.clearFocus()
    }),
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Text,
      imeAction = ImeAction.Done
    ),
    value = text,
    onValueChange = {
      text = it
    }
  )
}

@Composable
fun AllsetOutlinedTextField(
  modifier: Modifier = Modifier,
  modifierTextField: Modifier = Modifier,
  subLabelModifier: Modifier = Modifier
    .padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 8.dp),
  placeholderText: String = "",
  placeholder: @Composable (() -> Unit)? = null,
  label: @Composable (() -> Unit)? = {
    Text(
      text = placeholderText,
    )
  },

  keyboardOptions: KeyboardOptions = KeyboardOptions(),
  keyboardActions: KeyboardActions = KeyboardActions(),
  colors: AllsetTextFieldColors = AllsetTextFieldDefaults.outlinedTextFieldColors(
    textColor = Color.Black,
    cursorColor = Color.Black,
    focusedBorderColor = colorResource(id = R.color.blue),
    unfocusedBorderColor = Color.Transparent,
    errorBorderColor = MaterialTheme.colors.error.copy(0.5f),
    placeholderColor = MaterialTheme.colors.primaryVariant,
    errorCursorColor = Color.Black,
    focusedLabelColor = MaterialTheme.colors.primaryVariant,
    unfocusedLabelColor = MaterialTheme.colors.primaryVariant
  ),
  shape: Shape = MaterialTheme.shapes.small,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  value: String,
  onValueChange: (String) -> Unit,
  readOnly: Boolean = false,
  enabled: Boolean = true,
  subLabel: String = "",
  showMaxLength: Boolean = true,
  maxLength: Int? = null,
  maxLines: Int = Int.MAX_VALUE,
  singleLine: Boolean = false,
  leadingIcon: @Composable (() -> Unit)? = null,
  trailingIcon: @Composable (() -> Unit)? = null,
  textStyle: TextStyle = MaterialTheme.typography.subtitle1,
  isError: Boolean = false,
) {
  Column(modifier = modifier) {
    val customTextSelectionColors = TextSelectionColors(
      handleColor = Color.Black,
      backgroundColor = Color.Black.copy(alpha = 0.4f)
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
      AllsetTextField(
        modifier = modifierTextField,
        value = value,
        shape = shape,
        visualTransformation = visualTransformation,
        readOnly = readOnly,
        enabled = enabled,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = placeholder,
        label = label,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = colors,
        isError = isError,
        textStyle = textStyle,
        maxLines = maxLines,
        singleLine = singleLine,
      )
    }

    if (subLabel.isNotEmpty() || maxLength != null) {
      Row(
        modifier = subLabelModifier,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        if (subLabel.isNotEmpty()) {
          Text(
            modifier = Modifier
              .weight(1f)
              .padding(end = 8.dp),
            text = subLabel,
            fontSize = 12.sp,
            style = MaterialTheme.typography.caption.copy(
              lineHeight = 24.sp,
              fontWeight = FontWeight.Normal
            ),
            color = if (isError) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
          )
        }

        Spacer(modifier = Modifier.weight(0.01f))

        if (showMaxLength && maxLength != null) {
          Text(
            text = "${value.length}/$maxLength",
            fontSize = 12.sp,
            style = MaterialTheme.typography.caption.copy(
              lineHeight = 24.sp,
              fontWeight = FontWeight.Normal
            ),
            color = if (value.length > maxLength) {
              MaterialTheme.colors.error
            } else MaterialTheme.colors.onSurface
          )
        }
      }
    }
  }
}

@Composable
fun EmailTextField(
  modifier: Modifier = Modifier,
  modifierTextField: Modifier = Modifier,
  placeholderText: String = "",
  placeholder: @Composable (() -> Unit)? = null,
  label: @Composable (() -> Unit)? = {
    Text(
      text = placeholderText,
    )
  },

  keyboardActions: KeyboardActions = KeyboardActions(),
  keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
  shape: Shape = MaterialTheme.shapes.small,
  value: String,
  onValueChange: (String) -> Unit,
  subLabel: String = "",
  maxLength: Int? = null,
  maxLines: Int = Int.MAX_VALUE,
  trailingIcon: @Composable (() -> Unit)? = null,
  leadingIcon: @Composable (() -> Unit)? = null,
  isError: Boolean = false,
) {
  AllsetOutlinedTextField(
    modifier = modifier,
    modifierTextField = modifierTextField,
    value = value,
    shape = shape,
    onValueChange = onValueChange,
    trailingIcon = trailingIcon,
    placeholder = placeholder,
    label = label,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    maxLines = maxLines,
    singleLine = true,
    subLabel = subLabel,
    maxLength = maxLength,
    isError = isError,
    leadingIcon = leadingIcon
  )
}

@Composable
fun PasswordTextField(
  modifier: Modifier = Modifier,
  modifierTextField: Modifier = Modifier,
  placeholderText: String = "",
  placeholder: @Composable (() -> Unit)? = null,
  label: @Composable (() -> Unit)? = {
    Text(
      text = placeholderText,
    )
  },

  keyboardActions: KeyboardActions = KeyboardActions(),
  keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
  shape: Shape = MaterialTheme.shapes.small,
  value: String,
  onValueChange: (String) -> Unit,
  subLabel: String = "",
  maxLength: Int? = null,
  maxLines: Int = Int.MAX_VALUE,
  onPasswordVisibilityChange: (Boolean) -> Unit = {},
  isPasswordVisible: Boolean = false,
  isError: Boolean = false,
) {
  var passwordVisible by rememberSaveable { mutableStateOf(isPasswordVisible) }
  AllsetOutlinedTextField(
    modifier = modifier,
    modifierTextField = modifierTextField,
    value = value,
    shape = shape,
    onValueChange = onValueChange,
    placeholder = placeholder,
    label = label,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    maxLines = maxLines,
    singleLine = true,
    subLabel = subLabel,
    isError = isError,
    maxLength = maxLength,
    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//    trailingIcon = {
//      if (passwordVisible) {
//        Image(
//          modifier = Modifier
//            .clickable {
//              passwordVisible = false
//              onPasswordVisibilityChange(passwordVisible)
//            }
//            .size(16.dp),
//          colorFilter = ColorFilter.tint(color = colorResource(R.color.blue_grey)),
//          painter = painterResource(id = R.drawable.ic_password_visible),
//          contentDescription = null
//        )
//
//      } else {
//        Image(
//          modifier = Modifier
//            .clickable {
//              passwordVisible = true
//              onPasswordVisibilityChange(passwordVisible)
//            }
//            .size(16.dp),
//          colorFilter = ColorFilter.tint(color = colorResource(R.color.blue_grey)),
//          painter = painterResource(id = R.drawable.ic_password_invisible),
//          contentDescription = null
//        )
//      }
//    }
  )
}