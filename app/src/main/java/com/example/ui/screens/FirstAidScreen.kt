package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.FirstAidItem
import com.example.data.model.PreloadedFirstAidGuides
import com.example.ui.components.SpeakerAudioButton
import com.example.ui.components.TriageLevelBadge
import com.example.ui.localization.AppStrings
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.TealPrimary
import com.example.ui.theme.UrgentRed

@Composable
fun FirstAidScreen(
    lang: AppLanguage,
    isSpeaking: Boolean,
    onSpeak: (String) -> Unit,
    onStopAudio: () -> Unit,
    onCallEmergency: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedGuideId by remember { mutableStateOf<String?>("snake_bite") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("first_aid_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header with 100% Offline Badge
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppStrings.tabFirstAid(lang),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TealPrimary
                    )
                    Surface(
                        color = HealthGreen.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, HealthGreen)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.WifiOff,
                                contentDescription = "Offline Available",
                                tint = HealthGreen,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "100% ऑफलाइन उपलब्ध",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = HealthGreen
                            )
                        }
                    }
                }
                Text(
                    text = "इंटरनेट नसले तरीही तात्काळ जीवन वाचवणारे प्रथमोपचार व मार्गदर्शन",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // List of Essential Offline Guides
        items(PreloadedFirstAidGuides) { item ->
            val isExpanded = expandedGuideId == item.id
            val fullTextToSpeak = buildString {
                append(item.getTitle(lang)).append(". ")
                append("काय करावे: ")
                item.getSteps(lang).forEach { append(it).append(". ") }
                append("काय करू नये: ")
                item.getDonts(lang).forEach { append(it).append(". ") }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("first_aid_item_${item.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, if (isExpanded) TealPrimary else Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Header Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedGuideId = if (isExpanded) null else item.id },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = if (item.urgencyLevel == com.example.data.model.TriageLevel.URGENT) Color(0xFFFFEBEE) else Color(0xFFE0F2FE),
                                modifier = Modifier.size(42.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MedicalServices,
                                    contentDescription = item.getTitle(lang),
                                    tint = if (item.urgencyLevel == com.example.data.model.TriageLevel.URGENT) UrgentRed else TealPrimary,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = item.getTitle(lang),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (item.urgencyLevel == com.example.data.model.TriageLevel.URGENT) "अत्यंत तातडीचे (Emergency)" else "तातडीचे प्राथमिक उपचार",
                                    fontSize = 12.sp,
                                    color = if (item.urgencyLevel == com.example.data.model.TriageLevel.URGENT) UrgentRed else HealthGreen,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expand",
                            tint = TealPrimary
                        )
                    }

                    // Expanded Content
                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 16.dp)) {
                            // Listen Audio Button
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                SpeakerAudioButton(
                                    textToSpeak = fullTextToSpeak,
                                    isSpeaking = isSpeaking,
                                    onSpeak = onSpeak,
                                    onStop = onStopAudio,
                                    lang = lang
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Steps to do (Green)
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFF0FDF4),
                                border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Do",
                                            tint = HealthGreen,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "काय करावे? (Step-by-Step Do's):",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = HealthGreen
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    item.getSteps(lang).forEachIndexed { idx, step ->
                                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                                            Text(
                                                text = "${idx + 1}. ",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = HealthGreen
                                            )
                                            Text(
                                                text = step,
                                                fontSize = 13.sp,
                                                color = Color(0xFF14532D)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Don'ts (Red warning)
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFFFF1F2),
                                border = BorderStroke(1.dp, Color(0xFFFECDD3))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Block,
                                            contentDescription = "Don't",
                                            tint = UrgentRed,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "काय करू नये? (Don'ts):",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = UrgentRed
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    item.getDonts(lang).forEach { dont ->
                                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                            Text(
                                                text = "✗ ",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = UrgentRed
                                            )
                                            Text(
                                                text = dont,
                                                fontSize = 13.sp,
                                                color = Color(0xFF881337)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Direct Call Button
                            Button(
                                onClick = { onCallEmergency(item.emergencyContact) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("first_aid_call_${item.id}"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (item.emergencyContact == "102") Color(0xFF0284C7) else UrgentRed
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = "Call")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (item.emergencyContact == "102") "१०२ जननी वाहन बोलवा" else "१०८ रुग्णवाहिका बोलवा",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
