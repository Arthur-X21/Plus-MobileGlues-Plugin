package com.fcl.plugin.mobileglues.ui.miuix

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fcl.plugin.mobileglues.settings.AppearanceColors
import com.fcl.plugin.mobileglues.settings.ThemeMode
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MiuixAppearancePanel(
    themeMode: ThemeMode,
    primaryColor: Int,
    onThemeModeChange: (ThemeMode) -> Unit,
    onColorChange: (Int) -> Unit,
) {
    var showHexDialog by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MiuixTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MiuixTheme.colorScheme.outline.copy(alpha = 0.35f),
                RoundedCornerShape(20.dp)
            )
            .padding(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Appearance",
                style = MiuixTheme.textStyles.title3,
                color = MiuixTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(Modifier.height(14.dp))

            Text(
                text = "Theme mode",
                style = MiuixTheme.textStyles.body2,
                color = MiuixTheme.colorScheme.onBackgroundVariant,
            )

            Spacer(Modifier.height(9.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                ThemeMode.entries.forEach { mode ->
                    val selected = themeMode == mode
                    val label = when (mode) {
                        ThemeMode.System -> "System"
                        ThemeMode.Light -> "Light"
                        ThemeMode.Dark -> "Dark"
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (selected)
                            MiuixTheme.colorScheme.primaryContainer
                        else
                            MiuixTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onThemeModeChange(mode) }
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 11.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                style = MiuixTheme.textStyles.body2,
                                color = if (selected)
                                    MiuixTheme.colorScheme.onPrimaryContainer
                                else
                                    MiuixTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text = "Primary color",
                style = MiuixTheme.textStyles.body2,
                color = MiuixTheme.colorScheme.onBackgroundVariant,
            )

            Spacer(Modifier.height(12.dp))

            AppearanceColors.palette.chunked(5).forEach { colors ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    colors.forEach { color ->
                        val selected = primaryColor == color
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(Color(color))
                                .border(
                                    width = if (selected) 3.dp else 1.dp,
                                    color = if (selected)
                                        MiuixTheme.colorScheme.onSurface
                                    else
                                        MiuixTheme.colorScheme.outline.copy(alpha = 0.45f),
                                    shape = CircleShape
                                )
                                .clickable { onColorChange(color) }
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MiuixTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showHexDialog = true }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(Color(primaryColor))
                            .border(1.dp, MiuixTheme.colorScheme.outline, CircleShape)
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "Custom color  ·  ${AppearanceColors.toHex(primaryColor)}",
                        style = MiuixTheme.textStyles.body2,
                        color = MiuixTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }

    if (showHexDialog) {
        MiuixHexColorDialog(
            initialColor = primaryColor,
            onApply = {
                onColorChange(it)
                showHexDialog = false
            },
            onDismiss = { showHexDialog = false }
        )
    }
}

@Composable
private fun MiuixHexColorDialog(
    initialColor: Int,
    onApply: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    var hex by remember(initialColor) {
        mutableStateOf(AppearanceColors.toHex(initialColor))
    }
    val parsed = AppearanceColors.parseHex(hex)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MiuixTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Custom primary color",
                    style = MiuixTheme.textStyles.title3,
                    color = MiuixTheme.colorScheme.onSurface,
                )

                Spacer(Modifier.height(16.dp))

                BasicTextField(
                    value = hex,
                    onValueChange = { hex = it.take(9) },
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MiuixTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(14.dp),
                    decorationBox = { inner ->
                        Box {
                            if (hex.isEmpty()) {
                                Text(
                                    text = "#0001F9",
                                    color = MiuixTheme.colorScheme.onBackgroundVariant,
                                )
                            }
                            inner()
                        }
                    }
                )

                Spacer(Modifier.height(12.dp))

                if (parsed == null) {
                    Text(
                        text = "Enter a valid 6 or 8 digit HEX color.",
                        style = MiuixTheme.textStyles.footnote1,
                        color = MiuixTheme.colorScheme.error,
                    )
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(parsed))
                        )
                        Spacer(Modifier.size(8.dp))
                        Text(
                            text = AppearanceColors.toHex(parsed),
                            style = MiuixTheme.textStyles.footnote1,
                            color = MiuixTheme.colorScheme.onBackgroundVariant,
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cancel",
                        color = MiuixTheme.colorScheme.onBackgroundVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(onClick = onDismiss)
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    )
                    Text(
                        text = "Apply",
                        color = if (parsed != null)
                            MiuixTheme.colorScheme.primary
                        else
                            MiuixTheme.colorScheme.onBackgroundVariant.copy(alpha = 0.4f),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(enabled = parsed != null) {
                                parsed?.let(onApply)
                            }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}
